package com.polysorb.iotdemo.converter.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.polysorb.iotdemo.converter.CommonDataConverter;
import com.polysorb.iotdemo.converter.THDataConverter;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Service;

@Service
public class THDataConverterImpl implements THDataConverter {
    private final CommonDataConverter converter;

    public THDataConverterImpl(CommonDataConverter converter) {
        this.converter = converter;
    }

    @Override
    public JsonNode convert(ByteBuf buf) {
        ObjectMapper om = new ObjectMapper();
        ObjectNode node = om.createObjectNode();

        // humidity
        converter.convertHumidity(buf, node);

        // temperature
        converter.convertTemperature(buf, node);

        // voltage
        int voltageH = (int)buf.getByte(16)&0xff;
        int voltageL = (int)buf.getByte(17)&0xff;
        ObjectNode voltageDetail = om.createObjectNode();
        voltageDetail.put("value", voltageH+"."+voltageL);
        voltageDetail.put("number", 3);
        voltageDetail.put("unit", "V");
        node.set("voltage", voltageDetail);

        // lqi
        converter.convertLqi(buf, node);

        return node;
    }
}
