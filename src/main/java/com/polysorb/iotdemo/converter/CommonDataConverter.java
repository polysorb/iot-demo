package com.polysorb.iotdemo.converter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.netty.buffer.ByteBuf;
import org.springframework.stereotype.Service;

@Service
public class CommonDataConverter {
    public void convertTemperature(ByteBuf buf, ObjectNode node){
        ObjectMapper om = new ObjectMapper();
        int tempH = (int)buf.getByte(12)&0xff;
        int tempL = (int)buf.getByte(13)&0xff;
        ObjectNode tempDetail = om.createObjectNode();
        tempDetail.put("value", tempH+"."+tempL);
        tempDetail.put("number", 1);
        tempDetail.put("unit", "°C");
        node.set("temperature", tempDetail);
    }

    public void convertHumidity(ByteBuf buf, ObjectNode node) {
        ObjectMapper om = new ObjectMapper();
        int humidH = (int)buf.getByte(14)&0xff;
        int humidL = (int)buf.getByte(15)&0xff;
        ObjectNode humidDetail = om.createObjectNode();
        humidDetail.put("value", humidH+"."+humidL);
        humidDetail.put("number", 2);
        humidDetail.put("unit", "%RH");
        node.set("humidity", humidDetail);
    }

    public void convertLqi(ByteBuf buf, ObjectNode node) {
        ObjectMapper om = new ObjectMapper();
        int lqi = (int)buf.getByte(45)&0xff;
        ObjectNode lqiDetail = om.createObjectNode();
        lqiDetail.put("value", lqi);
        lqiDetail.put("number", 0);
        node.set("lqi", lqiDetail);
    }
}
