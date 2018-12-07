package jobtrends.jobsurvey.model

import android.databinding.ObservableField
import com.google.gson.annotations.JsonAdapter
import jobtrends.jobsurvey.adapter.ObservableStringAdapter

data class MessageModel(
    @JsonAdapter(ObservableStringAdapter::class)
    var _message: ObservableField<String?>? = ObservableField(),
    @JsonAdapter(ObservableStringAdapter::class)
    var message: ObservableField<String?>? = ObservableField(),
    @JsonAdapter(ObservableStringAdapter::class)
    var name: ObservableField<String?>? = ObservableField()
)
