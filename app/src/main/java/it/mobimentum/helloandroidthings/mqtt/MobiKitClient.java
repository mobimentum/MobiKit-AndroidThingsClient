package it.mobimentum.helloandroidthings.mqtt;

import org.eclipse.paho.client.mqttv3.IMqttMessageListener;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import it.mobimentum.helloandroidthings.MobiKit;

import static it.mobimentum.helloandroidthings.Config.*;

/**
 * @author Maurizio Pinotti
 */
public class MobiKitClient extends AbstractMobiKitClient {

    private MqttClient mClient;

    public MobiKitClient(MobiKit kit) {
        super(kit);

        try {
            mClient = new MqttClient(BROKER, CLIENT_ID, new MemoryPersistence());
        }
        catch (MqttException e) { mKit.log(e); }
    }

    @Override
    protected void startInternal() {
        try {
            if (mClient.isConnected()) mClient.disconnect();
        }
        catch (MqttException e) { mKit.log(e); }

        try {

            MqttConnectOptions connOpts = new MqttConnectOptions();
            connOpts.setCleanSession(true);
            mKit.log("Connessione al broker " + BROKER + "...");

            mClient.connect(connOpts);
            mKit.log("Client connesso!");

//            String topic = "MQTT Examples";
//            String content = "Message from MqttPublishSample";
//            logMessage("Publishing message: " + content);
//            MqttMessage message = new MqttMessage(content.getBytes());
//            message.setQos(qos);
//            sampleClient.publish(topic, message);
//            logMessage("Message published");

//            mClient.subscribe("mobikit/announcements/", new IMqttMessageListener() {
//                @Override
//                public void messageArrived(String topic, MqttMessage message) throws Exception {
//                    mKit.log(topic+": "+message.toString());
//                }
//            });
//            mKit.log("Subscribed to annoucements");

            mClient.subscribe(TOPIC_FILTER, new IMqttMessageListener() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String messageString = message.toString();

                    mKit.log(topic+": "+messageString);

                    try {
                        if (topic.contains("MOBIKIT-relay")) {
                            int intValue = Integer.parseInt(messageString);

                            mKit.setRelayState(intValue == 1);
                        }
                        else if (topic.contains("MOBIKIT-motion")) {
                            int intValue = Integer.parseInt(messageString);

                            mKit.setMotionState(intValue == 1);
                        }
                        else if (topic.contains("MOBIKIT-dht")) {
                            // Humidity|Temperature °C| Temperature °F| Heat index °C| Heat index °F
                            String[] values = messageString.split("\\|");
                            float humidValue = Float.parseFloat(values[0]);
                            float celsiusValue = Float.parseFloat(values[1]);
                            float fahrValue = Float.parseFloat(values[2]);

                            mKit.setTemperatureData(humidValue, celsiusValue, fahrValue);
                        }
                    }
                    catch (Exception e) { mKit.log(e); }
                }
            });
            mKit.log("Sottoscrizione al topic MQTT effettuata.");
        }
        catch (MqttException e) { mKit.log(e); }
    }

    @Override
    protected void stopInternal() {
        try {
            if (mClient.isConnected()) mClient.disconnect();

            mKit.log("Client disconnesso!");
        }
        catch (MqttException e) { mKit.log(e); }
    }
}
