package xiaoman;

import com.sdgtt.DemoApplication;
import com.sdgtt.model.auto.WorkOrder;
import com.sdgtt.model.workorder.WorkOrderStatus;
import com.sdgtt.model.workorder.WorlOrderPriority;
import com.sdgtt.service.IWorkOrderService;
import com.sdgtt.util.DateUtil;
import com.sdgtt.util.poi.Reader;
import com.sdgtt.util.poi.XlsxReader;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页读取excel 保存到数据库
 * 每读取一页的数据，insert一次
 * @author tao
 * @version 2020-08-14 16:28
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class, webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
@ContextConfiguration()
public class WorkOrderInsertByPage implements Reader {

    @Autowired
    private IWorkOrderService workOrderService;

//    private static final String filePath = "/Users/simon/Downloads/1.xlsx";
    private static final String filePath = "/Users/simon/Downloads/work_order_created.xlsx";

    private WorkOrder workOrder = null;
    private WorlOrderPriority worlOrderPriority;
    private WorkOrderStatus workOrderStatus;

    private int pageSize = 5000;

    private List<WorkOrder> list = new ArrayList<>(pageSize);


    @Test
    public void read() throws Exception {

        long start = System.currentTimeMillis();
        XlsxReader excelXlsxReader = new XlsxReader(this);
        int totalRows = excelXlsxReader.process(filePath);

        if (!CollectionUtils.isEmpty(list)) {
            // 最后一页数据还没保存
            workOrderService.insertListOnePage(list);
        }
        // 总页数
        int pages = (int)Math.ceil(totalRows / pageSize);
        log.info("加载总行数:{},总页数:{},耗时:{}", totalRows, pages, System.currentTimeMillis() - start);


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

        // 前n页到达满页  或者 最后一条数据

        if ((curRow - 1) % pageSize == 0) {
//            log.info("list.size={}，(curRow-1)={}", list.size(), curRow - 1);
            workOrderService.insertListOnePage(list);
            list.clear();
        }

    }

}
