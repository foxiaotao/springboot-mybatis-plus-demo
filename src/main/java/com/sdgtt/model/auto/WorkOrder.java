package com.sdgtt.model.auto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.util.Date;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 工单表（原小曼数据）
 * </p>
 *
 * @author astupidcoder
 * @since 2020-08-18
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class WorkOrder extends Model {

    private static final long serialVersionUID = 1L;

    /**
     * 物理主键
     */
    @TableId(value = "id", type = IdType.AUTO)
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
    private Date createdAt;

    /**
     * 操作时间
     */
    private Date opAt;

    /**
     * 更新时间
     */
    private Date updatedAt;

    /**
     * 内容说明
     */
    private String remark;


}
