package it.mobimentum.helloandroidthings.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import it.mobimentum.helloandroidthings.R;

/**
 * @author Maurizio Pinotti
 */
public class TemperatureView extends LinearLayout {

    private ImageView mThermometerView;
    private TextView mCelsiusView, mFahrenheitView, mHumidityView;

    public TemperatureView(Context context) {
        this(context, null);
    }

    public TemperatureView(Context context, AttributeSet attrs) {
        this(context, attrs, 0, 0);
    }

    public TemperatureView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public TemperatureView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        LayoutInflater.from(context).inflate(R.layout.view_temperature, this, true);

        setOrientation(HORIZONTAL);
        setBackgroundResource(R.drawable.widget_bg);

        mThermometerView = (ImageView) findViewById(R.id.thermometerIcon);
        mCelsiusView = (TextView) findViewById(R.id.celsiusText);
        mFahrenheitView = (TextView) findViewById(R.id.fahrText);
        mHumidityView = (TextView) findViewById(R.id.humidText);
    }

    public void setTemperature(int humidity, float celsius, float fahrenheit) {
        mCelsiusView.setText(String.valueOf(celsius) + "\u00B0C");
        mFahrenheitView.setText(String.valueOf(fahrenheit) + "\u00B0F");
        mHumidityView.setText(String.valueOf(humidity) + "%");

        if (celsius <= 0) {
            mThermometerView.setImageResource(R.drawable.lowest_blue);
        }
        else if (celsius >= 1 && celsius <= 15) {
            mThermometerView.setImageResource(R.drawable.blue);
        }
        else if (celsius >= 16 && celsius <= 26) {
            mThermometerView.setImageResource(R.drawable.orange);
        }
        else if (celsius >= 27 && celsius <= 35) {
            mThermometerView.setImageResource(R.drawable.red);
        }
        else {
            mThermometerView.setImageResource(R.drawable.purple);
        }
    }
}
