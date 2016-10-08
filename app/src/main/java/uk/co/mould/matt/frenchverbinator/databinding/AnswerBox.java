package uk.co.mould.matt.frenchverbinator.databinding;

import android.databinding.BaseObservable;

public class AnswerBox extends BaseObservable {
    private String text;
    private boolean enabled;
    private boolean visibility;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isVisible() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }
}
