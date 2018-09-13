package jobtrends.jobsurvey.model

import android.databinding.ObservableField
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import jobtrends.jobsurvey.adapter.ObservableStringAdapter

class ResultModel
{
    @Expose(serialize = true, deserialize = true)
    @JsonAdapter(ObservableStringAdapter::class)
    var id: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    var answers: MutableList<UserAnswerModel?>?

    constructor()
    {
        id = ObservableField()
        answers = null
    }
}
