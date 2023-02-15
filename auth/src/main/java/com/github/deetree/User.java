package com.github.deetree;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Mariusz Bal
 */
record User(String email, String name) {

    Map<String, Object> convertExtras() {
        return new HashMap<>() {{
            put("name", name);
        }};
    }
}
