package jobtrends.jobsurvey.fragment

import android.app.Dialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.ViewPager
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import jobtrends.jobsurvey.R
import jobtrends.jobsurvey.adapter.PagerAdapter
import jobtrends.jobsurvey.adapter.displayError
import jobtrends.jobsurvey.dagger.App
import jobtrends.jobsurvey.databinding.DialogCancelBinding
import jobtrends.jobsurvey.databinding.DialogValidateBinding
import jobtrends.jobsurvey.databinding.FragmentSurveyBinding
import jobtrends.jobsurvey.model.*
import jobtrends.jobsurvey.service.ApiController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.ServiceController
import javax.inject.Inject

class SurveyFragment : Fragment() {
    @Inject
    lateinit var apiController: ApiController
    @Inject
    lateinit var jsonController: JsonController
    @Inject
    lateinit var serviceController: ServiceController

    private lateinit var pagerAdapter: PagerAdapter
    private lateinit var dialog: Dialog

    lateinit var userAnswerModel: UserAnswerModel
    lateinit var survey: SurveyModel

    private val resultModel: ResultModel
    private val appBarBtn: Button

    init {
        App.component.inject(this)
        resultModel = ResultModel()
        serviceController.register(resultModel, true)
        appBarBtn = serviceController.getInstance()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (survey.questions?.size == 1) {
            appBarBtn.setBackgroundResource(R.drawable.ic_check_accent_32dp)
            appBarBtn.setOnClickListener { onValidateSurvey() }
        } else {
            appBarBtn.setBackgroundResource(R.drawable.ic_close_accent_32dp)
            appBarBtn.setOnClickListener { onCloseSurvey() }
        }

        val list: MutableList<Fragment> = mutableListOf()
        resultModel.id?.set(survey.id?.get())
        resultModel.answers = mutableListOf()
        if (survey.questions?.size == null) {
            return
        }
        survey.questions?.mapTo(list, ::newInstance)
        pagerAdapter = PagerAdapter(fragmentManager!!, list)
        val pager: ViewPager = view.findViewById(R.id.pager)
        pager.offscreenPageLimit = survey.questions?.size!!
        pager.adapter = pagerAdapter
        pager.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int,
                                        positionOffset: Float,
                                        positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {
                if (position == list.size - 1) {
                    appBarBtn.setBackgroundResource(R.drawable.ic_check_accent_32dp)
                    appBarBtn.setOnClickListener { onValidateSurvey() }
                } else {
                    appBarBtn.setBackgroundResource(R.drawable.ic_close_accent_32dp)
                    appBarBtn.setOnClickListener { onCloseSurvey() }
                }
            }
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val binding: FragmentSurveyBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_survey, container, false)
        binding.surveyFragment = this
        dialog = Dialog(context!!)
        return binding.root
    }

    private fun newInstance(questionModel: QuestionModel?): Fragment {
        userAnswerModel.question?.set(questionModel?.id?.get())
        val fragment = QuestionFragment(userAnswerModel, questionModel!!)
        resultModel.answers?.add(userAnswerModel)
        return fragment
    }

    fun onValidateSurvey() {
        val bind: DialogValidateBinding = DataBindingUtil
                .inflate(LayoutInflater.from(context), R.layout.dialog_validate,
                        null, false)
        bind.surveyFragment = this
        dialog.setContentView(bind.root)
        dialog.show()
    }

    fun onCloseSurvey() {
        val bind: DialogCancelBinding = DataBindingUtil
                .inflate(LayoutInflater.from(context), R.layout.dialog_cancel,
                        null, false)
        bind.surveyFragment = this
        dialog.setContentView(bind.root)
        dialog.show()
    }

    fun onValideYes() {
        dialog.hide()
        val fragment = ResultFragment()
        navTo(fragment)
    }

    private fun navTo(fragment: Fragment) {
        try {
            val transaction: FragmentTransaction = fragmentManager?.beginTransaction()!!
            transaction.replace(R.id.fragment_app_bar_nav_drawer_0, fragment)
            transaction.commit()
        } catch (exception: Exception) {
            Log.e(tag, exception.message)
        }
    }


    fun onValideNo() {
        dialog.hide()
    }

    fun onCloseYes() {
        dialog.hide()
        apiController.get("me", ::jobaymaxMeReply)
    }

    private fun jobaymaxMeReply(code: Int, body: String) {
        if (code != 200 && code != 201) {
            displayError(tag!!, body)
            return
        }
        val homeModel: HomeModel? = jsonController.deserialize(body)
        serviceController.register(homeModel, true)
        val fragment = ThemeFragment()
        navTo(fragment)
    }

    fun onCloseNo() {
        dialog.hide()
    }
}