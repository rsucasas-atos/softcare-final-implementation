using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace SoftCare.ClientApplication.ViewModels
{
    class LastWizardPageViewModel
    {
        public QuestionnaireWizard Wizard { get; set; }

        public LastWizardPageViewModel(QuestionnaireWizard wizard)
        {
            this.Wizard = wizard;
        }
    }
}
