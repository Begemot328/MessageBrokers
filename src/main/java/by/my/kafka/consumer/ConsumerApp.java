package by.my.kafka.consumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.time.Duration;

public class ConsumerApp {
    public static void main(String[] args) {
        System.out.println("ConsumerApp");
        while (true) {
            KafkaConsumer<String, String> consumer = new TinyKafkaConsumerCreator().getConsumer();
            consumer.poll(Duration.ofMillis(1000)).forEach(message ->
            {
                System.out.println("Partititon");
                if (message.key() != null || message.value() != null)
                    System.out.println(String.format("Record key: %s, value: %s, %s", message.key(), message.value(), message.toString()));
            });

        }
    }
}
