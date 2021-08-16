package com.jyd.core.constant;

/**
 * @author Zhenlin Jin
 * @date 2021/8/13 9:19
 */
public enum CodeEnum {
    REPORT_COMMAND("commandReport", "01"),
    REPORT_EVENT("eventReport", "02"),
    REPORT_SCHEDULE("scheduleReport", "03");

    private String name;
    private String code;

    CodeEnum(String name, String code) {
        this.name = name;
        this.code = code;
    }

    public static String getBeanNameByCode(String code) {
        switch (code) {
            case "01":
                return REPORT_COMMAND.name;
            case "02":
                return REPORT_EVENT.name;
            case "03":
                return REPORT_SCHEDULE.name;
            default:
                return null;
        }
    }
}
