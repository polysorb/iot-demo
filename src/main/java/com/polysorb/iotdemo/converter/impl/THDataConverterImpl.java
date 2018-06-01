package com.polysorb.iotdemo.converter.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.polysorb.iotdemo.converter.CommonDataConverter;
import com.polysorb.iotdemo.converter.THDataConverter;
import com.polysorb.iotdemo.model.constants.IOTData;
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
        int voltageH = buf.getShort(IOTData.VOLTAGE_H);
        ObjectNode voltageDetail = om.createObjectNode();
        voltageDetail.put("value", (float)voltageH/1000);
        voltageDetail.put("number", 3);
        voltageDetail.put("unit", "V");
        node.set("voltage", voltageDetail);

        // lqi
        converter.convertLqi(buf, node);

        return node;
    }
}
