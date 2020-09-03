package com.sdgtt.controller;

import com.sdgtt.core.JsonResult;
import com.sdgtt.model.auto.WorkOrder;
import com.sdgtt.model.workorder.WorkOrderStatus;
import com.sdgtt.model.workorder.WorlOrderPriority;
import com.sdgtt.service.IWorkOrderService;
import com.sdgtt.util.DateUtil;
import com.sdgtt.util.poi.Reader;
import com.sdgtt.util.poi.XlsxReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 分页读取excel 保存到数据库
 * 每读取一页的数据，insert一次
 * @author tao
 * @version 2020-08-14 16:28
 */
@Slf4j
@RestController()
@RequestMapping("wo")
public class WorkOrderInsertController implements Reader {

    @Autowired
    private IWorkOrderService workOrderService;
    @Autowired
    private ApplicationArguments applicationArguments;

    private Date update = new Date();

    private int pageSize = 500;

    private List<WorkOrder> list = new ArrayList<>(pageSize);


    @RequestMapping("insert")
    public JsonResult read(String fileName) throws Exception {
        // 从启动参数获取文件路径  jar运行
        String[] sourceArgs = applicationArguments.getSourceArgs();
        log.info("path={}", sourceArgs[0]);
        String basePath = sourceArgs[0] + "/excel/" + fileName;
        log.info("base_path={}", basePath);

        long start = System.currentTimeMillis();
        XlsxReader excelXlsxReader = new XlsxReader(this);

        int totalRows = excelXlsxReader.process(basePath);

        if (!CollectionUtils.isEmpty(list)) {
            // 最后一页数据还没保存
            workOrderService.insertListOnePage(list);
        }
        // 总页数
        int pages = (int)Math.ceil(totalRows / pageSize);
        log.info("加载总行数:{},总页数:{},耗时:{}", totalRows, pages, System.currentTimeMillis() - start);

        return JsonResult.buildSuccessResult("success", null);
    }

    /**
     *
     * @param filePath 文件路径
     * @param sheetName sheet名称
     * @param sheetIndex sheet序号
     * @param curRow 行号 真实行号
     * @param cellList 一行的所有单元格字段
     */
    @Override
    public void read(String filePath, String sheetName, int sheetIndex, int curRow, List<String> cellList) {
        WorkOrder workOrder = new WorkOrder();
        workOrder.setWorkOrderId(Long.valueOf(cellList.get(0)));
        workOrder.setDurationTimes(Integer.valueOf(cellList.get(1)));
        workOrder.setUserName(cellList.get(2));
        workOrder.setPhone(cellList.get(3));
        workOrder.setSubject(cellList.get(4));
        workOrder.setFromChannel(cellList.get(5));
        workOrder.setType1(cellList.get(6));
        workOrder.setType2(cellList.get(7));
        workOrder.setType3(cellList.get(8));
        final WorlOrderPriority worlOrderPriority = WorlOrderPriority.getWorlOrderPriority(cellList.get(9));
        workOrder.setPriority(worlOrderPriority != null ? worlOrderPriority.ordinal() : 0);
        workOrder.setCreateUsername(cellList.get(10));
        workOrder.setCreatedAt(DateUtil.dateStr2Date(cellList.get(11), DateUtil.DATE_FORMAT_1));
        workOrder.setOpUsername(cellList.get(12));
        workOrder.setDuration(Integer.valueOf(cellList.get(13)));
        final WorkOrderStatus workOrderStatus = WorkOrderStatus.getWorkOrderStatus(cellList.get(14));
        workOrder.setStatus(workOrderStatus != null ? workOrderStatus.ordinal() : 0);
        workOrder.setOpAt(DateUtil.dateStr2Date(cellList.get(15), DateUtil.DATE_FORMAT_1));
        workOrder.setUpdatedAt(update);
        workOrder.setRemark(StringUtils.replaceAll(cellList.get(16), "'", ""));
        list.add(workOrder);

        // 前n页到达满页  或者 最后一条数据

        if ((curRow - 1) % pageSize == 0) {
            workOrderService.insertListOnePage(list);
            list.clear();
        }

    }

}
