package com.polysorb.iotdemo.converter;

import com.fasterxml.jackson.databind.JsonNode;
import io.netty.buffer.ByteBuf;

public interface IotDataConverter {
    JsonNode convert(ByteBuf buf);
}
