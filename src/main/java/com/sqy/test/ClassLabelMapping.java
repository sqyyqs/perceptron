package com.sqy.test;

import java.util.*;

public class ClassLabelMapping {

    public static final Map<Character, Integer> MAPPING = new HashMap<>() {{
        put('儿', 1);
        put('入', 2);
        put('厄', 3);
        put('因', 4);
        put('士', 5);
        put('壬', 6);
        put('大', 7);
        put('廾', 8);
        put('文', 9);
        put('旧', 10);
    }};

    public static int from(InputData inputData) {
        return MAPPING.get(inputData.label());
    }
}
