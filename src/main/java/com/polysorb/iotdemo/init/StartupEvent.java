package com.polysorb.iotdemo.init;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

@Slf4j
public class StartupEvent implements ApplicationListener<ContextRefreshedEvent> {
    private static ApplicationContext context;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        try {

            context = contextRefreshedEvent.getApplicationContext();

            SysConfig sysConfig = context.getBean(SysConfig.class);

            UdpServer udpServer = (UdpServer)StartupEvent.getBean(UdpServer.class);
            udpServer.run(sysConfig.getUdpReceivePort());


        } catch (Exception e) {
            log.error("Exception", e);
        }
    }

    public static Object getBean(Class beanName) {
        return context != null ? context.getBean(beanName) : null;
    }
}
