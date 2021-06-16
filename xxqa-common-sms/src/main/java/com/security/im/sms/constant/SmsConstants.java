package com.security.im.sms.constant;

import com.security.im.sms.model.AliSmsConfig;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 短信工具类常量配置
 * Created by wuzhu on 2018/2/24
 */
public abstract class SmsConstants {
    /**
     * 短信通道 默认为阿里
     */
    public final static String SMS_CHANNEL;

    static {
        Properties props = new Properties();
        InputStream input = null;
        String[] files = {"application.properties"};
        for (String file : files) {
            try {
                input = AliSmsConfig.class.getResourceAsStream("/" + file);
                BufferedReader bf = new BufferedReader(new InputStreamReader(input, "UTF-8"));
                props.load(bf);
            } catch (IOException e) {
                throw new RuntimeException("请配置application.properties", e);
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                    }
                }
            }
        }
        SMS_CHANNEL = StringUtils.defaultString(props.getProperty("sms.channel"), SmsChannelConstants.ALI).trim();
    }
}
