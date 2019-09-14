package com.hhit.visaprocess;


import com.hhit.hhbase.base.BaseApplication;
import com.hhit.hhbase.utils.PreferencesUtils;
import com.hhit.visaprocess.api.BaseUrl;

/**
 * Created by chrisw on 2017/10/30.
 */

public class HHApplication extends BaseApplication {
    public HHApplication() {

    }

    @Override
    public void onCreate() {
        super.onCreate();
        if(PreferencesUtils.getEnviroment().equals("TEST")){
            BaseUrl.init(BaseUrl.HOST_TEST);
        }else {
            BaseUrl.init(BaseUrl.HOST_PRODUCT);
        }
    }
}
