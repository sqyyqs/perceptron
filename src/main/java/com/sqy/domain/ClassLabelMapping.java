package com.sqy.domain;

import java.util.*;

public class ClassLabelMapping {

    public static final Map<Character, Integer> MAPPING = new HashMap<>() {{
        put('儿', 0);
        put('入', 1);
        put('厄', 2);
        put('因', 3);
        put('士', 4);
        put('壬', 5);
        put('大', 6);
        put('廾', 7);
        put('文', 8);
        put('旧', 9);
    }};

    public static int from(InputData inputData) {
        return MAPPING.get(inputData.label());
    }
}
