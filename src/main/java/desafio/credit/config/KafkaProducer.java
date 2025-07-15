package desafio.credit.config;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class KafkaProducer {
    private static final Logger logger = LoggerFactory.getLogger(KafkaProducer.class);
    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String topic, String message) {
        try {
            kafkaTemplate.send(topic, message);
            logger.info("Mensagem enviado para o Kafka topic: {}, message: {}", topic, message);
        } catch (Exception e) {
            logger.warn("Falha ao enviar mensagem para o Kafka topic: {}, message: {}. Error: {}", topic, message, e.getMessage());
        }
    }
}
