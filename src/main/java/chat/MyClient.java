package chat;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttTopic;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

public  class MyClient {
    /**
     * 代理服务器ip地址
     */
    public static final String MQTT_BROKER_HOST = "tcp://localhost:61613";
    /**
     * 订阅标识
     */
    private static String userName = "admin";
    private static String password = "password";
    /**
     * 客户端唯一标识
     */
    private static String topic;
    public static String clintId;
    public static MqttClient client;

    public MyClient(String topic,String clintId) {
        this.topic = topic;
        this.clintId = clintId;
    }

    public static MqttClient getClient() throws MqttException {
        client = new MqttClient(MQTT_BROKER_HOST, clintId, new MemoryPersistence());
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(password.toCharArray());
        options.setConnectionTimeout(10);
        options.setKeepAliveInterval(20);
        client.connect(options);
        // 订阅
        client.subscribe(topic);
        return client;
    }
}
