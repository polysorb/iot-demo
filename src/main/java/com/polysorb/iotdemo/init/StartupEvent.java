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

            //接收UDP消息并保存至redis中
            UdpServer udpServer = (UdpServer)StartupEvent.getBean(UdpServer.class);
            udpServer.run(sysConfig.getUdpReceivePort());


//            这里可以开启多个线程去执行不同的任务
//            此处为工作的内容，不便公开！


        } catch (Exception e) {
            log.error("Exception", e);
        }
    }

    public static Object getBean(Class beanName) {
        return context != null ? context.getBean(beanName) : null;
    }
}
