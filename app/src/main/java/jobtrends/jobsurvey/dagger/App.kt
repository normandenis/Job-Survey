package jobtrends.jobsurvey.dagger

import android.app.Application

class App : Application() {

    companion object {

        lateinit var component: AppComponent

        lateinit var app: App

    }

    override fun onCreate() {
        super.onCreate()
        app = this
        component = DaggerAppComponent.create()
    }

}