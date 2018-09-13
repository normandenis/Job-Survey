package jobtrends.jobsurvey.model

import android.databinding.ObservableField
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import jobtrends.jobsurvey.adapter.ObservableStringAdapter

class QuestionModel
{
    @Expose(serialize = true, deserialize = true)
    @SerializedName("_id")
    @JsonAdapter(ObservableStringAdapter::class)
    var id: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    @JsonAdapter(ObservableStringAdapter::class)
    var content: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    @JsonAdapter(ObservableStringAdapter::class)
    var description: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    @JsonAdapter(ObservableStringAdapter::class)
    var type: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    var answers: List<QuestionAnswerModel?>?

    constructor()
    {
        id = ObservableField()
        content = ObservableField()
        description = ObservableField()
        type = ObservableField()
        answers = null
    }
}
