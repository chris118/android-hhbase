package com.hhit.hhbase.retrofit.okhttp.request;

import com.hhit.hhbase.retrofit.okhttp.Logger;
import com.hhit.hhbase.retrofit.okhttp.progress.ProgressRequestBody;
import com.hhit.hhbase.retrofit.okhttp.progress.ProgressRequestListener;

import java.io.File;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.CacheControl;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * form-data请求构建类
 * Created by chrisw on 2015/11/24.
 */
public class MultiPartRequestBuilder {
    private static final String TAG = MultiPartRequestBuilder.class.getSimpleName();

    private Map<String, String> headers;//请求头参数
    private Map<String, Object> params;//请求参数

    private Request.Builder builder;

    private ProgressRequestListener mProgressRequestListener;

    public MultiPartRequestBuilder() {
        headers = new ConcurrentHashMap<>();
        params = new ConcurrentHashMap<>();

        builder = new Request.Builder();
        builder.cacheControl(new CacheControl.Builder().maxAge(60 * 5, TimeUnit.SECONDS).build());
    }

    public MultiPartRequestBuilder setProgressRequestListener(ProgressRequestListener progressRequestListener) {
        mProgressRequestListener = progressRequestListener;
        return this;
    }

    /**
     * 创建FileRequestHelper实例
     *
     * @return
     */
    public static MultiPartRequestBuilder newInstance() {
        return new MultiPartRequestBuilder();
    }

    /**
     * 添加请求头
     *
     * @param name  name
     * @param value value
     * @return RequestHelper实例
     */
    public MultiPartRequestBuilder addHeader(String name, String value) {
        headers.put(name, value);
        return this;
    }

    /**
     * 添加请求参数
     *
     * @param name  name
     * @param value value
     * @return RequestHelper实例
     */
    public MultiPartRequestBuilder addParam(String name, Object value) {
        params.put(name, value);
        return this;
    }

    /**
     * 创建请求体
     *
     * @return 请求体
     */
    public Request build(String url) {
        for (String name : headers.keySet()) {
            Object value = headers.get(name);
            builder.addHeader(name, String.valueOf(value));
        }
        Logger.d(TAG, "请求Header" + "\n" + "请求 头:" + "\n" + formatHeader(headers));


        MultipartBody.Builder builder = new MultipartBody.Builder();
        builder.setType(MultipartBody.FORM);
        for (String name : params.keySet()) {
            Object value = params.get(name);
            if (value instanceof String)
                builder.addFormDataPart(name, (String) value);
            else
                builder.addFormDataPart(name, null, RequestBody.create(getMediaType((File) value), (File) value));
        }
        RequestBody body = builder.build();
        Logger.d(TAG, "form-data请求,url=" + url + "\n" + "请求 参数:" + "\n" + formatParams(params));
        if (mProgressRequestListener != null)
            return this.builder.url(url).post(new ProgressRequestBody(body, mProgressRequestListener)).tag(url).build();
        else
            return this.builder.url(url).post(body).tag(url).build();
    }

    private MediaType getMediaType(File file) {
        return MediaType.parse("application/octet-stream");

//        String fileName = file.getName();
//        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
//        switch (prefix) {
//            case "png":
//                return MediaType.parse("image/png");
//            case "jpg":
//                return MediaType.parse("image/jpeg");
//            case "mp4":
//                return MediaType.parse("video/mp4");
//            case "mp3":
//                return MediaType.parse("audio/mpeg");
//        }
//        return null;
    }

    /**
     * 格式化显示参数信息
     *
     * @return 参数信息
     */
    private String formatParams(Map<String, Object> params) {
        StringBuffer sb = new StringBuffer();
        for (String name : params.keySet()) {
            sb.append(name).append("=").append(params.get(name)).append("\n");
        }
        return sb.delete(sb.length() - 1, sb.length()).toString();
    }

    /**
     * 格式化显示头信息
     *
     * @return 头信息
     */
    private String formatHeader(Map<String, String> headers) {
        StringBuffer sb = new StringBuffer();
        for (String name : headers.keySet()) {
            sb.append(name).append("=").append(headers.get(name)).append("\n");
        }
        return sb.delete(sb.length() - 1, sb.length()).toString();
    }
}
