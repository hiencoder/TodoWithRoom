package vn.app.base.util;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Quang on 23/12/2014.
 */
public class JsonUtils {

    static final Gson gson = new Gson();

    public static Gson getGson() {
        return gson;
    }

    private static JsonParser parser = new JsonParser();

    /**
     * Instantiates a new json utils.
     */
    private JsonUtils() {
        // Contructor
    }

    /**
     * @param data
     * @return
     */
    public static JsonObject getJsonObjectFromString(String data) {
        return (JsonObject) parser.parse(data);
    }

    public static JsonArray getJsonArrayFromString(String data) {
        return (JsonArray) parser.parse(data);
    }

    /**
     * Get String content from assert file.
     *
     * @param fileName the file name
     * @param context  the context
     * @return Json in String
     */
    public static String getJsonFromAssets(String fileName, Context context) {
        BufferedReader reader;
        StringBuilder sb = new StringBuilder();
        try {
            reader = new BufferedReader(new InputStreamReader(context.getAssets().open(fileName), "UTF-8"));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            reader.close();

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * Get array from json primitive array.
     *
     * @param data the data
     * @return list of String
     */
    public static ArrayList<String> parserStringArray(JSONArray data) {
        if (data == null) {
            return null;
        }
        int arraySize = data.length();
        ArrayList<String> array = new ArrayList<String>(arraySize);
        for (int i = 0; i < arraySize; i++) {
            try {
                array.add(data.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return array;
    }

    /**
     * Get array from json primitive array.
     *
     * @param data the data
     * @return list of String
     */
    public static ArrayList<String> parserStringArray(JsonArray data) {
        if (data == null) {
            return null;
        }
        int arraySize = data.size();
        ArrayList<String> array = new ArrayList<String>(arraySize);
        for (int i = 0; i < arraySize; i++) {
            array.add(data.get(i).getAsString());
        }
        return array;
    }

    /**
     * Get array from json primitive array.
     *
     * @param data the data
     * @return list of Long
     */
    public static ArrayList<Long> parserLongArray(JsonArray data) {
        if (data == null) {
            return null;
        }
        int arraySize = data.size();
        ArrayList<Long> array = new ArrayList<Long>(arraySize);
        for (int i = 0; i < arraySize; i++) {
            array.add(data.get(i).getAsLong());
        }
        return array;
    }

    /**
     * Get array from json primitive array.
     *
     * @param data the data
     * @return list of Long
     */
    public static ArrayList<Long> parserLongArray(JSONArray data) {
        if (data == null) {
            return null;
        }
        int arraySize = data.length();
        ArrayList<Long> array = new ArrayList<Long>(arraySize);
        for (int i = 0; i < arraySize; i++) {
            try {
                array.add(data.getLong(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return array;
    }

    /**
     * Get String from Json by key.
     *
     * @param data the data
     * @param key  the key
     * @return String
     */
    public static String getString(JsonObject data, String key) {
        if (data.has(key) && !data.get(key).isJsonNull()) {
            return data.get(key).getAsString();
        } else {
            return null;
        }
    }

    /**
     * Get String from JSON by key.
     *
     * @param data the data
     * @param key  the key
     * @return String
     */
    public static String getString(JSONObject data, String key) {
        try {
            return data.getString(key);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Get long from Json by key.
     *
     * @param data the data
     * @param key  the key
     * @return long
     */
    public static long getLong(JsonObject data, String key) {
        if (data.has(key) && !data.get(key).isJsonNull()) {
            return data.get(key).getAsLong();
        } else {
            return 0L;
        }
    }

    /**
     * Get long from JSON by key.
     *
     * @param data the data
     * @param key  the key
     * @return long
     */
    public static long getLong(JSONObject data, String key) {
        try {
            return data.getLong(key);
        } catch (JSONException e) {
            return 0L;
        }
    }

    /**
     * Get JSONArray from JSON by key.
     *
     * @param data the data
     * @param key  the key
     * @return JSONArray
     */
    public static JSONArray getArray(JSONObject data, String key) {
        try {
            return data.getJSONArray(key);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * Get boolean from Json by key.
     *
     * @param data the data
     * @param key  the key
     * @return boolean
     */
    public static boolean getBoolean(JsonObject data, String key) {
        if (data.has(key) && !data.get(key).isJsonNull()) {
            return data.get(key).getAsBoolean();
        } else {
            return false;
        }
    }

    /**
     * Convert int, long to boolean.
     *
     * @param number the number
     * @return true if number > 0
     */
    public static boolean numberToBoolean(long number) {
        return (number > 0);
    }

    /**
     * Convert boolean to int.
     *
     * @param rightOrWrong the right or wrong
     * @return 1 : true/0 : false
     */
    public static int booleanToNumber(boolean rightOrWrong) {
        return rightOrWrong ? 1 : 0;
    }

}
