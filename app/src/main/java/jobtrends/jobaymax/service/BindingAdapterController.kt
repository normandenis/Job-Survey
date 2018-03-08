package jobtrends.jobaymax.service

import android.databinding.BindingAdapter
import android.databinding.BindingConversion
import android.view.View
import android.widget.ListAdapter
import android.widget.ListView

@BindingAdapter("app:adapter")
fun bindItems(view : ListView, viewModel : ListAdapter?)
{
  view.adapter = viewModel
}

@BindingConversion
fun convertToVisibility(boolean : Boolean) : Int
{
  return if (boolean) View.VISIBLE else View.INVISIBLE
}