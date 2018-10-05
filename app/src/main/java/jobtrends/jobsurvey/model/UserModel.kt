package jobtrends.jobsurvey.model

import android.databinding.ObservableField
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import jobtrends.jobsurvey.adapter.ObservableStringAdapter

data class UserModel(@JsonAdapter(ObservableStringAdapter::class)
                     var firstName: ObservableField<String?>? = ObservableField(),
                     @JsonAdapter(ObservableStringAdapter::class)
                     var lastName: ObservableField<String?>? = ObservableField(),
                     @JsonAdapter(ObservableStringAdapter::class)
                     var email: ObservableField<String?>? = ObservableField(),
                     @JsonAdapter(ObservableStringAdapter::class)
                     var password: ObservableField<String?>? = ObservableField(),
                     @SerializedName("newPassword")
                     @JsonAdapter(ObservableStringAdapter::class)
                     var passwordProtection: ObservableField<String?>? = ObservableField(),
                     @JsonAdapter(ObservableStringAdapter::class)
                     var birthday: ObservableField<String?>? = ObservableField(),
                     @JsonAdapter(ObservableStringAdapter::class)
                     var job: ObservableField<String?>? = ObservableField()) {

    fun reset() {
        firstName?.set("")
        lastName?.set("")
        email?.set("")
        password?.set("")
        birthday?.set("")
        job?.set("")
    }
}
