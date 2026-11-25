
package com.automationexercises.utils.dataReader;

import com.automationexercises.utils.logs.LogsManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class JsonReader {

    private final String TEST_DATA_PATH = "src/test/resources/test-data/";

    String jsonReader;
    String jsonFileName;

    public JsonReader(String jsonFileName) {
        this.jsonFileName = jsonFileName;

        try {
            JSONObject data = (JSONObject) new JSONParser().parse(
                    new FileReader(TEST_DATA_PATH + jsonFileName + ".json")
            );

            jsonReader = data.toJSONString();

        } catch (Exception e) {
            LogsManager.error("Error reading JSON file: " + e.getMessage());
            jsonReader = "{}";
        }
    }

    // Get String Data
    public String getJsonData(String key) {
        try {
            Object value = getValue(key);
            return value.toString();

        } catch (Exception e) {
            LogsManager.error(
                    "Error getting JSON data for key: " + key + ", " + e.getMessage()
            );
            return null;
        }
    }

    // Get List Data
    public List<String> getJsonDataList(String key) {
        try {
            Object value = getValue(key);

            if (!(value instanceof JSONArray)) {
                throw new IllegalArgumentException(
                        "JSON value is not an Array: " + key
                );
            }

            JSONArray jsonArray = (JSONArray) value;
            List<String> dataList = new ArrayList<>();

            for (Object item : jsonArray) {
                dataList.add(item.toString());
            }

            return dataList;

        } catch (Exception e) {
            LogsManager.error(
                    "Error getting JSON List for key: " + key + ", " + e.getMessage()
            );
            return null;
        }
    }

    // Get JSON Value By Path
    private Object getValue(String key) throws Exception {

        JSONObject data = (JSONObject) new JSONParser().parse(jsonReader);

        Object value = data;

        for (String part : key.split("\\.")) {

            if (!(value instanceof JSONObject)) {
                throw new IllegalArgumentException(
                        "Invalid JSON path: " + key
                );
            }

            value = ((JSONObject) value).get(part);

            if (value == null) {
                throw new IllegalArgumentException(
                        "JSON key not found: " + key
                );
            }
        }

        return value;
    }
}
