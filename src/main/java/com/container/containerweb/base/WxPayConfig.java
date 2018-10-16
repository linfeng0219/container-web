package com.container.containerweb.base;

import com.github.wxpay.sdk.WXPayConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class WxPayConfig implements WXPayConfig {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    private String appId;

    private String mchId;

    private String key;

    private String certPath;

    private Integer timeoutMs;

    public WxPayConfig(String appId, String mchId, String key, String certPath, Integer timeoutMs) {
        this.appId = appId;
        this.mchId = mchId;
        this.key = key;
        this.certPath = certPath;
        this.timeoutMs = timeoutMs;
    }

    @Override
    public String getAppID() {
        return this.appId;
    }

    @Override
    public String getMchID() {
        return this.mchId;
    }

    @Override
    public String getKey() {
        return this.key;
    }

    @Override
    public InputStream getCertStream() {
        try {
            return new FileInputStream(this.certPath);
        } catch (FileNotFoundException e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    @Override
    public int getHttpConnectTimeoutMs() {
        return this.timeoutMs;
    }

    @Override
    public int getHttpReadTimeoutMs() {
        return this.timeoutMs;
    }
}
