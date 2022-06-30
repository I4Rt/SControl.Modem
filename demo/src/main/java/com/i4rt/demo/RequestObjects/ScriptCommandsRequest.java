package com.i4rt.demo.RequestObjects;

import lombok.*;

@Data
public class ScriptCommandsRequest {
    String to;
    String type;
    String[] params;
    Integer order;

    public String getJson(){
        return "{" +
                "\"order\":" + "\"" + order + "\"" +
                "\"to\":" + "\"" + to + "\"" +
                "\"type\":" + "\"" + type + "\"" +
                "\"params\":" + params +
                "}";

    }
}
