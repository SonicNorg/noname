package my.wiki.common;

import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.net.URL;
import java.util.Map;

public class UrlSerializer implements Serializer<URL> {
    private final StringSerializer delegate = new StringSerializer();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        delegate.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, URL data) {
        return delegate.serialize(topic, data.toString());
    }

    @Override
    public byte[] serialize(String topic, Headers headers, URL data) {
        return delegate.serialize(topic, headers, data.toString());
    }

    @Override
    public void close() {
        delegate.close();
    }
}
