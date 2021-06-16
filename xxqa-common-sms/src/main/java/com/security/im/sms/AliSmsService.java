package com.security.im.sms;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import com.security.im.common.util.JsonUtils;
import com.security.im.sms.model.AliSmsConfig;
import com.security.im.sms.model.SmsSendResult;
import com.security.im.sms.model.SmsTemplateReq;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author dbox
 * @Date 2018年1月24日 下午4:45:35
 */
public class AliSmsService implements SmsService {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public SmsSendResult sendTemplateSms(SmsTemplateReq smsTemplateReq) {
        // 验证请求参数
        smsTemplateReq.formatCheck();

        try {
            // 初始化acsClient,暂不支持region化
            IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", AliSmsConfig.ACCESS_KEY,
                    AliSmsConfig.ACCESS_SECRET);
            DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", AliSmsConfig.PRODUCT, AliSmsConfig.DOMAIN);
            IAcsClient acsClient = new DefaultAcsClient(profile);

            // 组装请求对象-具体描述见控制台-文档部分内容
            SendSmsRequest request = new SendSmsRequest();
            // 必填:待发送手机号
            request.setPhoneNumbers(smsTemplateReq.getMobile());
            // 必填:短信签名-可在短信控制台中找到
            request.setSignName(StringUtils.isBlank(smsTemplateReq.getSignName()) ? AliSmsConfig.SIGN_NAME
                    : smsTemplateReq.getSignName());
            // 必填:短信模板-可在短信控制台中找到
            request.setTemplateCode(smsTemplateReq.getTemplateCode());
            // 可选:模板中的变量替换JSON串,如模板内容为"亲爱的${name},您的验证码为${code}"时,此处的值为
            Map<String, String> params = smsTemplateReq.getParams();
            if (params != null && params.size() > 0) {
                Iterator<Entry<String, String>> ite = params.entrySet().iterator();
                String templateParam = "{";
                while (ite.hasNext()) {
                    Entry<String, String> entry = ite.next();
                    templateParam += "\"" + entry.getKey() + "\":\"" + entry.getValue() + "\",";
                }
                templateParam = templateParam.substring(0, templateParam.length() - 1);
                templateParam += "}";
                request.setTemplateParam(templateParam);
            }

            // hint 此处可能会抛出异常，注意catch
            SendSmsResponse sendSmsResponse = acsClient.getAcsResponse(request);

            // 返回对象 to 字符串
            String thirdRespStr = JsonUtils.getJson(sendSmsResponse);

            if (logger.isDebugEnabled()) {
                logger.debug("短信发送，mobile:" + smsTemplateReq.getMobile() + ";request:" + JsonUtils.getJson(params)
                        + ";response:" + thirdRespStr);
            }

            if (!"OK".equals(sendSmsResponse.getCode())) {
                logger.error("阿里云短信发送请求失败，mobile:" + smsTemplateReq.getMobile() + ";request:"
                        + JsonUtils.getJson(params) + ";response:" + thirdRespStr);
                return new SmsSendResult(false, sendSmsResponse.getMessage(), thirdRespStr);
            } else {
                return new SmsSendResult(true, thirdRespStr);
            }
        } catch (Exception e) {
            logger.error("阿里云短信发送请求失败", e);
            return new SmsSendResult(false, "阿里云短信发送请求失败", e.getMessage());
        }
    }
}
