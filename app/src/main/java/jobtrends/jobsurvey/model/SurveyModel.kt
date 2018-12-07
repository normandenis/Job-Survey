package jobtrends.jobsurvey.model

import android.databinding.ObservableField
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import jobtrends.jobsurvey.adapter.ObservableIntAdapter
import jobtrends.jobsurvey.adapter.ObservableStringAdapter

data class SurveyModel(
    @SerializedName("_id")
    @JsonAdapter(ObservableStringAdapter::class)
    var id: ObservableField<String?>? = ObservableField(),
    @JsonAdapter(ObservableStringAdapter::class)
    var title: ObservableField<String?>? = ObservableField(),
    @JsonAdapter(ObservableIntAdapter::class)
    var theme: ObservableField<Int?>? = ObservableField(),
    @SerializedName("created_by")
    @JsonAdapter(ObservableStringAdapter::class)
    var createdBy: ObservableField<String?>? = ObservableField(),
    var questions: List<QuestionModel?>? = mutableListOf()
)
