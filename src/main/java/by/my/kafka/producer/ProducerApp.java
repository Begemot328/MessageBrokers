package by.my.kafka.producer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Scanner;

public class ProducerApp {

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
        try {
            System.out.println("ProducerApp");
            KafkaProducer<String, String> producer = new TinyKafkaProducerCreator().getProducer();
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.println("Pop in message");
                String value = scanner.nextLine();
                if (value.isBlank() || value.equals("$stop")) break;
                if (value.contains("$")) continue;
                ProducerRecord<String, String> record = new ProducerRecord<String, String>("testTopic", value);
                producer.send(record, callback);
                producer.flush();

            }
            producer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
