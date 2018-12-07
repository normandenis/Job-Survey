package jobtrends.jobsurvey.adapter

import android.databinding.BindingAdapter
import android.util.Log
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.ListView

@BindingAdapter("app:adapter")
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

fun displayError(tag: String, message: String) {
    try {
        throw Exception()
    } catch (exception: Exception) {
        Log.e(tag, if (message == "") exception.message else message)
    }
}

