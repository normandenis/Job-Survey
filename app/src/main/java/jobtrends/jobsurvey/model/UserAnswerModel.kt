package jobtrends.jobsurvey.model

import android.databinding.ObservableField
import com.google.gson.annotations.JsonAdapter
import jobtrends.jobsurvey.adapter.ObservableStringAdapter

data class UserAnswerModel(@JsonAdapter(ObservableStringAdapter::class)
                           var question: ObservableField<String?>? = ObservableField(),
                           @JsonAdapter(ObservableStringAdapter::class)
                           var answer: ObservableField<String?>? = ObservableField())
