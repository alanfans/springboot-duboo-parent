package com.example.demo.provider.redisson;

import com.example.demo.provider.api.service.impl.CardJob;
import com.example.demo.provider.api.service.impl.MetroJob;

public enum Job {

   CARD("1", CardJob.class),
    METRO("2", MetroJob.class);

    private String code;
    private Class value;

    Job(String code, Class<?> Class) {
        this.code = code;
        this.value = Class;
    }

    public static Class getValueByCode(String code) {
        Job[] values = Job.values();
        for (Job type : values) {
            if (type.code.equalsIgnoreCase(code)) {
                return type.value;
            }
        }
        return null;
    }

}
