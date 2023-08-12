package by.my.kafkaSpring.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;

import java.util.Collections;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

@SpringBootApplication
public class ProducerApp implements CommandLineRunner {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    private static Callback callback = new Callback() {
        @Override
        public void onCompletion(RecordMetadata recordMetadata, Exception e) {
            if (e == null) {
                System.out.println("Successfully received the details as: \n" +
                        "Topic:" + recordMetadata.topic() + "\n" +
                        "Partition:" + recordMetadata.partition() + "\n" +
                        "Offset" + recordMetadata.offset() + "\n" +
                        "Timestamp" + recordMetadata.timestamp());
            } else {
                e.printStackTrace();
            }
        }
    };

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ProducerApp.class);
        app.setDefaultProperties(Collections
                .singletonMap("server.port", "8083"));
        app.run(args);
    }


    @Override
    public void run(String... args) {
        try {
            System.out.println("ProducerApp");
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Pop in message");
                String value = scanner.nextLine();
                if (value.isBlank() || value.equals("$stop")) break;
                if (value.contains("$")) continue;
                CompletableFuture<SendResult<String, String>> future = null;
                if (value.contains(",")) {
                    value.split(",");
                    future = kafkaTemplate.send("testTopic", value.split(",")[0], value.split(",")[1]).completable();
                } else {
                    future = kafkaTemplate.send("testTopic", value).completable();
                }

                future.whenComplete((result, ex) -> {
                    if (ex == null) {
                        System.out.println("Sent message=[" + value +
                                "] with offset=[" + result.getRecordMetadata().offset() + "]");
                    } else {
                        System.out.println("Unable to send message=[" +
                                value + "] due to : " + ex.getMessage());
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}