using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.InteropServices;

namespace Hid
{
    public static class Hid
    {
        // Fields
        public const int DIGCF_DEVICEINTERFACE = 0x10;
        public const int DIGCF_PRESENT = 2;
        public const uint FILE_FLAG_OVERLAPPED = 0x40000000;

        // Methods
        [DllImport("kernel32.dll", SetLastError = true)]
        public static extern int CloseHandle(IntPtr hFile);
        [DllImport("kernel32.dll", SetLastError = true)]
        public static extern IntPtr CreateFile([MarshalAs(UnmanagedType.LPStr)] string strName, uint nAccess, uint nShareMode, IntPtr lpSecurity, uint nCreationFlags, uint nAttributes, IntPtr lpTemplate);
        [DllImport("hid.dll", SetLastError = true)]
        public static extern bool HidD_FreePreparsedData(ref IntPtr pData);
        [DllImport("hid.dll", SetLastError = true)]
        public static extern bool HidD_GetAttributes(IntPtr HidDeviceObject, ref HIDD_ATTRIBUTES Attributes);
        [DllImport("hid.dll", SetLastError = true)]
        public static extern void HidD_GetHidGuid(out Guid gHid);
        [DllImport("hid.dll", SetLastError = true)]
        public static extern bool HidD_GetManufacturerString(IntPtr HidDeviceObject, byte[] Buffer, ulong BufferLength);
        [DllImport("hid.dll", SetLastError = true)]
        public static extern bool HidD_GetPreparsedData(IntPtr hFile, out IntPtr lpData);
        [DllImport("hid.dll", SetLastError = true)]
        public static extern bool HidD_GetProductString(IntPtr HidDeviceObject, byte[] Buffer, ulong BufferLength);
        [DllImport("hid.dll", SetLastError = true)]
        public static extern bool HidD_GetSerialNumberString(IntPtr HidDeviceObject, byte[] Buffer, ulong BufferLength);
        [DllImport("hid.dll", SetLastError = true)]
        public static extern int HidP_GetCaps(IntPtr lpData, out HIDP_CAPS oCaps);
        [DllImport("setupapi.dll", SetLastError = true)]
        public static extern int SetupDiDestroyDeviceInfoList(IntPtr lpInfoSet);
        [DllImport("setupapi.dll", SetLastError = true)]
        public static extern bool SetupDiEnumDeviceInterfaces(IntPtr lpDeviceInfoSet, uint nDeviceInfoData, ref Guid gClass, uint nIndex, ref SP_DEVICE_INTERFACE_DATA oInterfaceData);
        [DllImport("setupapi.dll", SetLastError = true)]
        public static extern IntPtr SetupDiGetClassDevs(ref Guid gClass, [MarshalAs(UnmanagedType.LPStr)] string strEnumerator, IntPtr hParent, uint nFlags);
        [DllImport("setupapi.dll", SetLastError = true)]
        public static extern bool SetupDiGetDeviceInterfaceDetail(IntPtr lpDeviceInfoSet, ref SP_DEVICE_INTERFACE_DATA oInterfaceData, ref SP_DEVICE_INTERFACE_DETAIL_DATA oDetailData, int nDeviceInterfaceDetailDataSize, out int nRequiredSize, IntPtr lpDeviceInfoData);
        [DllImport("hid.dll", SetLastError = true)]
        public static extern int HidD_SetFeature(IntPtr HidDeviceObject, ref byte lpReportBuffer, int ReportBufferLength);

        // Properties
        public static string[] DevicePaths
        {
            get
            {
                Guid guid;
                List<string> list = new List<string>();
                HidD_GetHidGuid(out guid);
                IntPtr lpDeviceInfoSet = SetupDiGetClassDevs(ref guid, null, IntPtr.Zero, 0x12);
                try
                {
                    SP_DEVICE_INTERFACE_DATA structure = new SP_DEVICE_INTERFACE_DATA();
                    structure.Size = Marshal.SizeOf(structure);
                    uint num = 0;
                    while (SetupDiEnumDeviceInterfaces(lpDeviceInfoSet, 0, ref guid, num++, ref structure))
                    {
                        int num3;
                        SP_DEVICE_INTERFACE_DETAIL_DATA oDetailData = new SP_DEVICE_INTERFACE_DETAIL_DATA();
                        if (IntPtr.Size == 8)
                        {
                            oDetailData.Size = 8;
                        }
                        else
                        {
                            oDetailData.Size = 5;
                        }
                        int nDeviceInterfaceDetailDataSize = Marshal.OffsetOf(typeof(SP_DEVICE_INTERFACE_DETAIL_DATA), "DevicePath").ToInt32() + (Marshal.SystemDefaultCharSize * 0x100);
                        if (SetupDiGetDeviceInterfaceDetail(lpDeviceInfoSet, ref structure, ref oDetailData, nDeviceInterfaceDetailDataSize, out num3, IntPtr.Zero))
                        {
                            list.Add(oDetailData.DevicePath);
                        }
                    }
                }
                finally
                {
                    SetupDiDestroyDeviceInfoList(lpDeviceInfoSet);
                }
                return list.ToArray();
            }
        }

        // Nested Types
        [StructLayout(LayoutKind.Sequential, Pack = 1)]
        public struct HIDD_ATTRIBUTES
        {
            public uint Size;
            public ushort VendorID;
            public ushort ProductID;
            public ushort VersionNumber;
        }

        [StructLayout(LayoutKind.Sequential, Pack = 1)]
        public struct HIDP_CAPS
        {
            public ushort Usage;
            public ushort UsagePage;
            public ushort InputReportByteLength;
            public ushort OutputReportByteLength;
            public ushort FeatureReportByteLength;
            [MarshalAs(UnmanagedType.ByValArray, SizeConst = 0x11)]
            public ushort[] Reserved;
            public ushort NumberLinkCollectionNodes;
            public ushort NumberInputButtonCaps;
            public ushort NumberInputValueCaps;
            public ushort NumberInputDataIndices;
            public ushort NumberOutputButtonCaps;
            public ushort NumberOutputValueCaps;
            public ushort NumberOutputDataIndices;
            public ushort NumberFeatureButtonCaps;
            public ushort NumberFeatureValueCaps;
            public ushort NumberFeatureDataIndices;
        }

        [StructLayout(LayoutKind.Sequential, Pack = 1)]
        public struct SP_DEVICE_INTERFACE_DATA
        {
            public int Size;
            public Guid InterfaceClassGuid;
            public int Flags;
            public IntPtr Reserved;
        }

        [StructLayout(LayoutKind.Sequential, Pack = 1)]
        public struct SP_DEVICE_INTERFACE_DETAIL_DATA
        {
            public int Size;
            [MarshalAs(UnmanagedType.ByValTStr, SizeConst = 0x100)]
            public string DevicePath;
        }
    }
}
