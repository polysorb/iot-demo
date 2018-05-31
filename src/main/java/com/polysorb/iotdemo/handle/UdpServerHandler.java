package com.polysorb.iotdemo.handle;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.Date;

@Slf4j
public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private static int count = 0;

    @Override
    public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {

        String receiveMsg = packet.content().toString(CharsetUtil.UTF_8);

        log.info("Received UDP Msg:" + receiveMsg);

        if (StringUtils.isNotEmpty(receiveMsg) ){

            //count message number
            count++;
            // TODO: convert to json

            // TODO: print in console


//            ctx.write(new DatagramPacket(
//                    Unpooled.copiedBuffer("QOTM: " + "Got UDP Message!" , CharsetUtil.UTF_8), packet.sender()));

        }else{
            log.error("Received Error UDP Messsage:" + receiveMsg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        // We don't close the channel because we can keep serving requests.
    }

    public Timestamp getTime(){
        Date date = new Date();
        Timestamp time = new Timestamp(date.getTime());
        return time;
    }

}