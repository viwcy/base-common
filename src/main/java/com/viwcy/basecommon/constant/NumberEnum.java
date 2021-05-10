package com.viwcy.basecommon.constant;

/**
 * TODO //
 *
 * <p> Title: NumberEnum </p>
 * <p> Description: NumberEnum </p>
 * <p> History: 2020/9/4 23:02 </p>
 * <pre>
 *      Copyright: Create by FQ, ltd. Copyright(©) 2020.
 * </pre>
 * Author  FQ
 * Version 0.0.1.RELEASE
 */
public enum NumberEnum {

    TEN_MILLION("TEN_MILLION", 1, "日均千万级", "00000001"),
    ONE_MILLION("ONE_MILLION", 2, "日均百万级", "0000001"),
    ONE_HUNDRED_THOUSAND("ONE_HUNDRED_THOUSAND", 3, "日均十万级", "000001"),
    TEN_THOUSAND("TEN_THOUSAND", 4, "日均万级", "00001"),
    ONE_THOUSAND("ONE_THOUSAND", 5, "日均千级", "0001");

    private String name;
    private int value;
    private String describe;
    private String no;

    NumberEnum(String name, int value, String describe, String no) {
        this.name = name;
        this.value = value;
        this.describe = describe;
        this.no = no;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }

    public String getDescribe() {
        return describe;
    }

    public String getNo() {
        return no;
    }

    /**
     * 获取name值
     */
    public static String getName(int value) {
        NumberEnum[] values = values();
        for (NumberEnum numberEnum : values) {
            if (numberEnum.getValue() == value) {
                return numberEnum.getName();
            }
        }
        return null;
    }

    /**
     * 获取no值
     */
    public static String getNo(int value) {
        NumberEnum[] values = values();
        for (NumberEnum numberEnum : values) {
            if (numberEnum.getValue() == value) {
                return numberEnum.getNo();
            }
        }
        return null;
    }
}
