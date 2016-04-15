using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.IO;
using System.Linq;


namespace SoftCare.ClientApplication.ViewModels
{

    /// <summary>
    /// 
    /// </summary>
    public class MeasureActivityViewModel : INotifyPropertyChanged
    {


        #region INotifyPropertyChanged Members

        public event PropertyChangedEventHandler PropertyChanged;

        protected void SendPropertyChanged(string propertyName)
        {
            if (this.PropertyChanged != null)
                this.PropertyChanged(this, new PropertyChangedEventArgs(propertyName));
        }

        #endregion

        private const int MAX_DAY = 35;
        SoftCare.ClientApplication.aladdinService.Task ActiveTask;
        Aladdin.Omron dev;
        double[,] _Activity = new double[MAX_DAY, 24];
        bool hasData = false;
        bool manuallyStepsDay = false;
        int manuallyDataValue = 0;

        public double[,] Activity
        {
            get { return _Activity; }
            set
            {
                _Activity = value;
                this.SendPropertyChanged("Activity");
            }
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="activeTask"></param>
        public MeasureActivityViewModel(SoftCare.ClientApplication.aladdinService.Task activeTask)
        {
            this.ActiveTask = activeTask;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        internal bool OmronConnect()
        {
            try
            {
                this.dev = new Aladdin.Omron();
                return true;
            }
            catch (Exception)
            {
                return false;
            }
        }


        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        internal bool OmronReadData()
        {
            try
            {
                Aladdin.omron_pd_count_info c = this.dev.omron_get_pd_data_count();
                if (c.daily_count > 0)
                {
                    Aladdin.omron_pd_hourly_data[] steps;
                    for (int day = 1; day < Math.Min(c.daily_count, MAX_DAY); ++day)
                    {
                        steps = this.dev.omron_get_pd_hourly_data(day);
                        for (int hour = 0; hour < 24; ++hour)
                            this.Activity[day, hour] = steps[hour].regular_steps;
                    }
                    hasData = true;
                    return true;
                }
                else
                    return false;
            }
            catch (Exception)
            {
                return false;
            }
        }


        /// <summary>
        /// 
        /// </summary>
        /// <param name="var"></param>
        /// <param name="value"></param>
        internal void manuallyDataAvailable(bool var, int value) 
        {
            hasData = var;
            manuallyStepsDay = var;
            manuallyDataValue = value;
        }


        /// <summary>
        /// 
        /// </summary>
        /// <returns></returns>
        internal double GetTotaleSteps()
        {
            double tot = 0;

            for (int day = 0; day < MAX_DAY; ++day)
            {
                for (int hour = 0; hour < 24; ++hour)
                    tot += this.Activity[day, hour];
            }

            return tot;
        }


        /// <summary>
        /// 
        /// </summary>
        internal void SendMeasurements()
        {
            SoftCare.ClientApplication.aladdinService.SystemParameter measurementType = new SoftCare.ClientApplication.aladdinService.SystemParameter();
            measurementType.Code = Convert.ToString((int)Config.MeasurementTypeEnum.Activity);
            measurementType.Description = "Activity";

            if (manuallyStepsDay)
            {
                SoftCare.ClientApplication.aladdinService.Measurement Measurement = new SoftCare.ClientApplication.aladdinService.Measurement();

                Measurement.DateTime = App.getTaskById(this.ActiveTask.ID).DateTimeAssigned; ;
                Measurement.TaskID = this.ActiveTask.ID;
                Measurement.Type = measurementType;
                Measurement.Value = manuallyDataValue;
                Measurement.Units = "steps/day";
                Measurement.LowerLimitSpecified = false;
                Measurement.UpperLimitSpecified = false;

                SoftCare.ClientApplication.aladdinService.Measurement[] data = new SoftCare.ClientApplication.aladdinService.Measurement[1];
                data[0] = Measurement;

                SoftCare.ClientApplication.aladdinService.StorageComponentImplService sc = new SoftCare.ClientApplication.aladdinService.StorageComponentImplService();
                SoftCare.ClientApplication.aladdinService.OperationResult res = sc.StoreMeasurements(data, App.CurrentUserID);
                if (res.Status == 1)
                {
                    SoftCare.ClientApplication.aladdinService.OperationResult taskChangeStatus = sc.ChangeTaskStatus(Convert.ToInt32(this.ActiveTask.ID), (int)Config.TaskStatusEnum.Completed, App.CurrentUserID);
                }

                AppCommands.MoveToPageCommand.Execute("MyTasksPage", null);
            }
            else if (this.Activity.Length > 0)
            {
                SoftCare.ClientApplication.aladdinService.StorageComponentImplService sc = new SoftCare.ClientApplication.aladdinService.StorageComponentImplService();
                List<SoftCare.ClientApplication.aladdinService.Measurement> dataList = new List<SoftCare.ClientApplication.aladdinService.Measurement>(); // We use a dynamic array first, we still don't know how many (new) measurments we have to submit

                List<string> dateLog = new List<string>(); // List of days for which we have already sent the data (history)
                List<string> dateLogNew = new List<string>(); // List of days for which we have new data to send (will be added to the history at the end)
                string date;

                // Load the history
                string logFile = Environment.GetFolderPath(Environment.SpecialFolder.LocalApplicationData) + "\\activity.log";
                if (File.Exists(logFile))
                {
                    StreamReader r = new StreamReader(logFile);
                    while ((date = r.ReadLine()) != null)
                        dateLog.Add(date);
                    r.Close();
                }

                for (int day = 0; day < MAX_DAY; ++day)
                {
                    for (int hour = 0; hour < 24; ++hour)
                    {
                        // Build the timestamp of this measurement
                        DateTime dt = new DateTime(System.DateTime.Now.Year, System.DateTime.Now.Month, System.DateTime.Now.Day, hour, 0, 0, 0);
                        dt = dt.AddDays(-1 - day);

                        // Process the measurement only if its day is not present in the history
                        if (dateLog.IndexOf(String.Format("{0:d/M/yyyy}", dt)) == -1)
                        {
                            SoftCare.ClientApplication.aladdinService.Measurement Measurement = new SoftCare.ClientApplication.aladdinService.Measurement();

                            Measurement.DateTime = dt;
                            Measurement.TaskID = this.ActiveTask.ID;
                            Measurement.Type = measurementType;
                            Measurement.Value = this.Activity[day, hour];
                            Measurement.Units = "steps/day";
                            Measurement.LowerLimitSpecified = false;
                            Measurement.UpperLimitSpecified = false;
                    
                            dataList.Add(Measurement);

                            // Update the list of days with new data
                            date = String.Format("{0:d/M/yyyy}", dt);
                            if (dateLogNew.IndexOf(date) == -1)
                                dateLogNew.Add(date);
                        }
                    }
                }

                // Create an array (StoreMeasurements requires an array parameter) with the right size and fill it with the measurements
                SoftCare.ClientApplication.aladdinService.Measurement[] data = new SoftCare.ClientApplication.aladdinService.Measurement[dataList.Count()];
                for (int i = 0; i < dataList.Count(); ++i)
                    data[i] = dataList[i];

                SoftCare.ClientApplication.aladdinService.OperationResult res = sc.StoreMeasurements(data, App.CurrentUserID);
                if (res.Status == 1)
                {
                    SoftCare.ClientApplication.aladdinService.OperationResult taskChangeStatus = sc.ChangeTaskStatus(Convert.ToInt32(this.ActiveTask.ID), (int)Config.TaskStatusEnum.Completed, App.CurrentUserID);

                    // Update the history
                    StreamWriter sw;
                    sw = File.AppendText(logFile);
                    foreach (string s in dateLogNew)
                        sw.WriteLine(s);
                    sw.Close();
                }

                AppCommands.MoveToPageCommand.Execute("MyTasksPage", null);
            }
            
        }


        // Activity limits: activity data have to be read from measurement device
        internal bool CanSendMeasurements()
        {
            return hasData;
        }


    }


}
