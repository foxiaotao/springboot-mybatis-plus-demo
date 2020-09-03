package com.sdgtt.controller;


import com.sdgtt.core.JsonResult;
import com.sdgtt.model.auto.WorkOrder;
import com.sdgtt.service.IWorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 工单表（原小曼数据） 前端控制器
 * </p>
 *
 * @author astupidcoder
 * @since 2020-08-18
 */
@RestController
@RequestMapping("/work_order")
public class WorkOrderController {


    @Autowired
    private IWorkOrderService workOrderService;

    @GetMapping("test")
    public JsonResult test() {
        WorkOrder query = workOrderService.findById(1);
        return JsonResult.buildSuccessResult("success", query);
    }
}
