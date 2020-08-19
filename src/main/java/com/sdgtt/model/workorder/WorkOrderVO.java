package com.sdgtt.model.workorder;

import lombok.Data;

import java.io.Serializable;

/**
 * @description:
 * @author:tao
 * @create: 2020-08-13 17:07
 */
@Data
public class WorkOrderVO implements Serializable {

    private String workOrderId;

    private String userName;

    private String phone;

    private String subject;

    private String from;

    private String type1;

    private String type2;

    private String type3;

    /**
     * 优先级
     */
    private String priority;

    /**
     * 状态
     */
    private String status;

    private String createUsername;

    private String opUsername;

    /**
     * 流转次数
     */
    private String durationTimes;

    /**
     * 流转时长
     */
    private String duration;

    private String createdAt;

    private String opAt;

    private String updatedAt;

    private String remark;

}
