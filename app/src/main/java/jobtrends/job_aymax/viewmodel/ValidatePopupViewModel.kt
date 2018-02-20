package jobtrends.job_aymax.viewmodel

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface.OnCancelListener

class ValidatePopupViewModel : Dialog
{
	constructor(context : Context?) : super(context)
	constructor(context : Context?, themeResId : Int) : super(context, themeResId)
	constructor(context : Context?, cancelable : Boolean, cancelListener : OnCancelListener?) : super(context, cancelable, cancelListener)
}
