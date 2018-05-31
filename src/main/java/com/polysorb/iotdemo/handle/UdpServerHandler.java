package com.polysorb.iotdemo.handle;

import com.fasterxml.jackson.databind.JsonNode;
import com.polysorb.iotdemo.converter.CO2Converter;
import com.polysorb.iotdemo.converter.impl.CO2ConverterImpl;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) throws Exception {

        String receiveMsg = packet.content().toString(CharsetUtil.ISO_8859_1);

        log.info("Received UDP Msg:" + receiveMsg);

        if (StringUtils.isNotEmpty(receiveMsg) ){
            // CRC check

            // type
            int type = (int)packet.content().getByte(9)&0xff;
            log.info("type : " + type);
            // convert to json
            Optional<JsonNode> node = convertToJSON(type, packet.content());

            // print in console
            node.ifPresent(jsonNode -> log.info(jsonNode.toString()));

//            ctx.write(new DatagramPacket(
//                    Unpooled.copiedBuffer("QOTM: " + "Got UDP Message!" , CharsetUtil.UTF_8), packet.sender()));

        }else{
            log.error("Received Error UDP Messsage:" + receiveMsg);
        }
    }

    private Optional<JsonNode> convertToJSON(int type, ByteBuf content) {
        switch (type) {
            case 132:
                return Optional.of(new CO2ConverterImpl().convert(content));
            default:
                return Optional.empty();
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