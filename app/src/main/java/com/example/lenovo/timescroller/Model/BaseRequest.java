package com.example.lenovo.timescroller.Model;

/**
 * Created by wanghuan on 14/11/26.
 */
public class BaseRequest<T> {

    private String _sign_ = null;
    private String _wid_ = null;
    private String _token_ = null;
    private String timeStamp = null;
    private String shop_id = null;
    private String BaseAppType = null;
    private String BaseAppVersion = null;
    private String SystemVersion = null;
    private String serviceName = null;
    private String methodName = null;
    private T parameterInput = null;
    /*************此参数用来区分  萌店  还是 一元夺宝 独立ap****************/
    private String appIdentifier = null;

    public void setAppIdentifier(String appIdentifier) {
        this.appIdentifier = appIdentifier;
    }

    public String getAppIdentifier() {
        return appIdentifier;
    }

    public String get_sign_() {
        return _sign_;
    }

    public void set_sign_(String _sign_) {
        this._sign_ = _sign_;
    }

    public String get_wid_() {
        return _wid_;
    }

    public void set_wid_(String _wid_) {
        this._wid_ = _wid_;
    }

    public String get_token_() {
        return _token_;
    }

    public void set_token_(String _token_) {
        this._token_ = _token_;
    }

    public String getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getShop_id() {
        return shop_id;
    }

    public void setShop_id(String shop_id) {
        this.shop_id = shop_id;
    }

    public String getBaseAppType() {
        return BaseAppType;
    }

    public void setBaseAppType(String baseAppType) {
        BaseAppType = baseAppType;
    }

    public String getBaseAppVersion() {
        return BaseAppVersion;
    }

    public void setBaseAppVersion(String baseAppVersion) {
        BaseAppVersion = baseAppVersion;
    }

    public String getSystemVersion() {
        return SystemVersion;
    }

    public void setSystemVersion(String systemVersion) {
        SystemVersion = systemVersion;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public T getParameterInput() {
        return parameterInput;
    }

    public void setParameterInput(T parameterInput) {
        this.parameterInput = parameterInput;
    }
}
