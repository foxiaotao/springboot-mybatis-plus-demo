package com.sdgtt.model.workorder;


import lombok.Data;

/**
 * <p>
 * 工单表（原小曼数据）
 * </p>
 *
 * @author suntao
 * @since 2020-08-18
 */
@Data
public class WorkOrderSimple {

    private Long id;

    /**
     * 工单id
     */
    private Long workOrderId;

    /**
     * 姓名
     */
    private String userName;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 主题
     */
    private String subject;

    /**
     * 渠道来源
     */
    private String fromChannel;

    /**
     * 类型1
     */
    private String type1;

    /**
     * 类型2
     */
    private String type2;

    /**
     * 类型3
     */
    private String type3;

    /**
     * 优先级0：无，1：低；2：中；3：高
     */
    private Integer priority;

    /**
     * 创建人姓名
     */
    private String createUsername;

    /**
     * 操作人员姓名
     */
    private String opUsername;

    /**
     * 状态 0：创建，1：待处理；2：处理中；3：完成；4：重启；5：关闭
     */
    private Integer status;

    /**
     * 流转次数
     */
    private Integer durationTimes;

    /**
     * 流转时长
     */
    private Integer duration;

    /**
     * 创建时间
     */
    private String createdAt;

    /**
     * 操作时间
     */
    private String opAt;

    /**
     * 更新时间
     */
    private String updatedAt;

    /**
     * 内容说明
     */
    private String remark;


}
