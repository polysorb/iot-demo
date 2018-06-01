package com.polysorb.iotdemo.converter.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.polysorb.iotdemo.converter.CO2DataConverter;
import com.polysorb.iotdemo.converter.CommonDataConverter;
import com.polysorb.iotdemo.model.constants.IOTData;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Service;

@Service
public class CO2DataConverterImpl implements CO2DataConverter {
    private final CommonDataConverter converter;

    public CO2DataConverterImpl(CommonDataConverter converter) {
        this.converter = converter;
    }

    @Override
    public JsonNode convert(ByteBuf buf) {
        ObjectMapper om = new ObjectMapper();
        ObjectNode node = om.createObjectNode();

        // CO2
        int co2H = buf.getShort(IOTData.CO2_H);
        ObjectNode co2Detail = om.createObjectNode();
        co2Detail.put("value", (float)co2H);
        co2Detail.put("number", 3);
        co2Detail.put("unit", "ppm");
        node.set("CO2", co2Detail);

        // humidity
        converter.convertHumidity(buf, node);

        // temperature
        converter.convertTemperature(buf, node);

        // lqi
        converter.convertLqi(buf, node);

        return node;
    }
}
