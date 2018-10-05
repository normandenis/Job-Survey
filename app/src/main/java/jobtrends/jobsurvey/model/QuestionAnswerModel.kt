package jobtrends.jobsurvey.model

import android.databinding.ObservableField
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import jobtrends.jobsurvey.adapter.ObservableStringAdapter

data class QuestionAnswerModel(@SerializedName("_id")
                               @JsonAdapter(ObservableStringAdapter::class)
                               var id: ObservableField<String?>? = ObservableField(),
                               @JsonAdapter(ObservableStringAdapter::class)
                               var title: ObservableField<String?>? = ObservableField(),
                               @JsonAdapter(ObservableStringAdapter::class)
                               var value: ObservableField<String?>? = ObservableField())
