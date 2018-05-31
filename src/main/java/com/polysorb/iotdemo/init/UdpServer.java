package com.polysorb.iotdemo.init;

import com.polysorb.iotdemo.handle.UdpServerHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class UdpServer {
    @Async("myTaskAsyncPool")
    public void run(int udpReceivePort) {

        EventLoopGroup group = new NioEventLoopGroup();
        log.info("Server start!  Udp Receive msg Port:" + udpReceivePort );

        try {
            Bootstrap b = new Bootstrap();
            b.group(group)
                    .channel(NioDatagramChannel.class)
                    .option(ChannelOption.SO_BROADCAST, true)
                    .handler(new UdpServerHandler());

            b.bind(udpReceivePort).sync().channel().closeFuture().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();
        }
    }

}
