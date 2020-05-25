package src.server;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {
    private final static ObjectMapper objectMapper = new ObjectMapper();
    // Obj passed in must have an overridden toString() method that formats for JSON
    public static String getJsonString(Object obj) {
        String jsonString = null;
        try {
            jsonString = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
        }
        return jsonString;
    }

    public static Object deserialise(String jsonString, Class c) {
        try {
            return objectMapper.readValue(jsonString, c);
        } catch (JsonProcessingException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
