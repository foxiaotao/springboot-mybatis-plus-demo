package com.sdgtt.model.workorder;

import lombok.Getter;

public enum WorkOrderStatus {
    CREATED("创建"),
    WAIT("等待"),
    DOING("处理中"),
    COMPLETE("完成"),
    RE_OPEN("重启"),
    CLOSE("关闭");


    @Getter
    private String desc;

    WorkOrderStatus(String desc) {
        this.desc = desc;
    }


    public static WorkOrderStatus getWorkOrderStatus(String desc) {
        for (WorkOrderStatus status : WorkOrderStatus.values()) {
            if (status.getDesc().equals(desc)) {
                return status;
            }
        }
        return null;
    }
}
