package my.wiki.common;

import org.apache.kafka.common.header.Headers;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.net.URI;
import java.util.Map;

public class UriSerializer implements Serializer<URI> {
    private final StringSerializer delegate = new StringSerializer();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
        delegate.configure(configs, isKey);
    }

    @Override
    public byte[] serialize(String topic, URI data) {
        return delegate.serialize(topic, data.toString());
    }

    @Override
    public byte[] serialize(String topic, Headers headers, URI data) {
        return delegate.serialize(topic, headers, data.toString());
    }

    @Override
    public void close() {
        delegate.close();
    }
}
