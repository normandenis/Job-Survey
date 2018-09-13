package jobtrends.jobsurvey.model

import android.databinding.ObservableField
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import jobtrends.jobsurvey.adapter.ObservableStringAdapter

class BodyModel
{
    @Expose(serialize = true, deserialize = true)
    @JsonAdapter(ObservableStringAdapter::class)
    var message: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    @SerializedName("err")
    var error: MessageModel?

    constructor()
    {
        message = ObservableField()
        error = MessageModel()
    }
}