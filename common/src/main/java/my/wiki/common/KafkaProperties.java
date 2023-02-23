package my.wiki.common;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("wiki-config")
public class KafkaProperties {
    private String rawUrlsTopic;
    private String uniqueUrlsTopic;
    private String crawlersGroup;
    private String unifiersGroup;

    public String getRawUrlsTopic() {
        return rawUrlsTopic;
    }

    public void setRawUrlsTopic(String rawUrlsTopic) {
        this.rawUrlsTopic = rawUrlsTopic;
    }

    public String getUniqueUrlsTopic() {
        return uniqueUrlsTopic;
    }

    public void setUniqueUrlsTopic(String uniqueUrlsTopic) {
        this.uniqueUrlsTopic = uniqueUrlsTopic;
    }

    public String getCrawlersGroup() {
        return crawlersGroup;
    }

    public void setCrawlersGroup(String crawlersGroup) {
        this.crawlersGroup = crawlersGroup;
    }

    public String getUnifiersGroup() {
        return unifiersGroup;
    }

    public void setUnifiersGroup(String unifiersGroup) {
        this.unifiersGroup = unifiersGroup;
    }
}
