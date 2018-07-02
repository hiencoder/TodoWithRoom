package vn.app.base.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class APICacheUtil {

    private static APICacheUtil instance;
    private static Gson gson;

    public static APICacheUtil getInstance() {
        if (instance == null) {
            instance = new APICacheUtil();
        }
        return instance;
    }

    private APICacheUtil() {
        GsonBuilder builder = new GsonBuilder();
        gson = builder.create();
    }

    public void saveResult(Object obj, String key) {
        String json = gson.toJson(obj);
        SharedPrefUtils.putString(key, json);
    }

    public void saveResultAsync(Object obj, String key) {
        String json = gson.toJson(obj);
        SharedPrefUtils.putStringAsync(key, json);
    }

    public synchronized <T> T getResult(String key, Class<T> clazz) {
        String json = SharedPrefUtils.getString(key, "");
        if (StringUtil.isEmpty(json)) {
            return null;
        } else {
            try {
                return gson.fromJson(json, clazz);
            } catch (Exception ex) {
                return null;
            }
        }
    }

}
