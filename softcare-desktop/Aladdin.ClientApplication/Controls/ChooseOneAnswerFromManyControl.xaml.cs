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
using Aladdin.DataModel;
using SoftCare.ClientApplication.ViewModels;

namespace SoftCare.ClientApplication.Controls
{
    /// <summary>
    /// Interaction logic for ChooseOneAnswerFromManyControl.xaml
    /// </summary>
    public partial class ChooseOneAnswerFromManyControl : UserControl
    {
        ChooseOneAnswerFromManyControlViewModel ViewModel { get; set; }

        public ChooseOneAnswerFromManyControl()
        {
            InitializeComponent();
        }

        public ChooseOneAnswerFromManyControl(QuestionnaireWizard wizard)
        {
            this.ViewModel = new ChooseOneAnswerFromManyControlViewModel(wizard);
            this.DataContext = this.ViewModel;
            InitializeComponent();
        }

        public void FillAnswers(QuestionnaireQuestion question)
        {
            this.AnswersStackPanel.Children.Clear();
            foreach (QuestionnaireQuestionAnswer answer in question.Answers)
            {
                RadioButton rb = new RadioButton { MinWidth = 100, Content = answer.Answer, Tag = answer.Value };
                rb.Click += new RoutedEventHandler(RadioButton_Click);

                this.AnswersStackPanel.Children.Add(rb);
            }
        }

        void RadioButton_Click(object sender, RoutedEventArgs e)
        {
            RadioButton rb = sender as RadioButton;
            if (rb != null)
            {
                string answer = rb.Tag as string;
                if (!string.IsNullOrEmpty(answer))
                    this.ViewModel.SetAnswer(answer);
            }
        }
    }

}