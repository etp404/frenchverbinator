package uk.co.mould.matt.frenchverbinator.databinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class ResultBox extends BaseObservable {
    private String text;

    @Bindable
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
