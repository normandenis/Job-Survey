package jobtrends.jobsurvey.model

import android.databinding.ObservableField
import com.google.gson.annotations.JsonAdapter
import jobtrends.jobsurvey.adapter.ObservableIntAdapter

data class HomeModel(@JsonAdapter(ObservableIntAdapter::class)
                     var score: ObservableField<Int?>? = ObservableField(),
                     var themes: List<ThemeModel?>? = mutableListOf())
