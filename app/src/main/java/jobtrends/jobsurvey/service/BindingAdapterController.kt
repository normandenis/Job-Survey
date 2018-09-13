package jobtrends.jobsurvey.service

import android.databinding.BindingAdapter
import android.databinding.ObservableField
import android.widget.EditText
import android.widget.ListAdapter
import android.widget.ListView
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

@BindingAdapter("app:faqAdapter")
fun bindItems(view: ListView, viewModel: ListAdapter?)
{
    view.adapter = viewModel
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

