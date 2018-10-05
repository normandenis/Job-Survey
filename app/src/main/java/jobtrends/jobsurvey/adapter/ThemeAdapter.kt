package jobtrends.jobsurvey.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.databinding.DataBindingUtil
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentTransaction
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.dagger.App
import jobtrends.jobsurvey.databinding.AdapterThemeBinding
import jobtrends.jobsurvey.fragment.SurveyFragment
import jobtrends.jobsurvey.model.SurveyModel
import jobtrends.jobsurvey.model.ThemeModel
import jobtrends.jobsurvey.model.UserAnswerModel
import jobtrends.jobsurvey.service.ApiController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.ServiceController
import javax.inject.Inject

class ThemeAdapter(private val list: List<ThemeModel?>?, fragmentManager: FragmentManager?) : BaseAdapter() {
    @Inject
    lateinit var apiController: ApiController
    @Inject
    lateinit var jsonController: JsonController
    @Inject
    lateinit var context: Context
    @Inject
    lateinit var serviceController: ServiceController

    private val fragmentManager: FragmentManager
    private val tag: String
    private var layoutInflater: LayoutInflater?

    init {
        App.component.inject(this)
        this.fragmentManager = fragmentManager!!
        tag = "ThemeAdapter"
        layoutInflater = null
    }

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        if (layoutInflater == null) {
            layoutInflater = parent?.context?.getSystemService(
                    Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        }
        val binding: AdapterThemeBinding =
                DataBindingUtil.inflate(layoutInflater!!, R.layout.adapter_theme, parent, false)
        binding.themeAdapter = this
        binding.themeModel = list?.get(position)
        return binding.root
    }

    override fun getItem(position: Int): Any? {
        return list?.get(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        if (list?.size == null) {
            return 0
        }
        return list.size
    }

    fun onClickTheme(theme: ThemeModel) {
        val id = theme.surveyId?.get()
        apiController.get("surveys/$id", ::jobaymaxSurveyIdReply)
    }

    private fun jobaymaxSurveyIdReply(code: Int, body: String) {
        if (code != 200 && code != 201) {
            displayError(tag, body)
            return
        }
        val survey: SurveyModel = jsonController.deserialize(body)
        val userAnswerModel = UserAnswerModel()
        val fragment = SurveyFragment()
        fragment.survey = survey
        fragment.userAnswerModel = userAnswerModel
        navTo(fragment)
    }

    private fun navTo(fragment: Fragment) {
        try {
            val transaction: FragmentTransaction = fragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
            transaction.commit()
        } catch (exception: Exception) {
            Log.e(tag, exception.message)
        }
    }


    fun isVisible(open: Boolean?): Int? {
        return if (open == true) View.INVISIBLE else View.VISIBLE
    }
}