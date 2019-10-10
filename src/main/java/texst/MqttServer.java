package texst;

import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * MqttClient对象通过 client.connect(options)连接
 * 所有设置通过MqttConnectOptions设置
 * topic.publish(message)来发送消息
 *
 */
public class MqttServer {
    /**
     * 代理服务器ip地址
     */
    public static final String MQTT_BROKER_HOST = "tcp://localhost:61613";

    /**
     * 订阅标识
     */
    public static final String MQTT_TOPIC = "mybroker";

    // 主题还可以这样
    //public static final String TOPIC = "root/topic/testDx";
    private static String userName = "admin";
    private static String password = "password";

    /**
     * 客户端唯一标识
     */
    public static final String MQTT_CLIENT_ID = "android_server_xiasuhuei321";
    private static MqttTopic topic;
    private static MqttClient client;

    public static void main(String... args) {
        // 推送消息
        MqttMessage message = new MqttMessage();
        try {
            client = new MqttClient(MQTT_BROKER_HOST, MQTT_CLIENT_ID, new MemoryPersistence());
            MqttConnectOptions options = new MqttConnectOptions();
            options.setCleanSession(false);
            options.setUserName(userName);
            options.setPassword(password.toCharArray());
            options.setConnectionTimeout(10);
            options.setKeepAliveInterval(20);

            topic = client.getTopic(MQTT_TOPIC);

            //设置连接服务质量
            /**
             * level 0：最多一次的传输
             * level 1：至少一次的传输，(鸡肋)
             * level 2： 只有一次的传输
             */
            message.setQos(2);
            /**
             * 是否保存消息
             */
            message.setRetained(true);
            client.connect(options);

            int i = 0;
            while (true) {
                String str = new String("message" + i + " from server");
                message.setPayload(str.getBytes());
                MqttDeliveryToken token = topic.publish(message);
                token.waitForCompletion();
                System.out.println("message" + i + "已经发送");
                Thread.sleep(1000);
                i++;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
