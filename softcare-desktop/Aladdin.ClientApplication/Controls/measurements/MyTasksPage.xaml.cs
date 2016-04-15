using System;
using System.Windows;
using System.Linq;
using System.Windows.Controls;
using System.Windows.Input;


namespace SoftCare.ClientApplication.Controls
{


    /// <summary>
    /// Interaction logic for MyTasksPage.xaml
    /// </summary>
    public partial class MyTasksPage : UserControl
    {


        /// <summary>
        /// 
        /// </summary>
        public MyTasksPage()
        {
            InitializeComponent();
        }


        /// <summary>
        /// 
        /// </summary>
        private void GetMyTasks()
        {
            Cursor = Cursors.Wait;
            try
            {
                this.TaskPanel.Children.Clear();
                SoftCare.ClientApplication.aladdinService.StorageComponentImplService sc = new SoftCare.ClientApplication.aladdinService.StorageComponentImplService();
                DateTime today = System.DateTime.Now;
                DateTime yesterday = DateTime.Now.AddDays(-31);
                DateTime dateFrom = new DateTime(yesterday.Year, yesterday.Month, yesterday.Day, 0, 0, 0);
                DateTime dateTo = new DateTime(today.Year, today.Month, today.Day, 23, 59, 59);
                

                App.ActiveTasks = sc.GetUserPlannedTasks(App.CurrentUserID, dateFrom, dateTo, App.DefaultLocale, App.CurrentUserID).ToList();

                // fix for different time zones
                foreach (var item in App.ActiveTasks)
                {
                    item.DateTimeAssigned = item.DateTimeAssigned.AddHours(3);    
                }

                // Remove canceled tasks
                App.ActiveTasks = App.ActiveTasks.Where(c => Convert.ToInt32(c.TaskStatus.Code) != (int)Config.TaskStatusEnum.Canceled).ToList();
                // Remove completed tasks
                App.ActiveTasks = App.ActiveTasks.Where(c => Convert.ToInt32(c.TaskStatus.Code) != (int)Config.TaskStatusEnum.Completed).ToList();
                // Remove future tasks
                App.ActiveTasks = App.ActiveTasks.Where(c => c.DateTimeAssigned <= DateTime.Now.AddHours(12)).ToList();
                // Remove tasks assigned the day before, if completed
                string[] yesterdaysCompletedTasksIDs = App.ActiveTasks.Where(c => c.DateTimeAssigned.Year == dateFrom.Year && c.DateTimeAssigned.Month == dateFrom.Month && c.DateTimeAssigned.Day == dateFrom.Day && (Convert.ToInt32(c.TaskStatus.Code) == (int)Config.TaskStatusEnum.Completed || Convert.ToInt32(c.TaskType.Code) == (int)Config.TaskTypesEnum.Message)).Select(c => c.ID).ToArray();

                if (yesterdaysCompletedTasksIDs.Length > 0)
                    App.ActiveTasks = App.ActiveTasks.Where(c => !yesterdaysCompletedTasksIDs.Contains(c.ID)).ToList();

                int num = 1;
                foreach (SoftCare.ClientApplication.aladdinService.Task task in App.ActiveTasks)
                {
                    TaskControl taskControl = new TaskControl();
                    taskControl.Task = task;
                    taskControl.Number = num++;
                    taskControl.Date = task.DateTimeAssigned.ToShortDateString();
                    int taskType = Convert.ToInt32(task.TaskType.Code);
                    switch (taskType)
                    {
                        case (int)Config.TaskTypesEnum.PatientQuestionnaire:
                            taskControl.SetResourceReference(TaskControl.TitleProperty, "PatientQuestionnaireTaskTitle");
                            break;
                        case (int)Config.TaskTypesEnum.CarerQuestionnaire:
                            taskControl.SetResourceReference(TaskControl.TitleProperty, "CarerQuestionnaireTaskTitle");
                            break;
                        case (int)Config.TaskTypesEnum.BloodPressureMeasurement:
                            taskControl.SetResourceReference(TaskControl.TitleProperty, "MeasureBloodPressureTaskTitle");
                            break;
                        case (int)Config.TaskTypesEnum.WeightMeasurement:
                            taskControl.SetResourceReference(TaskControl.TitleProperty, "MeasureWeightTaskTitle");
                            break;
                        case (int)Config.TaskTypesEnum.CognitiveGame:
                            taskControl.SetResourceReference(TaskControl.TitleProperty, "PlayGamesTaskTitle");
                            break;
                        case (int)Config.TaskTypesEnum.ActivityMonitor:
                            taskControl.SetResourceReference(TaskControl.TitleProperty, "MeasureActivityTaskTitle");
                            break;
                        case (int)Config.TaskTypesEnum.Exercise:
                            taskControl.SetResourceReference(TaskControl.TitleProperty, "ExerciseTaskTitle");
                            break;
                        case (int)Config.TaskTypesEnum.Message:
                            taskControl.SetResourceReference(TaskControl.TitleProperty, "MessageTitle");
                            break;
                        default:
                            break;
                    }

                    int taskStatus = Convert.ToInt32(task.TaskStatus.Code);

                    switch (taskStatus)
                    {
                        case (int)Config.TaskStatusEnum.Pending:
                            taskControl.IsPending = true;
                            break;
                        case (int)Config.TaskStatusEnum.Completed:
                            taskControl.IsPending = false;
                            break;
                        default:
                            break;
                    }

                    this.TaskPanel.Children.Add(taskControl);

                }

            }
            catch (Exception ex)
            {
                string msg = ex.Message;
            }

            Cursor = Cursors.Arrow;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="sender"></param>
        /// <param name="e"></param>
        private void UserControl_Loaded(object sender, RoutedEventArgs e)
        {
            GetMyTasks();
        }


    }


}
