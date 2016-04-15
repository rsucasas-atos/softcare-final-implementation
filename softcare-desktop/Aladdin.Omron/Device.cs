using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using System.Runtime.InteropServices;
using Microsoft.Win32.SafeHandles;
using System.Threading;

namespace Hid
{
    public class Device : IDisposable
    {
        // Fields
        protected Hid.HIDD_ATTRIBUTES Attributes;
        protected Hid.HIDP_CAPS Capabilities;
        protected FileStream DataStream;
        protected IntPtr Handle;

        // Methods
        public Device(string path)
        {
            IntPtr ptr;
            this.Handle = GetDeviceHandle(path);
            this.Attributes = new Hid.HIDD_ATTRIBUTES();
            Hid.HidD_GetAttributes(this.Handle, ref this.Attributes);
            if (Marshal.GetLastWin32Error() != 0)
            {
                throw new Win32Exception("Cannot get device attributes.");
            }
            this.Capabilities = new Hid.HIDP_CAPS();
            if (Hid.HidD_GetPreparsedData(this.Handle, out ptr))
            {
                try
                {
                    Hid.HidP_GetCaps(ptr, out this.Capabilities);
                }
                finally
                {
                    Hid.HidD_FreePreparsedData(ref ptr);
                }
            }
            if (Marshal.GetLastWin32Error() != 0)
            {
                throw new Win32Exception("Cannot get device capabilities.");
            }
            SafeFileHandle handle = new SafeFileHandle(this.Handle, true);
            this.DataStream = new FileStream(handle, FileAccess.ReadWrite, this.Capabilities.InputReportByteLength, true);
            timeout = 3000;
        }

        public void Dispose()
        {
            this.Dispose(true);
            GC.SuppressFinalize(this);
        }

        protected virtual void Dispose(bool Disposing)
        {
            if (Disposing)
            {
                try
                {
                    this.DataStream.Close();
                }
                catch (Exception)
                {
                }
            }
            Hid.CloseHandle(this.Handle);
        }

        protected static IntPtr GetDeviceHandle(string path)
        {
            IntPtr ptr = Hid.CreateFile(path, 0xc0000000, 3, IntPtr.Zero, 3, 0x40000000, IntPtr.Zero);
            if ((Marshal.GetLastWin32Error() != 0) || (ptr == new IntPtr(-1)))
            {
                throw new Win32Exception(string.Format("Cannot create handle to device {0}", path));
            }
            return ptr;
        }

        public int SetFeature(ref byte lpReportBuffer, int ReportBufferLength)
        {
            return Hid.HidD_SetFeature(Handle, ref lpReportBuffer, ReportBufferLength);
        }

        private int timeout;

        public int TimeOut
        {
            get
            {
                return timeout;
            }
            set
            {
                timeout = value;
            }
        }

        public int Read(byte[] buffer)
        {
            if (buffer.Length != this.Capabilities.InputReportByteLength)
            {
                throw new Exception(string.Format("Buffer length must be {0} bytes.", this.Capabilities.InputReportByteLength));
            }

            ManualResetEvent ev = new ManualResetEvent(false);

            IAsyncResult asyncResult = DataStream.BeginRead(buffer, 0, buffer.Length, new AsyncCallback(OnReadCompletion), ev);
            ev.WaitOne(timeout, false);
            if (asyncResult.IsCompleted)
            {
                return DataStream.EndRead(asyncResult);
            }

            throw new Exception("Can't read data from device");
        }

        static void OnReadCompletion(IAsyncResult asyncResult)
        {
            (asyncResult.AsyncState as ManualResetEvent).Set();
        }

        private static string TruncateZeroTerminatedString(string input)
        {
            if (input == null)
            {
                return null;
            }
            return input.Substring(0, input.IndexOf('\0'));
        }

        public void Write(byte[] data)
        {
            if (data.Length != this.Capabilities.OutputReportByteLength)
            {
                throw new Exception(string.Format("Data length must be {0} bytes.", this.Capabilities.OutputReportByteLength));
            }
            this.DataStream.Write(data, 0, data.Length);
        }

        public byte[] WriteRead(byte[] data)
        {
            this.Write(data);
            byte[] buffer = new byte[this.InputLength];
            this.Read(buffer);
            return buffer;
        }

        // Properties
        public int InputLength
        {
            get
            {
                return this.Capabilities.InputReportByteLength;
            }
        }

        public string Name
        {
            get
            {
                byte[] buffer = new byte[0x100];
                if (!Hid.HidD_GetProductString(this.Handle, buffer, (ulong)buffer.LongLength))
                {
                    throw new Win32Exception("Unable to get device name.");
                }
                return TruncateZeroTerminatedString(new UnicodeEncoding().GetString(buffer));
            }
        }

        public int OutputLength
        {
            get
            {
                return this.Capabilities.OutputReportByteLength;
            }
        }

        public ushort ProductID
        {
            get
            {
                return this.Attributes.ProductID;
            }
        }

        public string SerialNumber
        {
            get
            {
                byte[] buffer = new byte[0x100];
                if (!Hid.HidD_GetSerialNumberString(this.Handle, buffer, (ulong)buffer.LongLength))
                {
                    throw new Win32Exception("Unable to get serial number.");
                }
                return TruncateZeroTerminatedString(new UnicodeEncoding().GetString(buffer));
            }
        }

        public ushort VendorID
        {
            get
            {
                return this.Attributes.VendorID;
            }
        }

        public string VendorName
        {
            get
            {
                byte[] buffer = new byte[0x100];
                if (!Hid.HidD_GetManufacturerString(this.Handle, buffer, (ulong)buffer.LongLength))
                {
                    throw new Win32Exception("Unable to get vendor name.");
                }
                return TruncateZeroTerminatedString(new UnicodeEncoding().GetString(buffer));
            }
        }

        public ushort Version
        {
            get
            {
                return this.Attributes.VersionNumber;
            }
        }
    }
}