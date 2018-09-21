package jobtrends.jobsurvey.service

import android.databinding.BindingAdapter
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.ListView

@BindingAdapter("app:faqAdapter")
fun bindItems(view: ListView, viewModel: ListAdapter?) {
    view.adapter = viewModel
}

@BindingAdapter("app:errorText")
fun setErrorMessage(editText: EditText, message: String?) {
    if (message == null && message == "") {
        return
    }
    editText.error = message
}

