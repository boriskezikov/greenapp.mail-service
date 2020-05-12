package com.greenapp.mailservice.kafka;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.errors.SerializationException;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.io.IOException;

public class KafkaJsonSerializer<T> extends JsonSerializer<T> {

    private TypeReference<T> typeReference;

    public KafkaJsonSerializer(TypeReference<T> typeReference, ObjectMapper objectMapper) {
        super(objectMapper);
        this.typeReference = typeReference;
    }

    public T deserialize(String topic, byte[] data) {
        try {
            T result = null;
            if (data != null) {

                result = this.objectMapper.readerFor(typeReference).readValue(data);
            }
            return result;
        } catch (IOException ex) {
            throw new SerializationException("Can't deserialize data [" + data + "] for topic [" + topic + "]", ex);

        }
    }

    public byte[] serialize(String topic, T data) {
        try {
            byte[] result = null;
            if (data != null) {
                result = this.objectMapper.writerFor(typeReference).writeValueAsBytes(data);
            }
            return result;
        } catch (IOException ex) {
            throw new SerializationException("Can't serialize data [" + data + "] for topic [" + topic + "]", ex);
        }
    }
}
