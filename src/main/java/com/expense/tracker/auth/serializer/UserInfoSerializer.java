package com.expense.tracker.auth.serializer;

import com.expense.tracker.auth.eventProducer.UserInfoEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;

import java.util.Map;
//using kafka serializer and passing for type of object needs to be serialize
public class UserInfoSerializer implements Serializer<UserInfoEvent> {


    @Override // No need to override this
    public void configure(Map<String, ?> configs, boolean isKey) {

    }
// Only need to serialize this methode others not need to be override
    @Override
    public byte[] serialize(String s, UserInfoEvent userInfoEvent) {
        byte[] bytesTosend = null;
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            bytesTosend=  objectMapper.writeValueAsString(userInfoEvent).getBytes();
        }catch (Exception e) {
            e.printStackTrace();
        }
        return bytesTosend;
    }

    @Override // No need to override this
    public void close() {
        Serializer.super.close();
    }
}
