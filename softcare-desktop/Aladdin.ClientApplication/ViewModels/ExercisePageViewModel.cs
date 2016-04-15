using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Windows;

namespace SoftCare.ClientApplication.ViewModels
{
    public class ExercisePageViewModel
    {
        SoftCare.ClientApplication.aladdinService.Task ActiveTask;

        public string Url { get; set; }

        public string Text { get; set; }

        public ExercisePageViewModel(SoftCare.ClientApplication.aladdinService.Task activeTask)
        {
            this.ActiveTask = activeTask;
            if (this.ActiveTask != null && this.ActiveTask.URL != null)
            {
                this.Url = this.ActiveTask.URL;
                this.Text = this.ActiveTask.Text;
                using (SoftCare.ClientApplication.aladdinService.StorageComponentImplService sc = new SoftCare.ClientApplication.aladdinService.StorageComponentImplService())
                {
                    if (this.ActiveTask.ID != null)
                    {
                        SoftCare.ClientApplication.aladdinService.OperationResult taskChangeStatus = sc.ChangeTaskStatus(Convert.ToInt32(this.ActiveTask.ID), (int)Config.TaskStatusEnum.Completed, App.CurrentUserID);
                    }
                }
            }
        }
    }
}
