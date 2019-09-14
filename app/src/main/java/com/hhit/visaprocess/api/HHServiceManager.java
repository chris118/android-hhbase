package com.hhit.visaprocess.api;

import com.hhit.hhbase.retrofit.HttpManager;

/**
 * Created by chrisw on 2017/11/8.
 */

public class HHServiceManager {
    private static class SingletonHolder {
        private static final HHServiceManager instance = new HHServiceManager();
    }
    public static HHServiceManager getInstance() {
        return HHServiceManager.SingletonHolder.instance;
    }

    public UserApi getUserApi() {
        return HttpManager.RetrofitFactory
                .create(HttpManager.getInstance().getGson(),
                        HttpManager.getInstance().getOkHttpClient(),
                        BaseUrl.USER_SERVICE_BASE_URL)
                .create(UserApi.class);
    }
}
