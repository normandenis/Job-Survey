package jobtrends.job_aymax.model

import android.databinding.ObservableField

class ThemeModel
{
	var name = ObservableField<String>()
	var surveyId = ObservableField<String>()
	var themeId = ObservableField<Int>()
	var lvl = ObservableField<Int>()
	var open = ObservableField<Boolean>()
}

class Theme
{
	var name : String? = null
	var surveyId : String? = null
	var themeId : Int? = null
	var lvl : Int? = null
	var open : Boolean? = null
}