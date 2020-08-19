package com.sdgtt.service.impl;

import com.sdgtt.model.auto.WorkOrder;
import com.sdgtt.mapper.auto.WorkOrderMapper;
import com.sdgtt.service.IWorkOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.sdgtt.util.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 工单表（原小曼数据） 服务实现类
 * </p>
 *
 * @author astupidcoder
 * @since 2020-08-18
 */
@Slf4j
@Service
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrder> implements IWorkOrderService {

    @Autowired
    private WorkOrderMapper workOrderMapper;

    @Override
    public WorkOrder findById(int i) {
        return workOrderMapper.selectById(i);
    }

    @Override
    public List<WorkOrder> findAllUser() {
        return workOrderMapper.findAllUser();
    }

    @Override
    public void insertAllListInnerPage(List<WorkOrder> list) {


        int pageSize = 10000;

        // 总页数
        int pages = list.size() / pageSize;

        int mo = list.size() % pageSize;
        if (mo > 0) {
            pages++;
        }
        for (int currentPage = 0; currentPage < pages; currentPage++) {
            int begin = currentPage * pageSize;
            int end = Math.min(list.size(), (currentPage + 1) * pageSize);
            workOrderMapper.insertList(list.subList(begin, end));
            log.info("导入{}--{}", begin, end);
        }
    }

    @Override
    public void insertListOnePage(List<WorkOrder> orderList) {
        workOrderMapper.insertList(orderList);
    }
}
