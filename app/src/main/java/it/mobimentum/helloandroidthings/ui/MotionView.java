package it.mobimentum.helloandroidthings.ui;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hamza.slidingsquaresloaderview.SlidingSquareLoaderView;

import java.lang.reflect.Field;

import it.mobimentum.helloandroidthings.R;

/**
 * @author Maurizio Pinotti
 */
public class MotionView extends FrameLayout {

    private SlidingSquareLoaderView mSlidingview;

    public MotionView(Context context) {
        this(context, null);
    }

    public MotionView(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public MotionView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public MotionView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        LayoutInflater.from(context).inflate(R.layout.view_motion, this, true);

        setBackgroundResource(R.drawable.widget_bg);

        // Cfr. https://github.com/biodunalfet/SlidingSquaresLoader
        mSlidingview = (SlidingSquareLoaderView) findViewById(R.id.bricksAnim);

        setMotionState(false);
    }

    public void setMotionState(boolean value) {
        if (value) {
            mSlidingview.setAlpha(1f);
            mSlidingview.start();
        }
        else {
            mSlidingview.stop();
            mSlidingview.setAlpha(0.3f);
        }
    }
}
