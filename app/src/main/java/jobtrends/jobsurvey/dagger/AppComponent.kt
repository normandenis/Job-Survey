package jobtrends.jobsurvey.dagger

import dagger.Component
import jobtrends.jobsurvey.service.ApiController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.ServiceController
import jobtrends.jobsurvey.activity.*
import jobtrends.jobsurvey.adapter.AnswerAdapter
import jobtrends.jobsurvey.adapter.ThemeAdapter
import jobtrends.jobsurvey.fragment.*
import javax.inject.Singleton

@Component(modules = [AppModule::class])
@Singleton
interface AppComponent {

    fun inject(homeActivity: HomeActivity)

    fun inject(profileFragment: ProfileFragment)

    fun inject(questionFragment: QuestionFragment)

    fun inject(resultFragment: ResultFragment)

    fun inject(settingFragment: SettingFragment)

    fun inject(signInActivity: SignInActivity)

    fun inject(signUpActivity: SignUpActivity)

    fun inject(splashActivity: SplashActivity)

    fun inject(surveyFragment: SurveyFragment)

    fun inject(themeFragment: ThemeFragment)


    // Services
    fun inject(serviceController: ServiceController)

    fun inject(jsonController: JsonController)

    fun inject(apiController: ApiController)


    fun inject(answerAdapter: AnswerAdapter)

    fun inject(themeAdapter: ThemeAdapter)

}