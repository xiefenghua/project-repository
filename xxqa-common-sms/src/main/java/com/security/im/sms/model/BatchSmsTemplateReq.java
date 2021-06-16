package com.security.im.sms.model;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * 批量短信模板类短信请求
 *
 * @author dbox
 * @Date 2018年2月23日 下午3:07:09
 */
public class BatchSmsTemplateReq extends SmsBaseReq {
    private static final long serialVersionUID = 1L;

    /**
     * 短信模版 第三方通道申请的短信模版
     */
    private String templateCode;

    /**
     * 接收用户，云信最大100个
     */
    private List<String> mobiles;

    /**
     * 模板参数集合（按模板中的顺序添加）,每个变量长度不能超过30字
     */
    private List<String> templateParams;

    public BatchSmsTemplateReq() {
    }

    public BatchSmsTemplateReq(String templateCode, List<String> mobiles) {
        this.templateCode = templateCode;
        this.mobiles = mobiles;
    }

    public BatchSmsTemplateReq(String templateCode, List<String> mobiles, String signName) {
        this.templateCode = templateCode;
        this.mobiles = mobiles;
        this.signName = signName;
    }

    public BatchSmsTemplateReq(String templateCode, List<String> mobiles, List<String> params) {
        this.templateCode = templateCode;
        this.mobiles = mobiles;
        this.templateParams = params;
    }

    public BatchSmsTemplateReq(String templateCode, List<String> mobiles, String signName, List<String> params) {
        this.templateCode = templateCode;
        this.mobiles = mobiles;
        this.signName = signName;
        this.templateParams = params;
    }

    @Override
    public void formatCheck() {
        if (StringUtils.isBlank(templateCode)) {
            throw new IllegalArgumentException(String.format("[%s]不能为空。", templateCode));
        }

        if (getMobiles() == null || getMobiles().isEmpty()) {
            throw new IllegalArgumentException(String.format("[%s]不能为空。", "mobiles"));
        }
        if (getMobiles().size() > 100) {
            throw new IllegalArgumentException(String.format("[mobiles]最大100个。", "mobiles"));
        }
        if (templateParams != null) {
            templateParams.forEach(yunxinParam -> {
                if (yunxinParam.length() > 30){
                    throw new IllegalArgumentException("[templateParams]每个变量长度不能超过30字");
                }
            });
        }
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public List<String> getMobiles() {
        return mobiles;
    }

    public void setMobiles(List<String> mobiles) {
        this.mobiles = mobiles;
    }

    public List<String> getTemplateParams() {
        return templateParams;
    }

    public void setTemplateParams(List<String> templateParams) {
        this.templateParams = templateParams;
    }
}
