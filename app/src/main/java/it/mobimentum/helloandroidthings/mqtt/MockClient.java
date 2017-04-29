package it.mobimentum.helloandroidthings.mqtt;

import android.os.SystemClock;

import it.mobimentum.helloandroidthings.MobiKit;

/**
 * @author Maurizio Pinotti
 */
public class MockClient extends AbstractMobiKitClient {

    private static final long SLEEP_BETWEEN_UPDATES_MS = 3000;

    private boolean mRunning;

    private boolean mRelayState, mMotionState;

    public MockClient(MobiKit kit) {
        super(kit);
    }

    @Override
    public void startInternal() {
        mRunning = true;

        while (mRunning) {
            mRelayState = !mRelayState;
            mKit.setRelayState(mRelayState);

            mMotionState = !mMotionState;
            mKit.setMotionState(mMotionState);

            int humidity = 50 + (int) (Math.random() * 50);
            float celsiusTemp = -10 + (int) (Math.random() * 50);
            float fahrTemp = 32 + (celsiusTemp * 9 / 5);
            mKit.setTemperatureData(humidity, celsiusTemp, fahrTemp);

            SystemClock.sleep(SLEEP_BETWEEN_UPDATES_MS);
        }
    }

    @Override
    public void stopInternal() {
        mRunning = false;
    }
}
