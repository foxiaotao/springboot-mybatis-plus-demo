package com.sdgtt.model.workorder;

import lombok.Getter;

/**
 * @description: 工单优先级
 * @author:tao
 * @create: 2020-08-14 15:40
 */
public enum WorlOrderPriority {
    UNDEFINED("未知"),
    LOW("低"),
    MIDDLE("中"),
    HIGH("高");

    @Getter
    private String desc;

    WorlOrderPriority(String desc) {
        this.desc = desc;
    }



    public static WorlOrderPriority getWorlOrderPriority(String desc) {
        for (WorlOrderPriority priority : WorlOrderPriority.values()) {
            if (priority.getDesc().equals(desc)) {
                return priority;
            }
        }
        return null;
    }
}
