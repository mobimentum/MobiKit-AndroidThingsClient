package it.mobimentum.helloandroidthings;

/**
 * @author Maurizio Pinotti.
 */
public class Config {

    /**
     * Setta a "true" per utilizzare il client finto con i dati mockati.
     */
    public static final boolean USE_MOCK_CLIENT = true;

    /**
     * Indirizzo del broker MQTT.
     */
    public static final String BROKER = "tcp://192.168.100.101:1883";

    /**
     * Filtro per i topic MQTT.
     */
    public static final String TOPIC_FILTER = "mobikit/modules/#";

//    public static final int QOS = 2; // Exactly once

    /**
     * ID utilizzato dal client MQTT per registrasi al broker.
     */
    public static final String CLIENT_ID = "HelloAndroidThings";

    /**
     * Numero massimo di caratteri nel log.
     */
    public static final int LOG_MAX_LENGTH = 5000;
}
