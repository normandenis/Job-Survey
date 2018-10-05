package jobtrends.jobsurvey.model

import android.databinding.ObservableField
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import jobtrends.jobsurvey.adapter.ObservableStringAdapter

data class QuestionModel(@SerializedName("_id")
                         @JsonAdapter(ObservableStringAdapter::class)
                         var id: ObservableField<String?>? = ObservableField(),
                         @JsonAdapter(ObservableStringAdapter::class)
                         var content: ObservableField<String?>? = ObservableField(),
                         @JsonAdapter(ObservableStringAdapter::class)
                         var description: ObservableField<String?>? = ObservableField(),
                         @JsonAdapter(ObservableStringAdapter::class)
                         var type: ObservableField<String?>? = ObservableField(),
                         var answers: List<QuestionAnswerModel?>? = mutableListOf())
