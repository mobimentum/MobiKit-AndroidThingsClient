package it.mobimentum.helloandroidthings.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ToggleButton;

import it.mobimentum.helloandroidthings.R;

/**
 * @author Maurizio Pinotti
 */
public class RelayView extends FrameLayout {

    private ToggleButton mToggleButton;

    public RelayView(Context context) {
        this(context, null);
    }

    public RelayView(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public RelayView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public RelayView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        LayoutInflater.from(context).inflate(R.layout.view_relay, this, true);

        setBackgroundResource(R.drawable.widget_bg);

        mToggleButton = (ToggleButton) findViewById(R.id.relayBtn);

        setRelayState(false);
    }

    public void setRelayState(boolean relayState) {
        mToggleButton.setChecked(relayState);
    }
}
