package com.security.im.sms.model;

import com.security.im.sms.constant.SmsChannelConstants;
import com.security.im.sms.constant.SmsConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * @author dbox
 * @Date 2018年1月24日 下午6:22:57
 */
public class AliSmsConfig {

    private static Logger logger = LoggerFactory.getLogger(AliSmsConfig.class);

    /**
     * 云通信短信API产品,开发者无需替换
     */
    public static final String PRODUCT = "Dysmsapi";
    /**
     * 产品域名,开发者无需替换
     */
    public static final String DOMAIN = "dysmsapi.aliyuncs.com";

    /**
     * 申请通道时的ACCESS_KEY
     */
    public final static String ACCESS_KEY;
    /**
     * 申请通道时的ACCESS_SECRET
     */
    public final static String ACCESS_SECRET;
    /**
     * 短信签名
     */
    public final static String SIGN_NAME;

    /**
     * 短信发送appkey
     */
    public final static String IM_SENDCODE_APPKEY;

    /**
     * 短信发送secret
     */
    public final static String IM_SENDCODE_SECRET;

    /**
     * 运营类短信appkey
     */
    public final static String IM_SMS_BUSINESS_APPKEY;

    /**
     * 运营类短信secret
     */
    public final static String IM_SMS_BUSINESS_SECRET;

    /**
     * 短信发送验证码模版
     */
    public final static String IM_SENDCODE_TEMPLATE;

    /**
     * 任务创建短信发送模板
     */
    public final static String IM_SENDCODE_TEMPLATE_TASK_CREATE;

    /**
     * 任务将逾期的短信发送模板
     */
    public final static String IM_SENDCODE_TEMPLATE_TASK_WILL_OVERDUE;
    /**
     * 消息通知提醒消息模板
     */
    public final static String IM_SENDCODE_MESSAGE_NOTICE_TEMPLATE;

    /**
     * 短信验证码长度
     */
    public final static String IM_SENDCODE_CODELEN;

    static {
        if (SmsConstants.SMS_CHANNEL.equals(SmsChannelConstants.ALI)) {
            Properties props = new Properties();
            InputStream input = null;
            String[] files = {"application.properties"};
            for (String file : files) {
                try {
                    input = AliSmsConfig.class.getResourceAsStream("/" + file);
                    BufferedReader bf = new BufferedReader(new InputStreamReader(input, "UTF-8"));
                    props.load(bf);
                } catch (IOException e) {
                    logger.error("获取短信配置异常：", e);
                } finally {
                    if (input != null) {
                        try {
                            input.close();
                        } catch (IOException e) {
                            logger.error("关闭流异常：", e);
                        }
                    }
                }
            }
            // 获得配置参数
            ACCESS_KEY = StringUtils.defaultString(props.getProperty("sms.ali.accessKey")).trim();
            ACCESS_SECRET = StringUtils.defaultString(props.getProperty("sms.ali.accessSecret")).trim();
            SIGN_NAME = StringUtils.defaultString(props.getProperty("sms.ali.signName")).trim();
            IM_SENDCODE_APPKEY = StringUtils.defaultString(props.getProperty("im.sendcode.appkey")).trim();
            IM_SENDCODE_SECRET = StringUtils.defaultString(props.getProperty("im.sendcode.secret")).trim();
            IM_SENDCODE_TEMPLATE = StringUtils.defaultString(props.getProperty("im.sendcode.templateid")).trim();
            IM_SENDCODE_CODELEN = StringUtils.defaultString(props.getProperty("im.sendcode.codelen")).trim();

            IM_SMS_BUSINESS_APPKEY = StringUtils.defaultString(props.getProperty("im.sms.business.appkey")).trim();
            IM_SMS_BUSINESS_SECRET = StringUtils.defaultString(props.getProperty("im.sms.business.secret")).trim();
            IM_SENDCODE_TEMPLATE_TASK_CREATE = StringUtils.defaultString(props.getProperty("im.sendcode.task-create-templateid")).trim();
            IM_SENDCODE_TEMPLATE_TASK_WILL_OVERDUE = StringUtils.defaultString(props.getProperty("im.sendcode.task-will-overdue-templateid")).trim();
            IM_SENDCODE_MESSAGE_NOTICE_TEMPLATE = StringUtils.defaultString(props.getProperty("im.sendcode.message-notice-templateid")).trim();

            if (StringUtils.isBlank(ACCESS_KEY)) {
                throw new RuntimeException("请在constants.properties配置文件中配置【sms.ali.accessKey】");
            }
            if (StringUtils.isBlank(ACCESS_SECRET)) {
                throw new RuntimeException("请在constants.properties配置文件中配置【sms.ali.accessSecret】");
            }
            if (StringUtils.isBlank(SIGN_NAME)) {
                throw new RuntimeException("请在constants.properties配置文件中配置【sms.ali.signName】");
            }
            if (StringUtils.isBlank(IM_SENDCODE_APPKEY)) {
                throw new RuntimeException("请在constants.properties配置文件中配置【im.sendcode.appkey】");
            }
            if (StringUtils.isBlank(IM_SENDCODE_SECRET)) {
                throw new RuntimeException("请在constants.properties配置文件中配置【im.sendcode.secret】");
            }
            if (StringUtils.isBlank(IM_SENDCODE_TEMPLATE)) {
                throw new RuntimeException("请在constants.properties配置文件中配置【im.sendcode.template】");
            }
            if (StringUtils.isBlank(IM_SENDCODE_CODELEN)) {
                throw new RuntimeException("请在constants.properties配置文件中配置【im.sendcode.codelen】");
            }
            if (StringUtils.isBlank(IM_SMS_BUSINESS_APPKEY)) {
                throw new RuntimeException("请在constants.properties配置文件中配置【im.sms.business.appkey】");
            }
            if (StringUtils.isBlank(IM_SMS_BUSINESS_SECRET)) {
                throw new RuntimeException("请在constants.properties配置文件中配置【im.sms.business.secret】");
            }
            if (StringUtils.isBlank(IM_SENDCODE_TEMPLATE_TASK_CREATE)) {
                throw new RuntimeException("请在constants.properties配置文件中配置【im.sendcode.task-create-templateid】");
            }
            if (StringUtils.isBlank(IM_SENDCODE_TEMPLATE_TASK_WILL_OVERDUE)) {
                throw new RuntimeException("请在constants.properties配置文件中配置【im.sendcode.task-will-overdue-templateid】");
            }
            if (StringUtils.isBlank(IM_SENDCODE_MESSAGE_NOTICE_TEMPLATE)) {
                throw new RuntimeException("请在constants.properties配置文件中配置【im.sendcode.message-notice-templateid】");
            }
        } else {
            ACCESS_KEY = "";
            ACCESS_SECRET = "";
            SIGN_NAME = "";
            IM_SENDCODE_APPKEY = "";
            IM_SENDCODE_SECRET = "";
            IM_SENDCODE_TEMPLATE = "";
            IM_SENDCODE_CODELEN = "";
            IM_SMS_BUSINESS_APPKEY = "";
            IM_SMS_BUSINESS_SECRET = "";
            IM_SENDCODE_TEMPLATE_TASK_CREATE = "";
            IM_SENDCODE_TEMPLATE_TASK_WILL_OVERDUE = "";
            IM_SENDCODE_MESSAGE_NOTICE_TEMPLATE = "";
        }
    }
}
