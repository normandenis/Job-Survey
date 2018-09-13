package jobtrends.jobsurvey.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.databinding.ThemeAdapterViewBinding
import jobtrends.jobsurvey.model.SurveyModel
import jobtrends.jobsurvey.model.ThemeModel
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController
import jobtrends.jobsurvey.viewmodel.SurveyViewModel

class ThemeAdapter : BaseAdapter
{
    private val _apiController: APIController?
    private val _jsonController: JsonController?
    private var _inflater: LayoutInflater?
    private var _view: View?
    private val _fragmentManager: FragmentManager?
    private val _tag: String?
    private val _list: List<ThemeModel?>?

    constructor(list: List<ThemeModel?>?, fragmentManager: FragmentManager?) : super()
    {
        _list = list
        _fragmentManager = fragmentManager
        _tag = "ThemeAdapter"
        _apiController = serviceController?.getInstance()
        _jsonController = serviceController?.getInstance()
        _view = null
        _inflater = null
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View?
    {
        if (_inflater == null)
        {
            _inflater = parent?.context?.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        val binding: ThemeAdapterViewBinding? =
                DataBindingUtil.inflate(_inflater!!, R.layout.theme_adapter_view, parent, false)
        binding?.vm = this
        binding?.m = _list?.get(position)

        _view = binding?.root

        return _view
    }

    override fun getItem(position: Int): Any?
    {
        return _list?.get(position)
    }

    override fun getItemId(position: Int): Long
    {
        return position.toLong()
    }

    override fun getCount(): Int
    {
        return _list?.size!!
    }

    fun onClickTheme(theme: ThemeModel)
    {
        val id = theme.surveyId?.get()
        _apiController?.get("surveys/$id", ::jobaymaxSurveyIdReply, _view?.context)
    }

    private fun jobaymaxSurveyIdReply(code: Int?, body: String?)
    {
        val msg = "$code: $body"
        Log.d(_tag, msg)
        val survey: SurveyModel? = _jsonController?.deserialize(body)
        val fragment = SurveyViewModel()
        fragment.survey = survey
        val transaction = _fragmentManager?.beginTransaction()
        transaction?.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }

    fun isVisible(open: Boolean?): Int?
    {
        return if (open == true) View.INVISIBLE else View.VISIBLE
    }
}