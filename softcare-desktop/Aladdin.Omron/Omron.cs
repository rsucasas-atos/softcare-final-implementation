using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Hid;

namespace Aladdin
{
    public class Omron
    {
        public const int PEDOMETER_MODE = 0x0102;
        private Int32 mode;
        private Device dev;

        private int omron_set_mode(Int32 mode)
        {
            byte b1 = 0;
            byte b2 = (byte)((mode & 0xff00) >> 8);
            byte b3 = (byte)(mode & 0x00ff);
            byte[] feature_report = new byte[3] { b1, b2, b3 };
            int ret = dev.SetFeature(ref feature_report[0], (int)3);
            if (ret < 1)
            {
            }
            return 0;
        }

        private int omron_read_data(byte[] input_report)
        {
            return dev.Read(input_report);
        }

        private int omron_write_data(byte[] output_report)
        {
            byte[] command = new byte[9];
            command[0] = 0;
            for (int i = 0; i < 8; i++)
            {
                command[i + 1] = output_report[i];
            }
            dev.Write(command);
            return 0;
        }

        private int omron_send_command(int size, byte[] buf)
        {
            int total_write_size = 0;
            int current_write_size;
            byte[] output_report = new byte[8];

            while (total_write_size < size)
            {
                current_write_size = size - total_write_size;
                if (current_write_size > 7)
                    current_write_size = 7;

                output_report[0] = (byte)current_write_size;
                for (int i = 0; i < current_write_size; i++)
                {
                    output_report[1 + i] = buf[total_write_size + i];
                }
                omron_write_data(output_report);
                total_write_size += current_write_size;
            }

            return 0;
        }

        private int omron_check_success(byte[] input_report, int start_index)
        {
            return (input_report[start_index] == 'O' && input_report[start_index + 1] == 'K') ? 0 : -1;
        }

        private static byte[] zero = new byte[12];
        private int omron_send_clear()
        {
            byte[] input_report = new byte[9];
            int read_result;
            read_result = omron_read_data(input_report);
            do
            {
                omron_send_command(12, zero);
                read_result = omron_read_data(input_report);
            } while (omron_check_success(input_report, 2) != 0);
            return 0;
        }

        private int omron_check_mode(Device dev, Int32 mode)
        {
            int ret = 0;
            if (this.mode == mode) return 0;
            ret = omron_set_mode(mode);
            if (ret == 0)
            {
                this.mode = mode;
                omron_send_clear();
                return 0;
            }
            return ret;
        }

        private int xor_checksum(byte[] data, int offset, int len)
        {
            byte checksum = 0;
            int begin_len = len;

            for (int i = 0; i < len; i++)
            {
                checksum ^= data[i + offset];
            }
            return checksum;
        }

        private int omron_get_command_return(Int32 size, ref Byte[] data)
        {
            int total_read_size = 0;
            int current_read_size = 0;
            bool has_checked = false;
            byte[] input_report = new byte[9];
            int read_result;
            String buf;

            while (total_read_size < size)
            {
                read_result = omron_read_data(input_report);
                if (read_result < 0)
                {
                    return read_result;
                }

                current_read_size = input_report[1];
                buf = String.Format("{0}", current_read_size);

                if (current_read_size == 8)
                    current_read_size = 7; /* FIXME? Bug? */

                for (int i = 0; i < current_read_size; i++)
                {
                    data[total_read_size + i] = input_report[2 + i];
                }
                total_read_size += current_read_size;

                if (!has_checked && total_read_size >= 2)
                {
                    if (total_read_size == 2 && data[0] == 'N' && data[1] == 'O') return 0;
                    if (data[0] == 'E' && data[1] == 'N' && data[2] == 'D' && data[3] == '\r' && data[4] == '\n') has_checked = (total_read_size >= 5);
                    else
                    {
                        if (data[0] != 'O' || data[1] != 'K') return 1;
                        has_checked = true;
                    }
                }
            }

            if (total_read_size >= 3 && data[0] == 'O' && data[1] == 'K') return xor_checksum(data, 2, total_read_size - 2);
            return 0;
        }

