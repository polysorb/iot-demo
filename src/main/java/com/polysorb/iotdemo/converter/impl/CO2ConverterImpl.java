package com.polysorb.iotdemo.converter.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.polysorb.iotdemo.converter.CO2Converter;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Service;

@Service
public class CO2ConverterImpl implements CO2Converter {
    @Override
    public JsonNode convert(ByteBuf buf) {
        ObjectMapper om = new ObjectMapper();
        ObjectNode node = om.createObjectNode();

        // temperature
        int tempH = (int)buf.getByte(12)&0xff;
        int tempL = (int)buf.getByte(13)&0xff;
        ObjectNode tempDetail = om.createObjectNode();
        tempDetail.put("value", tempH+"."+tempL);
        tempDetail.put("number", 1);
        tempDetail.put("unit", "C");
        node.set("temperature", tempDetail);

        // voltage
        int voltageH = (int)buf.getByte(16)&0xff;
        int voltageL = (int)buf.getByte(17)&0xff;
        ObjectNode voltageDetail = om.createObjectNode();
        voltageDetail.put("value", voltageH+"."+voltageL);
        voltageDetail.put("number", 3);
        voltageDetail.put("unit", "V");
        node.set("voltage", voltageDetail);

        // lqi
        int lqi = (int)buf.getByte(45)&0xff;
        ObjectNode lqiDetail = om.createObjectNode();
        lqiDetail.put("value", lqi);
        lqiDetail.put("number", 0);
        node.set("lqi", lqiDetail);

        return node;
    }
}
