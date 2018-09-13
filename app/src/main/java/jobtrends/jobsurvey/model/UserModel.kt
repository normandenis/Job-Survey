package jobtrends.jobsurvey.model

import android.databinding.ObservableField
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import jobtrends.jobsurvey.adapter.ObservableStringAdapter

class UserModel
{
    @Expose(serialize = true, deserialize = true)
    @JsonAdapter(ObservableStringAdapter::class)
    var firstName: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    @JsonAdapter(ObservableStringAdapter::class)
    var lastName: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    @JsonAdapter(ObservableStringAdapter::class)
    var email: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    @JsonAdapter(ObservableStringAdapter::class)
    var password: ObservableField<String?>?

    @Expose(serialize = false, deserialize = false)
    var passwordProtection: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    @JsonAdapter(ObservableStringAdapter::class)
    var birthday: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    @JsonAdapter(ObservableStringAdapter::class)
    var job: ObservableField<String?>?

    @Expose(serialize = false, deserialize = false)
    var token: ObservableField<String?>?

    constructor()
    {
        firstName = ObservableField()
        lastName = ObservableField()
        email = ObservableField()
        password = ObservableField()
        passwordProtection = ObservableField()
        job = ObservableField()
        birthday = ObservableField()
        token = ObservableField()
    }

    constructor(userModel: UserModel?)
    {
        firstName = ObservableField(userModel?.firstName?.get())
        lastName = ObservableField(userModel?.lastName?.get())
        email = ObservableField(userModel?.email?.get())
        password = ObservableField(userModel?.password?.get())
        passwordProtection = ObservableField(userModel?.passwordProtection?.get())
        job = ObservableField(userModel?.job?.get())
        birthday = ObservableField(userModel?.birthday?.get())
        token = ObservableField(userModel?.token?.get())
    }

    fun reset()
    {
        firstName?.set("")
        lastName?.set("")
        email?.set("")
        password?.set("")
        birthday?.set("")
        job?.set("")
    }
}
