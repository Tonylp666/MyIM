package com.tonylp.server.utils;

import java.util.UUID;

public class IDUtil {
    public static String randomId(){
        return UUID.randomUUID().toString().split("-")[0];
    }
}
