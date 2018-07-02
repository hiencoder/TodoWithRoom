package vn.app.base.api.volley.core;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import vn.app.base.util.DebugLog;


public abstract class BaseTypeRequest<T> extends Request<T> {

    public static final String JSON_CONTENT_TYPE = "application/json; charset=utf-8";

    protected final Class<T> clazz;
    protected final Response.Listener<T> listener;
    protected final Map<String, String> header;
    protected final Map<String, String> params;
    protected Map<String, String> responseHeader;
    protected GsonRequestHeaderOnResult gsonRequestHeaderOnResult;
    protected boolean isPostRawContent = false;
    protected String requestContent = null;

    public BaseTypeRequest(int method, String url, Response.ErrorListener errorListener, Class<T> clazz, Response.Listener<T> listener, Map<String, String> header, Map<String, String> params) {
        super(method, url, errorListener);
        this.clazz = clazz;
        this.listener = listener;
        this.header = header;
        this.params = params;
    }

    public void updateParam(String key, String value) {
        if (params != null) {
            params.put(key, value);
        }
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        return header != null ? header : super.getHeaders();
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    public void setGsonRequestHeaderOnResult(GsonRequestHeaderOnResult mGsonRequestHeaderOnResult) {
        this.gsonRequestHeaderOnResult = mGsonRequestHeaderOnResult;
    }

    public interface GsonRequestHeaderOnResult {
        void onGsonRequestHeaderResult(Map<String, String> mHeaders);
    }

    public void setPostRawContent(boolean postRawContent) {
        isPostRawContent = postRawContent;
    }

    public void setRequestContent(String requestContent) {
        this.requestContent = requestContent;
    }

    @Override
    public String getBodyContentType() {
        if (isPostRawContent) {
            return JSON_CONTENT_TYPE;
        }
        return super.getBodyContentType();
    }

    @Override
    public byte[] getBody() throws AuthFailureError {
        if (isPostRawContent && this.requestContent != null) {
            try {
                DebugLog.i("getBody() content: " + this.requestContent);
                return this.requestContent.getBytes("utf-8");
            } catch (UnsupportedEncodingException e) {
                DebugLog.i(e.getMessage());
                return null;
            }
        }
        return super.getBody();
    }
}
