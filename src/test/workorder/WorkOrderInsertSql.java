package workorder;

import com.sdgtt.model.workorder.WorkOrderSimple;
import com.sdgtt.model.workorder.WorkOrderStatus;
import com.sdgtt.model.workorder.WorlOrderPriority;
import com.sdgtt.util.poi.Reader;
import com.sdgtt.util.poi.XlsxReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Lists;
import org.junit.Test;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 分页写到sql本地文件
 *
 * 分页读取excel 保存到数据库
 * 每读取一页的数据，转换成sql写到文件
 * @author tao
 * @version 2020-08-14 16:28
 */
@Slf4j
public class WorkOrderInsertSql implements Reader {

    private static final String filePath = "/Users/simon/Downloads/创建.xlsx";
//    private static final String filePath = "/Users/simon/Downloads/完成.xlsx";
//    private static final String filePath = "/Users/simon/Downloads/work_order_created.xlsx";

    private int pageSize = 500;
    private List<WorkOrderSimple> list = new ArrayList<>(pageSize);


    @Test
    public void read() throws Exception {

        long start = System.currentTimeMillis();
        XlsxReader excelXlsxReader = new XlsxReader(this);
        int totalRows = excelXlsxReader.process(filePath);

        if (!CollectionUtils.isEmpty(list)) {
            // 最后一页数据还没保存
            writeIntoFile(list);
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
        WorkOrderSimple workOrder = new WorkOrderSimple();
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
        workOrder.setCreatedAt(cellList.get(11));
        workOrder.setOpUsername(cellList.get(12));
        workOrder.setDuration(Integer.valueOf(cellList.get(13)));
        final WorkOrderStatus workOrderStatus = WorkOrderStatus.getWorkOrderStatus(cellList.get(14));
        workOrder.setStatus(workOrderStatus != null ? workOrderStatus.ordinal() : 0);
        workOrder.setOpAt(cellList.get(15));
        workOrder.setRemark(StringUtils.replaceAll(cellList.get(16), "'", ""));
        list.add(workOrder);

        // 前n页到达满页  或者 最后一条数据

        if ((curRow - 1) % pageSize == 0) {
            try {
                writeIntoFile(list);
            } catch (IOException e) {
                e.printStackTrace();
            }
            list.clear();
        }

    }

    private void writeIntoFile(List<WorkOrderSimple> list) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO work_order (work_order_id,user_name,phone,subject,from_channel,type1,type2,type3,priority,create_username,op_username,status,duration_times,duration,created_at,op_at,updated_at,remark) VALUES ");
        for (int i = 0; i < list.size(); i++) {
            if (i < list.size() - 1) {
                stringBuilder.append(getOneLine(list.get(i)))
                        .append(",");
            } else {
                stringBuilder.append(getOneLine(list.get(i)))
                        .append(";");
            }
        }
        File file = new File("/Users/simon/Documents/workspace/wp_202007/mybatis-demo2/src/main/resources/insert.sql");
        if (!file.exists()) {
            file.createNewFile();
        }
        FileUtils.writeLines(file, Lists.newArrayList(stringBuilder.toString()), true);
    }

    private String getOneLine(WorkOrderSimple workOrder) {
        final String format = "(%s,'%s','%s','%s','%s','%s','%s','%s',%s,'%s','%s',%s,%s,%s,'%s','%s','%s','%s')";
        return String.format(format, workOrder.getWorkOrderId(), workOrder.getUserName(), workOrder.getPhone(), workOrder.getSubject(), workOrder.getFromChannel(),
                workOrder.getType1(), workOrder.getType2(), workOrder.getType3(), workOrder.getPriority() != null ? workOrder.getPriority() : 0,
                workOrder.getCreateUsername(), workOrder.getOpUsername(),
                workOrder.getStatus() != null ? workOrder.getStatus() : "", workOrder.getDurationTimes() != null ?workOrder.getDurationTimes():"", workOrder.getDuration() != null ?workOrder.getDuration():"",
                workOrder.getCreatedAt(), workOrder.getOpAt(), workOrder.getOpAt(), workOrder.getRemark());
    }

}
