package kafka.utils;

import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

public class KafkaProducerClass {
	public void sendMessage(String message) throws Exception {

			Properties props = new Properties();
			props.put("bootstrap.servers","10.74.51.23:9092,10.74.51.21:9092,10.74.51.22:9092,10.74.51.25:9092,10.74.51.24:9092");
			props.put("acks", "all");
			props.put("retries", 0);
			props.put("batch.size", 16384);
			props.put("linger.ms", 1);
			props.put("buffer.memory", 33554432);
			props.put("key.serializer",
					"org.apache.kafka.common.serialization.StringSerializer");
			props.put("value.serializer",
					"org.apache.kafka.common.serialization.StringSerializer");

			Producer<String, String> producer = new KafkaProducer<>(props);
				producer.send(new ProducerRecord<String, String>("TopicFor10Aug","Person",message));
			producer.close();
		}
	}

