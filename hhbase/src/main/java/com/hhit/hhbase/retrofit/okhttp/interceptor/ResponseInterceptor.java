package com.hhit.hhbase.retrofit.okhttp.interceptor;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Created by xiaopeng on 2017/11/25.
 */

public class ResponseInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if(response.code() == -1001){
        }
        return response;
    }
}
