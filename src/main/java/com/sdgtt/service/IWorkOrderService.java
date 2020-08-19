package com.sdgtt.service;

import com.sdgtt.model.auto.WorkOrder;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 工单表（原小曼数据） 服务类
 * </p>
 *
 * @author astupidcoder
 * @since 2020-08-18
 */
public interface IWorkOrderService extends IService<WorkOrder> {

    WorkOrder findById(int i);

    List<WorkOrder> findAllUser();

    void insertAllListInnerPage(List<WorkOrder> orderList);

    void insertListOnePage(List<WorkOrder> orderList);
}
