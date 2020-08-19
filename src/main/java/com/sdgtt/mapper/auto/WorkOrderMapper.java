package com.sdgtt.mapper.auto;

import com.sdgtt.model.auto.WorkOrder;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 * 工单表（原小曼数据） Mapper 接口
 * </p>
 *
 * @author astupidcoder
 * @since 2020-08-18
 */
public interface WorkOrderMapper extends BaseMapper<WorkOrder> {
    List<WorkOrder> findAllUser();
    void insertList(List<WorkOrder> orderList);
}
