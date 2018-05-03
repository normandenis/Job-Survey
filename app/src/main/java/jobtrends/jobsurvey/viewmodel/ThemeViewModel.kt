package jobtrends.jobsurvey.viewmodel

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
import jobtrends.jobsurvey.databinding.ListviewStartSurveyViewBinding
import jobtrends.jobsurvey.model.Survey
import jobtrends.jobsurvey.model.Theme
import jobtrends.jobsurvey.service.APIController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.serviceController

class ThemeViewModel : BaseAdapter
{
  private val _apiController: APIController?
  private val _jsonController: JsonController?
  private var _inflater: LayoutInflater?
  private var _view: View?
  private val _fragmentManager: FragmentManager?
  private val _tag: String?
  private val _list: List<Theme>?

  constructor(list: List<Theme>, fragmentManager: FragmentManager?) : super()
  {
    _list = list
    _fragmentManager = fragmentManager
    _tag = "ThemeViewModel"
    _apiController = serviceController!!.getInstance()
    _jsonController = serviceController!!.getInstance()
    _view = null
    _inflater = null
  }

  @SuppressLint("ViewHolder")
  override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View?
  {
    if (_inflater == null)
    {
      _inflater = parent!!.context.getSystemService(
        Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }
    val binding: ListviewStartSurveyViewBinding? = DataBindingUtil
      .inflate(_inflater!!, R.layout.listview_start_survey_view, parent, false)
    binding!!.vm = this
    binding.m = _list!![position]

    _view = binding.root

    return _view
  }

  override fun getItem(position: Int): Any?
  {
    return _list!![position]
  }

  override fun getItemId(position: Int): Long
  {
    return position.toLong()
  }

  override fun getCount(): Int
  {
    return _list!!.size
  }

  fun onClickTheme(theme: Theme)
  {
    val id = theme.survey_id
    _apiController!!.get("jobaymax/survey/$id", ::jobaymaxSurveyIdReply, _view!!.context)
  }

  private fun jobaymaxSurveyIdReply(code: Int?, body: String?)
  {
    val msg = "$code: $body"
    Log.d(_tag, msg)
    val survey = _jsonController!!.deserialize<Survey>(body)
    val fragment = SurveyViewModel()
    fragment.survey = survey
    val transaction = _fragmentManager!!.beginTransaction()
    transaction.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
    transaction.addToBackStack(null)
    transaction.commit()
  }

  fun isVisible(open: Boolean?): Int?
  {
    return if (open == true) View.INVISIBLE else View.VISIBLE
  }
}