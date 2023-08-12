package by.my.kafka.consumer;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class TinyKafkaConsumerCreator {

    Map<String, Object> props = new HashMap<>();
    private String bootstrapAddress = "127.0.0.1:9092";
    private String groupId = "null";
    private String topic = "testTopic";

    {
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        if(groupId != null) props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.MAX_PARTITION_FETCH_BYTES_CONFIG, "20971520");
        props.put(ConsumerConfig.FETCH_MAX_BYTES_CONFIG, "20971520");
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
    }

    public KafkaConsumer<String, String> getConsumer() {
        return getConsumer(topic);
    }


    public KafkaConsumer<String, String> getConsumer(String topic) {
        KafkaConsumer<String, String> result = new KafkaConsumer<>(props);
        result.subscribe(Collections.singletonList(topic));
        return result;
    }
}
