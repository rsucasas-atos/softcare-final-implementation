using SoftCare.ClientApplication.Properties;
using System;
using System.IO;
using System.Net;
using System.ServiceModel.Syndication;
using System.Xml;
using System.Linq;
using System.Windows;
using System.Reflection;


namespace SoftCare.ClientApplication
{


    /// <summary>
    /// 
    /// </summary>
    public class AutoUpdater
    {


        private const string ExtensionsNamespace = "http://www.aladdin-project.eu";
        private const string ProductIDExtension = "product_id";
        private const string VersionExtension = "version";
        private const string InstallationUriExtension = "installation_uri";
        private const string StatusExtension = "status";
        private const string SizeExtension = "size";


        /// <summary>
        /// 
        /// </summary>
        public static void CheckForUpdates()
        {
            App.UpdatesAddress = Settings.Default.UpdatesUri;
            Uri uri = new Uri(App.UpdatesAddress);
            using (Stream stream = GetFeedStream(uri))
            {
                SyndicationFeed feed = SyndicationFeed.Load(XmlReader.Create(stream));
                if (feed.Items.Count() > 0)
                {
                    SyndicationItem item = feed.Items.FirstOrDefault();
                    string version = item.ElementExtensions.ReadElementExtensions<string>(AutoUpdater.VersionExtension, AutoUpdater.ExtensionsNamespace)[0];
                    Uri installationUri = item.ElementExtensions.ReadElementExtensions<Uri>(AutoUpdater.InstallationUriExtension, AutoUpdater.ExtensionsNamespace)[0];
                    int versionComparison = CompareVersions(GetAladdinClientVersion(), version, 2);
                    if (versionComparison < 0) // a newer version exists
                    {
                        WebClient client = new WebClient();
                        string path = string.Format("{0}{1}", System.IO.Path.GetTempPath(), "SoftCare.ClientApplication.Setup.msi");
                        client.DownloadFile(installationUri, path);
                        System.Diagnostics.Process.Start(path, "/passive");
                        Application.Current.Shutdown();
                    }
                }
            }
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="version1"></param>
        /// <param name="version2"></param>
        /// <param name="digits"></param>
        /// <returns></returns>
        public static int CompareVersions(string version1, string version2, int digits)
        {
            try
            {
                string[] v1 = version1.Split('.');
                string[] v2 = version2.Split('.');

                for (int i = 0; i < digits; i++)
                {
                    int result = string.Compare(v1[i], v2[i]);
                    if (result != 0)
                        return result;
                }
                return 0;
            }
            catch (Exception ex)
            {
                throw new FormatException("input versions not in correct format", ex);
            }
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="uri"></param>
        /// <returns></returns>
        private static Stream GetFeedStream(Uri uri)
        {
            if (uri.IsFile)
                return File.OpenRead(uri.LocalPath);
            return WebRequest.Create(uri).GetResponse().GetResponseStream();
        }


        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        public static string GetAladdinClientVersion()
        {
            Assembly ass = typeof(AutoUpdater).Assembly;
            object[] atrs = ass.GetCustomAttributes(typeof(AssemblyFileVersionAttribute), false);
            if (atrs.Length == 1)
                return (atrs[0] as AssemblyFileVersionAttribute).Version;
            return null;
        }


    }


}
