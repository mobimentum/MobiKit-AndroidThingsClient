package it.mobimentum.helloandroidthings;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Html;
import android.text.Selection;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import it.mobimentum.helloandroidthings.mqtt.AbstractMobiKitClient;
import it.mobimentum.helloandroidthings.mqtt.MobiKitClient;
import it.mobimentum.helloandroidthings.mqtt.MockClient;
import it.mobimentum.helloandroidthings.ui.MotionView;
import it.mobimentum.helloandroidthings.ui.RelayView;
import it.mobimentum.helloandroidthings.ui.TemperatureView;
import it.mobimentum.helloandroidthings.util.IpUtils;

import static it.mobimentum.helloandroidthings.Config.LOG_MAX_LENGTH;
import static it.mobimentum.helloandroidthings.Config.USE_MOCK_CLIENT;

/**
 * @author Maurizio Pinotti.
 */
public class MainActivity extends Activity implements MobiKit {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Handler mHandler;

    private TextView mLogView, mInfoView;
    private TemperatureView mTemperatureView;
    private MotionView mMotionView;
    private RelayView mRelayView;

    private AbstractMobiKitClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Log.i(TAG, "Smallest width: "+getResources().getConfiguration().smallestScreenWidthDp);

        mInfoView = (TextView) findViewById(R.id.infoText);
        mInfoView.setText(Html.fromHtml(String.format(getResources().getString(R.string.ip_address),
                IpUtils.getIPAddress(true)), 0));

        mLogView = (TextView) findViewById(R.id.logText);
        mLogView.setMovementMethod(new ScrollingMovementMethod());

        mTemperatureView = (TemperatureView) findViewById(R.id.temperatureWidget);
        mMotionView = (MotionView) findViewById(R.id.motionWidget);
        mRelayView = (RelayView) findViewById(R.id.relayWidget);

        mHandler = new Handler();

        // Init MQTT client
        if (USE_MOCK_CLIENT) {
            mClient = new MockClient(this); // fake client
        }
        else {
            mClient = new MobiKitClient(this); // real client
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        // Start MQTT client
        mClient.start();
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Stop MQTT client
        mClient.stop();
    }

    @Override
    public void setTemperatureData(final float humidity, final float celsius, final float fahrenheit) {
        log("Valore temperatura: " + humidity + "% " + celsius + "ºC " + fahrenheit + "ºF");

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mTemperatureView.setTemperature(humidity, celsius, fahrenheit);
            }
        });
    }

    @Override
    public void setMotionState(final boolean state) {
        log("Valore sensore movimento: " + state);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mMotionView.setMotionState(state);
            }
        });
    }

    @Override
    public void setRelayState(final boolean state) {
        log("Valore stato relé: " + state);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mRelayView.setRelayState(state);
            }
        });
    }

    @Override
    public void log(final String message) {
        Log.d(TAG, message);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mLogView.append( message + "\n");
//                Log.i(TAG, "Text length: "+mLogView.getText().length());

                Editable editable = mLogView.getEditableText();
                int length = editable.length();

                if (length > LOG_MAX_LENGTH) {
                    Log.i(TAG, "Trimming logs (length="+length+")...");

                    mLogView.setText(editable.subSequence(length-LOG_MAX_LENGTH, length));

                    editable = mLogView.getEditableText();
                    length = editable.length();
                }

                Selection.setSelection(editable, length);
            }
        });
    }

    @Override
    public void log(final Exception e) {
        Log.i(TAG, e.getClass().getName()+": "+e.getMessage(), e);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mLogView.append(e.getClass().getSimpleName() + ": " + e.getMessage() + "\n");

                Editable editable = mLogView.getEditableText();
                Selection.setSelection(editable, editable.length());
            }
        });
    }
}
