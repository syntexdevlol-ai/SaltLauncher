package net.kdt.pojavlaunch.utils;

import android.util.ArrayMap;

public class JSONUtils {
    /**
    * Simple passthrough used by control expressions; for now we just
    * replace any known variables using the provided map if present.
    */
    public static String insertSingleJSONValue(String template, ArrayMap<String, String> values) {
        if(template == null || values == null) return template;
        String result = template;
        for (String key : values.keySet()) {
            result = result.replace("${" + key + "}", values.get(key));
        }
        return result;
    }
}
