package com.polysorb.iotdemo.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.polysorb.iotdemo.model.constants.IOTData;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Service;

@Service
public class CommonDataConverter {
    public void convertTemperature(ByteBuf buf, ObjectNode node){
        ObjectMapper om = new ObjectMapper();
        int tempH = buf.getShort(IOTData.TEMP_H);
        ObjectNode tempDetail = om.createObjectNode();
        tempDetail.put("value", (float)tempH/100);
        tempDetail.put("number", 1);
        tempDetail.put("unit", "°C");
        node.set("temperature", tempDetail);
    }

    public void convertHumidity(ByteBuf buf, ObjectNode node) {
        ObjectMapper om = new ObjectMapper();
        int humidH = buf.getShort(IOTData.HUMID_H);
        ObjectNode humidDetail = om.createObjectNode();
        humidDetail.put("value", (float)humidH/100);
        humidDetail.put("number", 2);
        humidDetail.put("unit", "%RH");
        node.set("humidity", humidDetail);
    }

    public void convertLqi(ByteBuf buf, ObjectNode node) {
        ObjectMapper om = new ObjectMapper();
        int lqi = buf.getUnsignedByte(IOTData.LQI);
        ObjectNode lqiDetail = om.createObjectNode();
        lqiDetail.put("value", lqi);
        lqiDetail.put("number", 0);
        node.set("lqi", lqiDetail);
    }
}
