package jobtrends.jobsurvey.adapter

import android.databinding.ObservableField
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter

class ObservableStringAdapter : TypeAdapter<ObservableField<String?>?>
{
    constructor()

    override fun write(json: JsonWriter?, value: ObservableField<String?>?)
    {
        json?.value(value?.get())
    }

    override fun read(json: JsonReader?): ObservableField<String?>?
    {
        return ObservableField(json?.nextString())
    }
}

class ObservableIntAdapter : TypeAdapter<ObservableField<Int?>?>
{
    constructor()

    override fun write(json: JsonWriter?, value: ObservableField<Int?>?)
    {
        json?.value(value?.get())
    }

    override fun read(json: JsonReader?): ObservableField<Int?>?
    {
        return ObservableField(json?.nextInt())
    }
}

class ObservableBooleanAdapter : TypeAdapter<ObservableField<Boolean?>?>
{
    constructor()

    override fun write(json: JsonWriter?, value: ObservableField<Boolean?>?)
    {
        json?.value(value?.get())
    }

    override fun read(json: JsonReader?): ObservableField<Boolean?>?
    {
        return ObservableField(json?.nextBoolean())
    }
}