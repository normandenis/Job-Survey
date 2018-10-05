package jobtrends.jobsurvey.dagger

import android.content.Context
import android.content.res.Resources
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import jobtrends.jobsurvey.model.ErrorModel
import jobtrends.jobsurvey.service.ApiController
import jobtrends.jobsurvey.service.JsonController
import jobtrends.jobsurvey.service.ServiceController
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(): Context = App.app.applicationContext

    @Provides
    @Singleton
    fun provideGson(): Gson = Gson()

    @Provides
    @Singleton
    fun provideResources(): Resources = App.app.resources

    @Provides
    fun provideErrorModel(): ErrorModel = ErrorModel()

    @Provides
    @Singleton
    fun provideApiController(): ApiController = ApiController()

    @Provides
    @Singleton
    fun provideJsonController(): JsonController = JsonController()

    @Provides
    @Singleton
    fun provideServiceController(): ServiceController = ServiceController()

}