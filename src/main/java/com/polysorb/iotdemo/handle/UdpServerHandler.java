package com.polysorb.iotdemo.handle;

import com.fasterxml.jackson.databind.JsonNode;
import com.polysorb.iotdemo.converter.CO2DataConverter;
import com.polysorb.iotdemo.converter.THDataConverter;
import com.polysorb.iotdemo.model.constants.IOTData;
import com.polysorb.iotdemo.model.constants.IOTTypes;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Slf4j
@Component
public class UdpServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {
    private final CO2DataConverter co2DataConverter;
    private final THDataConverter thDataConverter;

    public UdpServerHandler(CO2DataConverter co2DataConverter, THDataConverter thDataConverter) {
        this.co2DataConverter = co2DataConverter;
        this.thDataConverter = thDataConverter;
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, DatagramPacket packet) {
        String receiveMsg = packet.content().toString(CharsetUtil.ISO_8859_1);
//        log.info("Received UDP Msg:" + receiveMsg);

        if (StringUtils.isNotEmpty(receiveMsg) ){
            // CRC check
            if(!checkCRC(packet.content())){
                log.error("Received CRC Error UDP Messsage");
                return;
            }

            // type
            int type = packet.content().getUnsignedByte(IOTData.TYPE);
            log.info("type : " + type);
            // convert to json
            Optional<JsonNode> node = convertToJSON(type, packet.content());

            // print in console
            node.ifPresent(jsonNode -> log.info(jsonNode.toString()));

        }else{
            log.error("Received Error UDP Messsage:" + receiveMsg);
        }
    }

    private boolean checkCRC(ByteBuf content) {
        // 55 XOR byte2~byte18
        int CRCCalc = 55;

        for (int pos = 1; pos < 18; pos++) {
            CRCCalc ^= content.getUnsignedByte(pos);   // XOR byte into least sig. byte of crc
        }

        int CRC = content.getUnsignedByte(IOTData.CRC);
        return CRCCalc==CRC;
    }

    private Optional<JsonNode> convertToJSON(int type, ByteBuf content) {
        switch (type) {
            case IOTTypes.CO2:
                return Optional.of(co2DataConverter.convert(content));
            case IOTTypes.TH:
                return Optional.of(thDataConverter.convert(content));
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

}