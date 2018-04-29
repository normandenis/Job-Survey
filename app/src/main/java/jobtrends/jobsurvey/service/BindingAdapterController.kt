package jobtrends.jobsurvey.service

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.databinding.ObservableField
import android.support.design.widget.TextInputLayout
import android.view.View
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.ListView
import android.text.Editable
import android.text.TextUtils



@BindingAdapter("app:adapter")
fun bindItems(view: ListView, viewModel: ListAdapter?)
{
  view.adapter = viewModel
}

@BindingConversion
fun convertToVisibility(boolean: Boolean): Int
{
  return if (boolean) View.VISIBLE else View.INVISIBLE
}

@BindingAdapter("app:errorText")
fun setErrorMessage(editText: EditText, message: String?)
{
  if (message == null || message == "")
  {
    return
  }
  editText.error = message
}