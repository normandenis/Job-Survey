package jobtrends.jobsurvey.viewmodel

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface.OnCancelListener

class CancelPopupViewModel : Dialog
{
  constructor(context : Context?) : super(context)
  constructor(context : Context?, themeResId : Int) : super(context, themeResId)
  constructor(context : Context?, cancelable : Boolean, cancelListener : OnCancelListener?) : super(
      context, cancelable, cancelListener)
}
