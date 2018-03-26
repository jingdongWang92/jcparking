package com.jcble.jcparking.api.test;

import java.util.HashMap;
import java.util.Map;

public abstract class TestParamMap implements TestParam {

    private Map<String, String> map = new HashMap<>();

    @Override
    public Map<String, String> get() {
        setValue();
        return map;
    }

    public void V(String key, String value) {
        map.put(key, value);
    }
}
