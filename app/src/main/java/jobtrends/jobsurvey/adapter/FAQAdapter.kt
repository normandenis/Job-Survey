package jobtrends.jobsurvey.adapter

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
import jobtrends.jobsurvey.databinding.FaqAdapterViewBinding
import jobtrends.jobsurvey.model.FAQModel

class FAQAdapter : BaseAdapter, Filterable {
    private var _list: List<FAQModel?>?
    private var _displayedList: List<FAQModel?>?
    private var _inflater: LayoutInflater?

    constructor(list: List<FAQModel?>?) : super() {
        _list = ArrayList(list)
        _displayedList = ArrayList(list)
        _inflater = null
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        if (_inflater == null) {
            _inflater = parent?.context?.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        val binding: FaqAdapterViewBinding? = DataBindingUtil.inflate(_inflater!!, R.layout.faq_adapter_view, parent, false)
        binding?.vm = this
        binding?.m = _displayedList?.get(position)

        return binding?.root
    }

    override fun getItem(position: Int): Any? = _displayedList?.get(position)

    override fun getItemId(position: Int): Long = position.toLong()

    override fun getCount(): Int = _displayedList?.size!!

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun publishResults(constraint: CharSequence, results: FilterResults) {
                _displayedList = results.values as ArrayList<FAQModel>
                notifyDataSetChanged()
            }

            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var constraint = constraint
                val results = FilterResults()
                val filteredArrList = ArrayList<FAQModel>()

                if (_list == null) {
                    _list = ArrayList(_displayedList)
                }

                if (constraint == null || constraint.isEmpty()) {
                    // set the Original result to return
                    results.count = _list?.size!!
                    results.values = _list
                } else {
                    constraint = "$constraint".toLowerCase()
                    for (i in 0 until _list?.size!!) {
                        val data = _list!![i]?.question
                        if (data?.toLowerCase()?.startsWith("$constraint")!!) {
                            filteredArrList.add(FAQModel(_list!![i]?.question))
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
