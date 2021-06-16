package com.codemachine.platform.model;

/**
 * 对象基本信息
 *
 * @author wuzhu
 */
public class EnumBean {
    private String name;
    private String value;

    public EnumBean() {
    }

    public EnumBean(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
