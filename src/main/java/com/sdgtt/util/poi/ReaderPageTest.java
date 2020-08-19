package com.sdgtt.util.poi;


import com.sdgtt.model.auto.WorkOrder;
import com.sdgtt.model.workorder.WorkOrderStatus;
import com.sdgtt.model.workorder.WorlOrderPriority;
import com.sdgtt.util.DateUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 百万数据分页封装到list
 * 导入到db
 * @author tao
 * @version  2020-08-19 12:13
 **/
@Slf4j
public class ReaderPageTest implements Reader {


//    private final static String filePath = "/Users/simon/Downloads/1.xlsx";
    private final static String filePath = "/Users/simon/Downloads/work_order_created.xlsx";

    private WorkOrder workOrder = null;
    private WorlOrderPriority worlOrderPriority;
    private WorkOrderStatus workOrderStatus;

    private int pageSize = 10000;
    private int currentPage = 0;

    @Getter
    private List<WorkOrder> list = new ArrayList<>(pageSize);


    public void read() throws Exception {
        long start = System.currentTimeMillis();
        XlsxReader excelXlsxReader = new XlsxReader(this);
        int totalRows = excelXlsxReader.process(filePath);
        log.info("加载总行数：{},耗时:{}", totalRows, System.currentTimeMillis() - start);
    }

    @Override
    public void read(String filePath, String sheetName, int sheetIndex, int curRow, List<String> cellList) {
        if (currentPage++ < pageSize) {
            workOrder = new WorkOrder();
            workOrder.setWorkOrderId(Long.valueOf(cellList.get(0)));
            workOrder.setDurationTimes(Integer.valueOf(cellList.get(1)));
            workOrder.setUserName(cellList.get(2));
            workOrder.setPhone(cellList.get(3));
            workOrder.setSubject(cellList.get(4));
            workOrder.setFromChannel(cellList.get(5));
            workOrder.setType1(cellList.get(6));
            workOrder.setType2(cellList.get(7));
            workOrder.setType3(cellList.get(8));
            worlOrderPriority = WorlOrderPriority.getWorlOrderPriority(cellList.get(9));
            workOrder.setPriority(worlOrderPriority != null ? worlOrderPriority.ordinal() : null);
            workOrder.setCreateUsername(cellList.get(10));
            workOrder.setCreatedAt(DateUtil.dateStr2Date(cellList.get(11), DateUtil.DATE_FORMAT_1));
            workOrder.setOpUsername(cellList.get(12));
            workOrder.setDuration(Integer.getInteger(cellList.get(13)));
            workOrderStatus = WorkOrderStatus.getWorkOrderStatus(cellList.get(14));
            workOrder.setStatus(workOrderStatus != null ? workOrderStatus.ordinal() : null);
            workOrder.setOpAt(DateUtil.dateStr2Date(cellList.get(15), DateUtil.DATE_FORMAT_1));
            workOrder.setRemark(cellList.get(16));
            list.add(workOrder);
        } else {
            log.info("页码：" + currentPage);
            currentPage = 0;

        }
    }
}

