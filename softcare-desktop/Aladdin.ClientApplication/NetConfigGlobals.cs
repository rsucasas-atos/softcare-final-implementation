using Newtonsoft.Json;
using SoftCare.ClientApplication.Controls;
using System;
using System.Collections;
using System.Collections.Generic;
using System.Configuration;
using System.Net.Http;
using System.Net.Http.Headers;
using System.Web.Script.Serialization;
using System.Windows;


namespace SoftCare.ClientApplication
{


    /// <summary>
    /// 
    /// </summary>
    public class NetConfigGlobals
    {


        // REST service
        private string multimediarepoRESTUrl;

        // Multimedia objects
        // VIDEOS:
        private MediaContent[] _multimediarepoVideos;
        public MediaContent[] MultimediaRepoVideos
        {
            get
            {
                return this._multimediarepoVideos;
            }
        }

        private MediaContent[] _multimediarepoVideosWeb;
        public MediaContent[] MultimediaRepoVideosWeb
        {
            get
            {
                return this._multimediarepoVideosWeb;
            }
        }

        // MUSIC:
        private MediaContent[] _multimediarepoMusic;
        public MediaContent[] MultimediarepoMusic
        {
            get
            {
                return this._multimediarepoMusic;
            }
        }

        // BOOKS:
        private MediaContent[] _multimediarepoBooks;
        public MediaContent[] MultimediarepoBooks
        {
            get
            {
                return this._multimediarepoBooks;
            }
        }

        // IMAGES:
        private MediaContent[] _multimediarepoImgs;
        public MediaContent[] MultimediarepoImgs
        {
            get
            {
                return this._multimediarepoImgs;
            }
        }

        // OTHER:
        private MediaContent[] _multimediarepoOther;
        public MediaContent[] MultimediarepoOther
        {
            get
            {
                return this._multimediarepoOther;
            }
        }


