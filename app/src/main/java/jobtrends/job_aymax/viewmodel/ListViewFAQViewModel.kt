package jobtrends.job_aymax.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import jobtrends.job_aymax.R
import jobtrends.job_aymax.databinding.ListviewFaqViewBinding
import jobtrends.job_aymax.model.FAQModel

class ListViewFAQViewModel(var list : List<FAQModel>) : BaseAdapter(), Filterable
{
	var listdisplayed : List<FAQModel> = list
	var inflater : LayoutInflater? = null

	@SuppressLint("ViewHolder")
	override fun getView(position : Int, convertView : View?, parent : ViewGroup?) : View
	{
		if (inflater == null)
		{
			inflater = parent?.context?.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
		}

		val binding : ListviewFaqViewBinding = DataBindingUtil.inflate(inflater, R.layout.listview_faq_view, parent, false)
		binding.m = listdisplayed[position]

		return binding.root
	}

	override fun getItem(position : Int) : Any
	{
		return listdisplayed[position]
	}

	override fun getItemId(position : Int) : Long
	{
		return position.toLong()
	}

	override fun getCount() : Int
	{
		return listdisplayed.size
	}

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
				val FilteredArrList = ArrayList<FAQModel>()

				if (list == null)
				{
					list = ArrayList<FAQModel>(listdisplayed) // saves the original data in mOriginalValues
				}

				/********
				 *
				 * If constraint(CharSequence that is received) is null returns the mOriginalValues(Original) values
				 * else does the Filtering and returns FilteredArrList(Filtered)
				 *
				 */
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
							FilteredArrList.add(FAQModel(list[i].question))
						}
					}
					// set the Filtered result to return
					results.count = FilteredArrList.size
					results.values = FilteredArrList
				}
				return results
			}
		}
	}

}