        private void omron_exchange_cmd(Int32 mode, Int32 cmdLen, byte[] cmd, Int32 response_len, ref Byte[] response)
        {
            int status = 0;
            do
            {
                omron_check_mode(dev, mode);
                omron_send_command(cmdLen, cmd);
                status = omron_get_command_return(response_len, ref response);
            } while (status > 0);
        }

        private void omron_dev_info_command(String cmd, ref Byte[] result, Int32 Length)
        {
            Byte[] tmp = new Byte[Length + 3];
            omron_exchange_cmd(PEDOMETER_MODE, cmd.Length, new System.Text.UTF8Encoding().GetBytes(cmd), Length + 3, ref tmp);
            for (int i = 0; i < Length; i++)
            {
                result[i] = tmp[3 + i];
            }
        }

        private int bcd_to_int(byte[] data, int start, int length)
        {
            int ret = 0;
            int pos = start;
            int i;
            for (i = 0; i < length; ++i)
            {
                if ((i % 2) == 0)
                {
                    if (i >= 2)
                        pos++;
                    ret += (data[pos] >> 4) * (int)(Math.Pow(10, length - i - 1));
                }
                else
                {
                    ret += (data[pos] & 0x0f) * (int)(Math.Pow(10, length - i - 1));
                }
            }
            return ret;
        }

        public omron_pd_count_info omron_get_pd_data_count()
        {
            omron_pd_count_info count_info = new omron_pd_count_info();
            Byte[] buf = new Byte[5] { 0, 0, 0, 0, 0 };
            omron_dev_info_command("CNT00", ref buf, 5);
            count_info.daily_count = bcd_to_int(buf, 0, 4);
            count_info.hourly_count = bcd_to_int(buf, 2, 4);
            return count_info;
        }

        private short short_to_bcd(int number)
        {
            return (short)(((number / 10) << 4) | (number % 10));
        }

        public omron_pd_daily_data omron_get_pd_daily_data(int day)
        {
            omron_pd_daily_data daily_data = new omron_pd_daily_data();
            byte[] data = new byte[20];
            byte[] command = new byte[7];
            command[0] = (byte)'M';
            command[1] = (byte)'E';
            command[2] = (byte)'S';
            command[3] = 0;
            command[4] = 0;
            command[5] = (byte)short_to_bcd(day);
            command[6] = (byte)(0 ^ short_to_bcd(day));

            omron_exchange_cmd(PEDOMETER_MODE, 7, command, 20, ref data);
            daily_data.total_steps = bcd_to_int(data, 3, 5);
            return daily_data;
        }

        public omron_pd_hourly_data[] omron_get_pd_hourly_data(int day)
        {
            omron_pd_hourly_data[] hourly_data = new omron_pd_hourly_data[24];
            byte[] data = new byte[37];
            for (int i = 0; i < 3; ++i)
            {
                byte[] command = new byte[8] { (byte)'G', (byte)'T', (byte)'D', 0x00, 0, (byte)(short_to_bcd(day)), (byte)(i + 1), (byte)(short_to_bcd(day) ^ (i + 1)) };
                omron_exchange_cmd(PEDOMETER_MODE, 8, command, 37, ref data);
                for (int j = 0; j <= 7; ++j)
                {
                    int offset = j * 4 + 4;
                    int hour = (i * 8) + j;
                    if (hourly_data[hour] == null) hourly_data[hour] = new omron_pd_hourly_data();
                    hourly_data[hour].is_attached = (data[(offset)] & (1 << 6)) > 0;
                    hourly_data[hour].regular_steps = ((data[(offset)] & (~0xc0)) << 8) | (data[(offset) + 1]);
                    hourly_data[hour].aerobic_steps = ((data[(offset) + 2] & (~0xc0)) << 8) | (data[(offset) + 3]);
                }
            }
            return hourly_data;
        }

        public Omron() {

            foreach (string str in Hid.Hid.DevicePaths) {
                try {
                    dev = new Device(str);
                    if (dev.VendorID == 0x0590 && dev.ProductID == 0x0028) break;
                }
                catch (Win32Exception) {
                }
                dev = null;
            }

            if (dev == null) throw new Exception("device not found");

            mode = 0;
        }
    }
}
