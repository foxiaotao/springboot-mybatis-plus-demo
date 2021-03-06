package workorder.step1;


import com.sdgtt.model.auto.WorkOrder;
import com.sdgtt.model.workorder.WorkOrderStatus;
import com.sdgtt.model.workorder.WorlOrderPriority;
import com.sdgtt.util.DateUtil;
import com.sdgtt.util.poi.Reader;
import com.sdgtt.util.poi.XlsxReader;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * 百万数据全部封装到list
 * 然后对list进行分页导入到db
 * @author tao
 * @version  2020-08-19 12:13
 **/
@Slf4j
public class ReaderTest2 implements Reader {


//    private final static String filePath = "/Users/simon/Downloads/1.xlsx";
    private final static String filePath = "/Users/simon/Downloads/work_order_created.xlsx";

    private WorkOrder workOrder = null;

    @Getter
    private List<WorkOrder> list = new ArrayList<>();


    public void read() throws Exception {
        long start = System.currentTimeMillis();
        XlsxReader excelXlsxReader = new XlsxReader(this);
        int totalRows = excelXlsxReader.process(filePath);

        log.info("加载总行数：{},耗时:{}", totalRows, System.currentTimeMillis() - start);
//        System.out.println(JsonUtils.serialize(list.get(0)));
//        System.out.println(JsonUtils.serialize(list.get(list.size()-1)));
    }

    @Override
    public void read(String filePath, String sheetName, int sheetIndex, int curRow, List<String> cellList) {
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
        final WorlOrderPriority worlOrderPriority = WorlOrderPriority.getWorlOrderPriority(cellList.get(9));
        workOrder.setPriority(worlOrderPriority != null ? worlOrderPriority.ordinal() : null);
        workOrder.setCreateUsername(cellList.get(10));
        workOrder.setCreatedAt(DateUtil.dateStr2Date(cellList.get(11), DateUtil.DATE_FORMAT_1));
        workOrder.setOpUsername(cellList.get(12));
        workOrder.setDuration(Integer.getInteger(cellList.get(13)));
        final WorkOrderStatus workOrderStatus = WorkOrderStatus.getWorkOrderStatus(cellList.get(14));
        workOrder.setStatus(workOrderStatus != null ? workOrderStatus.ordinal() : null);
        workOrder.setOpAt(DateUtil.dateStr2Date(cellList.get(15), DateUtil.DATE_FORMAT_1));
        workOrder.setRemark(cellList.get(16));
        list.add(workOrder);
    }
}

