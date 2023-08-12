package by.my.kafkaSpring.consumer;


import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class MyKafkaListener {

    @KafkaListener(topics = "testTopic")
    public void listenGroupFoo(@Payload String message,
                               @Header(KafkaHeaders.RECEIVED_MESSAGE_KEY) String key,
                               @Header(KafkaHeaders.RECEIVED_TOPIC) String topic) {
        System.out.println("Received Message in topic : " + topic + " message: " + message);
        System.out.println("Partititon");
        if (key != null || message != null)
            System.out.println(String.format("Record key: %s, value: %s ", key, message));
    }
}
