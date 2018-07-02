package vn.app.base.api.volley.core;

import com.android.volley.Request;

import java.io.File;
import java.util.Map;

import vn.app.base.BaseApplication;
import vn.app.base.util.NetworkUtils;


public abstract class UploadBinaryApiRequest<T> extends BaseApiRequest<T> {

    private Map<String, File> requestFiles;

    @Override
    protected void excecuteRequest() {
        super.excecuteRequest();
        baseTypeRequest = new MultiPartRequest<>(Request.Method.POST, createRequestUrl(), getErrorListener(), getResponseClass(), getRequestHeaders(), getListener(), handleRequestParams(), requestFiles);
        baseTypeRequest.setGsonRequestHeaderOnResult(this);
        baseTypeRequest.setRetryPolicy(getDefaultRetryPolicy());
        NetworkUtils.getInstance(BaseApplication.getInstance()).addToRequestQueue(baseTypeRequest);
    }

    public void setRequestFiles(Map<String, File> requestFiles) {
        this.requestFiles = requestFiles;
    }
}
