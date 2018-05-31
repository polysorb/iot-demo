package com.polysorb.iotdemo.init;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="sysconfig")
public class SysConfig {
    private int UdpReceivePort;

    private int CorePoolSize;

    private int MaxPoolSize;

    private int KeepAliveSeconds;

    private int QueueCapacity;

    public int getCorePoolSize() {
        return CorePoolSize;
    }

    public void setCorePoolSize(int corePoolSize) {
        CorePoolSize = corePoolSize;
    }

    public int getMaxPoolSize() {
        return MaxPoolSize;
    }

    public void setMaxPoolSize(int maxPoolSize) {
        MaxPoolSize = maxPoolSize;
    }

    public int getKeepAliveSeconds() {
        return KeepAliveSeconds;
    }

    public void setKeepAliveSeconds(int keepAliveSeconds) {
        KeepAliveSeconds = keepAliveSeconds;
    }

    public int getQueueCapacity() {
        return QueueCapacity;
    }

    public void setQueueCapacity(int queueCapacity) {
        QueueCapacity = queueCapacity;
    }

    public int getUdpReceivePort() {
        return UdpReceivePort;
    }

    public void setUdpReceivePort(int udpReceivePort) {
        UdpReceivePort = udpReceivePort;
    }
}

