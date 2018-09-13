package jobtrends.jobsurvey.model

import android.databinding.ObservableField
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import com.google.gson.annotations.SerializedName
import jobtrends.jobsurvey.adapter.ObservableBooleanAdapter
import jobtrends.jobsurvey.adapter.ObservableIntAdapter
import jobtrends.jobsurvey.adapter.ObservableStringAdapter

class ThemeModel
{
    @Expose(serialize = true, deserialize = true)
    @SerializedName("_id")
    @JsonAdapter(ObservableStringAdapter::class)
    var themeId: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    @SerializedName("survey_id")
    @JsonAdapter(ObservableStringAdapter::class)
    var surveyId: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    @JsonAdapter(ObservableStringAdapter::class)
    var name: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    @JsonAdapter(ObservableBooleanAdapter::class)
    var open: ObservableField<Boolean?>?

    @Expose(serialize = true, deserialize = true)
    @JsonAdapter(ObservableStringAdapter::class)
    var timeSet: ObservableField<String?>?

    @Expose(serialize = true, deserialize = true)
    @SerializedName("lvl")
    @JsonAdapter(ObservableIntAdapter::class)
    var level: ObservableField<Int?>?

    constructor()
    {
        themeId = null
        surveyId = null
        name = null
        open = null
        timeSet = null
        level = null
    }
}