package it.mobimentum.helloandroidthings;

/**
 * @author Maurizio Pinotti
 */

public interface MobiKit {

    // Data
    void setTemperatureData(int humidity, float celsius, float fahrenheit);
    void setMotionState(boolean state);
    void setRelayState(boolean state);

    // Log
    void log(String message);
    void log(Exception e);
}
