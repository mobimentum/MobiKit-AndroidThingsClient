package it.mobimentum.helloandroidthings;

/**
 * @author Maurizio Pinotti.
 */
public class Config {

    public static final String BROKER = "tcp://192.168.100.101:1883";

    public static final String TOPIC_FILTER = "mobikit/modules/#";

//    public static final int QOS = 2; // Exactly once

    public static final String CLIENT_ID = "HelloAndroidThings";

    public static final int LOG_MAX_LENGTH = 5000;
}
