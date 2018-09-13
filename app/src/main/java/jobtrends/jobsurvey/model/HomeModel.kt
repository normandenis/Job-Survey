package jobtrends.jobsurvey.model

import android.databinding.ObservableField
import com.google.gson.annotations.Expose
import com.google.gson.annotations.JsonAdapter
import jobtrends.jobsurvey.adapter.ObservableIntAdapter
import jobtrends.jobsurvey.adapter.ObservableStringAdapter

class HomeModel
{
    @Expose(serialize = true, deserialize = true)
    @JsonAdapter(ObservableIntAdapter::class)
    var score: ObservableField<Int?>?

    @Expose(serialize = true, deserialize = true)
    var themes: List<ThemeModel?>?

    constructor()
    {
        score = ObservableField()
        themes = null
    }
}
