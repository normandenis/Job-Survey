package jobtrends.jobsurvey.model

import android.databinding.ObservableField
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import jobtrends.jobsurvey.adapter.ObservableStringAdapter

data class ResultModel(@JsonAdapter(ObservableStringAdapter::class)
                       var id: ObservableField<String?>? = ObservableField(),
                       var answers: MutableList<UserAnswerModel?>? = mutableListOf())
