package it.mobimentum.helloandroidthings.mqtt;

import it.mobimentum.helloandroidthings.MobiKit;

/**
 * @author Maurizio Pinotti
 */
public abstract class AbstractMobiKitClient {

    protected MobiKit mKit;

    public AbstractMobiKitClient(MobiKit kit) {
        mKit = kit;
    }

    public void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                startInternal();
            }
        }).start();
    }

    protected abstract void startInternal();

    public void stop() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                stopInternal();
            }
        }).start();
    }

    protected abstract void stopInternal();
}
