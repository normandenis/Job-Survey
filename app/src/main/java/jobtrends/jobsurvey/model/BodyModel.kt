package jobtrends.jobsurvey.model

import android.databinding.ObservableField
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import jobtrends.jobsurvey.adapter.ObservableStringAdapter

data class BodyModel(@JsonAdapter(ObservableStringAdapter::class)
                     var message: ObservableField<String?>? = ObservableField(),
                     @SerializedName("err")
                     var error: MessageModel? = MessageModel())