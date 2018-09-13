package jobtrends.jobsurvey.model

import android.databinding.ObservableField
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import jobtrends.jobsurvey.adapter.ObservableIntAdapter
import jobtrends.jobsurvey.adapter.ObservableStringAdapter

class SurveyModel
{
    @Expose(serialize = true, deserialize = true)
    @SerializedName("_id")
    @JsonAdapter(ObservableStringAdapter::class)
    var id: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    @JsonAdapter(ObservableStringAdapter::class)
    var title: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    @JsonAdapter(ObservableIntAdapter::class)
    var theme: ObservableField<Int?>?

    @Expose(serialize = true, deserialize = true)
    @SerializedName("created_by")
    @JsonAdapter(ObservableStringAdapter::class)
    var createdBy: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    var questions: List<QuestionModel?>?

    constructor()
    {
        id = ObservableField()
        title = ObservableField()
        theme = ObservableField()
        createdBy = ObservableField()
        questions = null
    }
}