        /// <summary>
        /// 
        /// </summary>
        public NetConfigGlobals()
        {
            // TESTS: UNCOMMENT THIS!!!!!
            //try
            //{
            //    string wsUrl = getUrlFromRestService(ConfigurationManager.AppSettings["URL.Rest.WS.Main"] + "/services/api/url/ws");
            //    SoftCare.ClientApplication.Properties.Settings.Default
            //        .Aladdin_ClientApplication_aladdinService_StorageComponentImplService = wsUrl + "/StorageComponent";

            //    string mmUrl = getUrlFromRestService(ConfigurationManager.AppSettings["URL.Rest.WS.Main"] + "/services/api/url/mrepo");
            //    multimediarepoRESTUrl = mmUrl;
            //}
            //catch (Exception ex)
            //{
            //    Console.Write(ex.StackTrace);
            //    MessageBox.Show("Error getting the servers urls: " + ex.Message, Config.APP_NAME, MessageBoxButton.OK, MessageBoxImage.Error);
            //    System.Windows.Forms.Application.Exit();
            //}

            // TESTS: REMOVE THIS!!!!!
            multimediarepoRESTUrl = ConfigurationManager.AppSettings["URL.Rest.WS.MultimediaRepo"];

            _multimediarepoVideos = null;
            _multimediarepoVideosWeb = null;
            _multimediarepoMusic = null;
            _multimediarepoBooks = null;
            _multimediarepoImgs = null;
            _multimediarepoOther = null;

            try
            {
                ArrayList cloudinaryVideos = new ArrayList();
                ArrayList webVideos = new ArrayList();
                ArrayList music = new ArrayList();
                ArrayList books = new ArrayList();
                ArrayList imgs = new ArrayList();
                ArrayList other = new ArrayList();

                // call to Multimedia Repository
                string url_multimedia = multimediarepoRESTUrl + "/api/documents";
                HttpClient client = new HttpClient();
                HttpResponseMessage response = client.GetAsync(url_multimedia).Result;  // Blocking call!

                if (response.IsSuccessStatusCode)
                {
                    // Parse the response body. Blocking!
                    string dataObjects = response.Content.ReadAsStringAsync().Result;
                    Dictionary<string, object>[] json2 =
                        new JavaScriptSerializer().Deserialize<Dictionary<string, object>[]>(dataObjects);

                    MediaContent mediaContent = new MediaContent();

                    for (int i = 0; i < json2.Length; i++)
                    {
                        Dictionary<string, object> obj1 = json2[i];
                        /*
                         * {
                         *  "_id":"56729088c982a6ef2a810e97",
                         *  "created_at":"2015-12-17T10:38:00.774Z",
                         *  "updated_at":"2015-12-17T10:38:00.774Z",
                         *  "name":"Alzheimers001",
                         *  "desc":"Experience 12 Minutes In Alzheimers Dementia",
                         *  "url":"https://youtu.be/LL_Gq7Shc-Y",
                         *  "type":"video",
                         *  "tags":"dementia",
                         *  "stored":false,
                         *  "__v":0
                         * }
                         */
                        mediaContent = new MediaContent();
                        mediaContent.title = obj1["name"].ToString();
                        mediaContent.url = obj1["url"].ToString();
                        mediaContent.type = obj1["type"].ToString();
                        mediaContent.text = obj1["desc1"].ToString();
                        mediaContent.ID = obj1["_id"].ToString();
                        mediaContent.enabled = true;

                        switch (mediaContent.type)
                        {
                            case "video":
                                if ("true".Equals(obj1["stored"].ToString().ToLower()))
                                {
                                    mediaContent.category = Config.CATEGORY_VIDEOS;
                                    cloudinaryVideos.Add(mediaContent);
                                }
                                else
                                {
                                    mediaContent.category = Config.CATEGORY_VIDEOSWEB;
                                    webVideos.Add(mediaContent);
                                };
                                break;

                            case "image":
                                mediaContent.category = Config.CATEGORY_IMGS;
                                imgs.Add(mediaContent);
                                break;

                            case "music":
                                mediaContent.category = Config.CATEGORY_MUSIC;
                                music.Add(mediaContent);
                                break;

                            case "book":
                                mediaContent.category = Config.CATEGORY_BOOKS;
                                books.Add(mediaContent);
                                break;

                            case "other":
                                mediaContent.category = Config.CATEGORY_OTHER;
                                other.Add(mediaContent);
                                break;

                            default:
                                break;
                        }
                    }

                    _multimediarepoVideos = (MediaContent[])cloudinaryVideos.ToArray(typeof(MediaContent));
                    _multimediarepoVideosWeb = (MediaContent[])webVideos.ToArray(typeof(MediaContent));
                    _multimediarepoMusic = (MediaContent[])music.ToArray(typeof(MediaContent));
                    _multimediarepoBooks = (MediaContent[])books.ToArray(typeof(MediaContent));
                    _multimediarepoImgs = (MediaContent[])imgs.ToArray(typeof(MediaContent));
                    _multimediarepoOther = (MediaContent[])other.ToArray(typeof(MediaContent));


                    // TODO: fix
                    //MediaContent mediaContentMusicTest = new MediaContent();
                    //mediaContentMusicTest.title = "Kalimba";
                    //mediaContentMusicTest.url = "C:/Users/A572832/Desktop/Kalimba.mp3";
                    //mediaContentMusicTest.type = "music";
                    //mediaContentMusicTest.text = "Kalimba test";
                    //mediaContentMusicTest.ID = "123123";
                    //mediaContentMusicTest.category = Config.CATEGORY_MUSIC;
                    //mediaContentMusicTest.enabled = true;
                    //music.Add(mediaContentMusicTest);
                    //_multimediarepoMusic = (MediaContent[])music.ToArray(typeof(MediaContent));
                }
                else
                {
                    MessageBox.Show("Warning message: Multimedia Repository not found", "SoftCare Desktop", MessageBoxButton.OK, MessageBoxImage.Warning);
                }
            }
            catch (Exception ex)
            {
                Console.Write(ex.StackTrace);
                MessageBox.Show("Error : " + ex.Message, Config.APP_NAME, MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="type"></param>
        /// <returns></returns>
        public MediaContent[] getVideos(string type)
        {
            try
            {
                HttpClient client = new HttpClient();
                HttpResponseMessage response = client.GetAsync(multimediarepoRESTUrl + "/api/documents").Result;  // Blocking call!

                if (response.IsSuccessStatusCode)
                {
                    if (type.Equals(Config.CATEGORY_VIDEOS)) 
                    {
                        _multimediarepoVideos = null;
                    }
                    else
                    {
                        _multimediarepoVideosWeb = null;
                    }
                    ArrayList arrTmp = new ArrayList();

                    // Parse the response body. Blocking!
                    string dataObjects = response.Content.ReadAsStringAsync().Result;
                    Dictionary<string, object>[] json2 =
                        new JavaScriptSerializer().Deserialize<Dictionary<string, object>[]>(dataObjects);

                    MediaContent mediaContent = new MediaContent();
                    
                    for (int i = 0; i < json2.Length; i++)
                    {
                        Dictionary<string, object> obj1 = json2[i];
                        /*
                         * {
                         *  "_id":"56729088c982a6ef2a810e97",
                         *  "created_at":"2015-12-17T10:38:00.774Z",
                         *  "updated_at":"2015-12-17T10:38:00.774Z",
                         *  "name":"Alzheimers001",
                         *  "desc":"Experience 12 Minutes In Alzheimers Dementia",
                         *  "url":"https://youtu.be/LL_Gq7Shc-Y",
                         *  "type":"video",
                         *  "tags":"dementia",
                         *  "stored":false,
                         *  "__v":0
                         * }
                         */
                        mediaContent = new MediaContent();
                        mediaContent.title = obj1["name"].ToString();
                        mediaContent.url = obj1["url"].ToString();
                        mediaContent.type = obj1["type"].ToString();
                        mediaContent.text = obj1["desc"].ToString();
                        mediaContent.ID = obj1["_id"].ToString();
                        mediaContent.enabled = true;

                        if ( (type.Equals(Config.CATEGORY_VIDEOS)) && ("true".Equals(obj1["stored"].ToString().ToLower())) )
                        {
                            mediaContent.category = Config.CATEGORY_VIDEOS;
                        }
                        else
                        {
                            mediaContent.category = Config.CATEGORY_VIDEOSWEB;
                        }
                        arrTmp.Add(mediaContent);
                    }

                    if (type.Equals(Config.CATEGORY_VIDEOS))
                    {
                        _multimediarepoVideos = (MediaContent[])arrTmp.ToArray(typeof(MediaContent));
                    }
                    else
                    {
                        _multimediarepoVideosWeb = (MediaContent[])arrTmp.ToArray(typeof(MediaContent));
                    }
                }
                else
                {
                    MessageBox.Show("Warning message: Multimedia Repository not found", "SoftCare Desktop", MessageBoxButton.OK, MessageBoxImage.Warning);
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error message: " + ex.Message, "SoftCare Desktop", MessageBoxButton.OK, MessageBoxImage.Error);
            }

            // return results
            if (type.Equals(Config.CATEGORY_VIDEOS))
            {
                return _multimediarepoVideos;
            }
            return _multimediarepoVideosWeb;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="url"></param>
        /// <returns></returns>
        private string getUrlFromRestService(string url)
        {
            try
            {
                HttpClient client = new HttpClient();
                HttpResponseMessage response = client.GetAsync(url).Result;  // Blocking call!

                if (response.IsSuccessStatusCode)
                {
                    // Parse the response body. Blocking!
                    string dataObjects = response.Content.ReadAsStringAsync().Result;
                    Dictionary<string, string> json2 =
                        new JavaScriptSerializer().Deserialize<Dictionary<string, string>>(dataObjects);

                    /* 
                     * {
                     *  "code":"0",
                     *  "result":"url"
                     * }
                     */
                    if (json2["code"].Equals("0"))
                    {
                        return json2["result"];
                    }
                }
                else
                {
                    MessageBox.Show("Error - getUrlFromRestService", "SoftCare Desktop", MessageBoxButton.OK, MessageBoxImage.Warning);
                }
            }
            catch (Exception ex)
            {
                Console.Write(ex.StackTrace);
                MessageBox.Show("Error - getUrlFromRestService : " + ex.Message, Config.APP_NAME, MessageBoxButton.OK, MessageBoxImage.Error);
            }

            return "";
        }





        /// <summary>
        /// 
        /// </summary>
        class LoginContent
        {
            public string username { get; set; }
            public string password { get; set; }
        }


        /// <summary>
        /// 
        /// </summary>
        class ResponseContent
        {
            public string code { get; set; }
            public Object[] content { get; set; }
        }


        /// <summary>
        /// 
        /// </summary>
        class LoginInfoContent
        {
            public string username { get; set; }
            public string rol { get; set; }
            public string last_name { get; set; }
            public string first_name { get; set; }
        }


        /// <summary>
        /// 
        /// </summary>
        public async void getExample()
        {
            try
            {
                using (var client = new HttpClient())
                {
                    client.BaseAddress = new Uri(multimediarepoRESTUrl);
                    client.DefaultRequestHeaders.Accept.Clear();
                    client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                    // HTTP GET   await
                    HttpResponseMessage response =  client.GetAsync("tests/2/123123").Result;
                    if (response.IsSuccessStatusCode)
                    {
                        string res = await response.Content.ReadAsStringAsync();
                        
                    }
                    else
                    {
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error message: " + ex.Message, "SoftCare Desktop", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }


        /// <summary>
        /// 
        /// </summary>
        public async void postExample()
        {
            try
            {
                using (var client = new HttpClient())
                {
                    client.BaseAddress = new Uri(multimediarepoRESTUrl);
                    client.DefaultRequestHeaders.Accept.Clear();
                    client.DefaultRequestHeaders.Accept.Add(new MediaTypeWithQualityHeaderValue("application/json"));

                    // HTTP POST
                    var up = new LoginContent() { username = "rsucasas", password = "atos1977"};
                    HttpResponseMessage response = await client.PostAsJsonAsync("auth/login", up);
                    if (response.IsSuccessStatusCode)
                    {
                        string res = await response.Content.ReadAsStringAsync();

                        // {:code '1', :content ({:rol 'admin', :username 'rsucasas', ...})}
                        ResponseContent resp = JsonConvert.DeserializeObject<ResponseContent>(res);

                        string res0 = resp.content[0].ToString();

                        LoginInfoContent res2 = JsonConvert.DeserializeObject<LoginInfoContent>(res0);
                    }
                    else
                    {
                    }
                }
            }
            catch (Exception ex)
            {
                MessageBox.Show("Error message: " + ex.Message, "SoftCare Desktop", MessageBoxButton.OK, MessageBoxImage.Error);
            }
        }


    }


}
