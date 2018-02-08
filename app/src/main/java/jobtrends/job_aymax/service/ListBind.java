package jobtrends.job_aymax.service;

import android.databinding.BindingAdapter;
import android.widget.ListView;

import java.util.List;

import jobtrends.job_aymax.model.AnswerSurveyModel;

/**
 * Created by norma on 08-Feb-18.
 */

public class ListBind {

    @BindingAdapter("bind:items")
    public static void bindList(ListView view, List<AnswerSurveyModel> list) {
        ListAdapter adapter = new ListAdapter(list);
        view.setAdapter(adapter);
    }
}
