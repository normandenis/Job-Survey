package jobtrends.job_aymax.service

import android.databinding.BindingAdapter
import android.widget.ListAdapter
import android.widget.ListView

@BindingAdapter("app:adapter")
fun bindItems(view : ListView, viewModel : ListAdapter?)
{
	view.adapter = viewModel
}