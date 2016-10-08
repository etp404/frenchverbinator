package uk.co.mould.matt.frenchverbinator.databinding;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

public class AnswerBox extends BaseObservable {
    private String text;
    private boolean enabled;
    private boolean visibility;

    public String getText() {
        return text;
    }

    @Bindable
    public void setText(String text) {
        this.text = text;
    }

    @Bindable
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Bindable
    public boolean isVisible() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
