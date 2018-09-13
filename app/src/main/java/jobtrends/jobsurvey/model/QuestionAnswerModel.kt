package jobtrends.jobsurvey.model

import android.databinding.ObservableField
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import jobtrends.jobsurvey.adapter.ObservableStringAdapter

class QuestionAnswerModel
{
    @Expose(serialize = true, deserialize = true)
    @SerializedName("_id")
    @JsonAdapter(ObservableStringAdapter::class)
    var id: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    @JsonAdapter(ObservableStringAdapter::class)
    var title: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    @JsonAdapter(ObservableStringAdapter::class)
    var value: ObservableField<String?>?

    constructor()
    {
        id = ObservableField()
        title = ObservableField()
        value = ObservableField()
    }
}
