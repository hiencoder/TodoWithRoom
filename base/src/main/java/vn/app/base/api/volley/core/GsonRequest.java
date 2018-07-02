package vn.app.base.api.volley.core;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Response;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.nio.charset.Charset;
import java.util.Map;

import vn.app.base.util.DebugLog;


public class GsonRequest<T> extends BaseTypeRequest<T> {

    public static final String WARP_JSON_KEY = "data";

    private boolean isWarpJsonToObject = false;

    private boolean isHandlePlainText = false;

    private static Gson gson = getGson();

    private static JsonParser jsonParser = new JsonParser();

    private static Gson getGson() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).serializeNulls().create();
    }

    /**
     * Make a GET request and return a parsed object from JSON.
     *
     * @param url     URL of the request to make
     * @param clazz   Relevant class object, for Gson's reflection
     * @param headers Map of request headers
     */
    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers, Map<String, String> params,
                       Response.Listener<T> listener, Response.ErrorListener errorListener) {
        super(method, url, errorListener, clazz, listener, headers, params);
    }

    public GsonRequest(int method, String url, Class<T> clazz, Map<String, String> headers, Map<String, String> params,
                       Response.Listener<T> listener, Response.ErrorListener errorListener, boolean isWarpJsonToObject, boolean isHandlePlainText) {
        super(method, url, errorListener, clazz, listener, headers, params);
        this.isWarpJsonToObject = isWarpJsonToObject;
        this.isHandlePlainText = isHandlePlainText;
    }

    @Override
    protected void deliverResponse(T response) {
        listener.onResponse(response);
    }

    @Override
    protected Response<T> parseNetworkResponse(NetworkResponse response) {
        try {

            String json = new String(
                    response.data,
                    Charset.forName("UTF-8"));

            if (!isCanceled()) {
                DebugLog.i("url: " + getUrl() + "\njson: " + json);
            }

            if (isHandlePlainText) {
                isWarpJsonToObject = false;
                JsonObject wrapResult = new JsonObject();
                wrapResult.addProperty(WARP_JSON_KEY, json);
                json = wrapResult.toString();
                DebugLog.i("isHandlePlainText after handle: " + json);
            }

            if (isWarpJsonToObject) {
                JsonElement result = jsonParser.parse(json);
                JsonObject wrapResult = new JsonObject();
                wrapResult.add(WARP_JSON_KEY, result);
                json = wrapResult.toString();
                DebugLog.i("isWarpJsonToObject after warp: " + json);
            }

            Response<T> success = Response.success(
                    gson.fromJson(json, clazz),
                    HttpHeaderParser.parseCacheHeaders(response));

            this.responseHeader = response.headers;

            if (gsonRequestHeaderOnResult != null) {
                gsonRequestHeaderOnResult.onGsonRequestHeaderResult(responseHeader);
            }
            return success;
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
            return Response.error(new ParseError(e));
        }
    }
}
