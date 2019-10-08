package chat;

import org.eclipse.paho.client.mqttv3.*;

import java.util.Scanner;

public class PersonOne {
    public static final String MQTT_TOPIC = "chat";
    // 推送消息
    static MqttMessage message = new MqttMessage();
    private static MqttClient client;
    private static MqttTopic topic;
    public static void main(String[] args) throws MqttException {
        final Scanner sc = new Scanner(System.in);
        client = new MyClient(MQTT_TOPIC,"yp").getClient();
        topic =client.getTopic(MQTT_TOPIC);
        PersonOne demo2 = new PersonOne();
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    String str = sc.nextLine();
                    if(str.equals("/-/-")){
                        System.out.println("已结束输入！");
                        break;
                    }
                    message.setPayload(str.getBytes());
                    MqttDeliveryToken token = null;
                    try {
                        token = topic.publish(message);
                        token.waitForCompletion();
                    } catch (MqttException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        new Thread(new Runnable() {
            @Override
            public void run() {
                // 设置回调
                client.setCallback(new MqttCallback() {
                    @Override
                    public void connectionLost(Throwable throwable) {
                        System.out.println("connectionLost");
                    }

                    @Override
                    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                        System.out.println("Topic: " + s + "---- Message: " + mqttMessage.toString());
                    }

                    @Override
                    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
                        System.out.println("deliveryComplete");
                    }
                });
            }
        });
    };

}
