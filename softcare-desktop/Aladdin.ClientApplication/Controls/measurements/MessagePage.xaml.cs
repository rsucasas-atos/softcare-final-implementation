using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace SoftCare.ClientApplication.Controls
{
    /// <summary>
    /// Interaction logic for MessagePage.xaml
    /// </summary>
    public partial class MessagePage : UserControl
    {

        public string MessageText { get; set; }

        public MessagePage(SoftCare.ClientApplication.aladdinService.Task activeTask)
        {
            this.MessageText = activeTask.Text;
            this.DataContext = this;
            InitializeComponent();
        }
    }
}
