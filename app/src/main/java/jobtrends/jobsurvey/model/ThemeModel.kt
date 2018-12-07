package jobtrends.jobsurvey.model

import android.databinding.ObservableField
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import jobtrends.jobsurvey.adapter.ObservableBooleanAdapter
import jobtrends.jobsurvey.adapter.ObservableIntAdapter
import jobtrends.jobsurvey.adapter.ObservableStringAdapter

data class ThemeModel(
    @SerializedName("_id")
    @JsonAdapter(ObservableStringAdapter::class)
    var themeId: ObservableField<String?>? = ObservableField(),
    @SerializedName("survey_id")
    @JsonAdapter(ObservableStringAdapter::class)
    var surveyId: ObservableField<String?>? = ObservableField(),
    @JsonAdapter(ObservableStringAdapter::class)
    var name: ObservableField<String?>? = ObservableField(),
    @JsonAdapter(ObservableBooleanAdapter::class)
    var open: ObservableField<Boolean?>? = ObservableField(),
    @JsonAdapter(ObservableStringAdapter::class)
    var timeSet: ObservableField<String?>? = ObservableField(),
    @SerializedName("lvl")
    @JsonAdapter(ObservableIntAdapter::class)
    var level: ObservableField<Int?>? = ObservableField()
)
