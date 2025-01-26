package com.expense.tracker.auth.eventProducer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service //so that spring can make bin of this producer for use of kafka template
public class UserInfoProducer {

    private final KafkaTemplate<String,UserInfoEvent> kafkaTemplate ;

    @Value("${spring.kafka.topic-json.name}")
    private String topicJsonName;

    @Autowired
    public UserInfoProducer(KafkaTemplate<String, UserInfoEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendEvent(UserInfoEvent userInfoEvent){
        Message<UserInfoEvent> message = MessageBuilder
                                            .withPayload(userInfoEvent)
                                            .setHeader(KafkaHeaders.TOPIC,topicJsonName)
                                            .build();
        kafkaTemplate.send(message);
    }


}
