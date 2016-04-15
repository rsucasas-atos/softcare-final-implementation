using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;


namespace SoftCare.ClientApplication.ViewModels
{


    class ContentPageViewModel
    {

        public string Url { get; set; }

        public string Text { get; set; }

        public string Title { get; set; }

        public string Category { get; set; }

        public ContentPageViewModel(SoftCare.ClientApplication.Controls.MediaContent content)
        {
            this.Url = content.url;
            this.Text = content.text;
            this.Title = content.title;
            this.Category = content.category;
        }

    }


}