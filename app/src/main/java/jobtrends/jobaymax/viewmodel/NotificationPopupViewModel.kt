package jobtrends.jobaymax.viewmodel

import android.app.Dialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import jobtrends.jobaymax.R
import jobtrends.jobaymax.databinding.NotificationPopupViewBinding

class NotificationPopupViewModel : DialogFragment()
{
  override fun onCreateDialog(savedInstanceState : Bundle?) : Dialog
  {
    val builder = AlertDialog.Builder(this.context !!)
    // Get the layout inflater
    val inflater = activity !!.layoutInflater
    // Inflate and set the layout for the dialog
    // Pass null as the parent view because its going in the dialog layout
    builder.setView(inflater.inflate(R.layout.notification_popup_view, null))
    return builder.create()
  }

  override fun onCreateView(inflater : LayoutInflater, container : ViewGroup?,
                            savedInstanceState : Bundle?) : View?
  {
    val binding : NotificationPopupViewBinding = DataBindingUtil
        .inflate(inflater, R.layout.notification_popup_view, container, false)
    val view = binding.root
    binding.vm = this
    return view
  }
}
