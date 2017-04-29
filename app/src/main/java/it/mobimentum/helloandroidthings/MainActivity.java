package it.mobimentum.helloandroidthings;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.Selection;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.widget.TextView;

import it.mobimentum.helloandroidthings.mqtt.AbstractMobiKitClient;
import it.mobimentum.helloandroidthings.mqtt.MockClient;
import it.mobimentum.helloandroidthings.ui.MotionView;
import it.mobimentum.helloandroidthings.ui.RelayView;
import it.mobimentum.helloandroidthings.ui.TemperatureView;

/**
 * @author Maurizio Pinotti.
 */
public class MainActivity extends Activity implements MobiKit {

    private static final String TAG = MainActivity.class.getSimpleName();

    private Handler mHandler;

    private TextView mLogView;
    private TemperatureView mTemperatureView;
    private MotionView mMotionView;
    private RelayView mRelayView;

    private AbstractMobiKitClient mClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        mLogView = (TextView) findViewById(R.id.logText);
        mLogView.setMovementMethod(new ScrollingMovementMethod());

        mTemperatureView = (TemperatureView) findViewById(R.id.temperatureWidget);
        mMotionView = (MotionView) findViewById(R.id.motionWidget);
        mRelayView = (RelayView) findViewById(R.id.relayWidget);

        mHandler = new Handler();

        // Init MQTT client
//        mClient = new MobiKitClient(this); // real client
        mClient = new MockClient(this); // fake client
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
    public void setTemperatureData(final int humidity, final float celsius, final float fahrenheit) {
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
        Log.i(TAG, message);

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                mLogView.append( message + "\n");

                Editable editable = mLogView.getEditableText();
                Selection.setSelection(editable, editable.length());
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
