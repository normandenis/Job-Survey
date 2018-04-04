package jobtrends.jobsurvey.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.ListviewFaqViewBinding
import jobtrends.jobsurvey.model.FAQModel

class ListViewFAQViewModel(var list : List<FAQModel>) : BaseAdapter(), Filterable
{
  var listdisplayed : List<FAQModel> = list
  var inflater : LayoutInflater? = null
  @SuppressLint("ViewHolder")
  override fun getView(position : Int, convertView : View?, parent : ViewGroup?) : View
  {
    if (this.inflater == null)
    {
      inflater = parent?.context?.getSystemService(
          Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
    val binding : ListviewFaqViewBinding = DataBindingUtil
        .inflate(inflater!!, R.layout.listview_faq_view, parent, false)
    binding.m = listdisplayed[position]

    return binding.root
  }

  override fun getItem(position : Int) : Any = this.listdisplayed[position]
  override fun getItemId(position : Int) : Long = position.toLong()
  override fun getCount() : Int = this.listdisplayed.size
  override fun getFilter() : Filter
  {
    return object : Filter()
    {
      override fun publishResults(constraint : CharSequence, results : FilterResults)
      {
        listdisplayed = results.values as ArrayList<FAQModel> // has the filtered values
        notifyDataSetChanged()  // notifies the data with new filtered values
      }

      override fun performFiltering(constraint : CharSequence?) : FilterResults
      {
        var constraint = constraint
        val results = FilterResults()        // Holds the results of a filtering operation in values
        val filteredArrList = ArrayList<FAQModel>()

        if (list == null)
        {
          list = ArrayList<FAQModel>(listdisplayed)
        }

        if (constraint == null || constraint.isEmpty())
        {
          // set the Original result to return
          results.count = list.size
          results.values = list
        }
        else
        {
          constraint = constraint.toString().toLowerCase()
          for (i in 0 until list.size)
          {
            val data = list[i].question
            if (data?.toLowerCase()?.startsWith(constraint.toString()) !!)
            {
              filteredArrList.add(FAQModel(list[i].question))
            }
          }
          // set the Filtered result to return
          results.count = filteredArrList.size
          results.values = filteredArrList
        }
        return results
      }
    }
  }
}
