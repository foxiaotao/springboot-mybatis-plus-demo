package com.sdgtt.util.poi;


import com.sdgtt.model.workorder.WorkOrderVO;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tao
 * @version  2020-08-19 12:13
 **/
@Slf4j
public class ReaderTest implements Reader {


    private final static String filePath = "/Users/simon/Downloads/1.xlsx";
//    private final static String filePath = "/Users/simon/Downloads/work_order_created.xlsx";

    private WorkOrderVO workOrderVO = null;

    @Getter
    private List<WorkOrderVO> list = new ArrayList<>();


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
        workOrderVO = new WorkOrderVO();
        workOrderVO.setWorkOrderId(cellList.get(0));
        workOrderVO.setDurationTimes(cellList.get(1));
        workOrderVO.setUserName(cellList.get(2));
        workOrderVO.setPhone(cellList.get(3));
        workOrderVO.setSubject(cellList.get(4));
        workOrderVO.setFrom(cellList.get(5));
        workOrderVO.setType1(cellList.get(6));
        workOrderVO.setType2(cellList.get(7));
        workOrderVO.setType3(cellList.get(8));
        workOrderVO.setPriority(cellList.get(9));
        workOrderVO.setCreateUsername(cellList.get(10));
        workOrderVO.setCreatedAt(cellList.get(11));
        workOrderVO.setOpUsername(cellList.get(12));
        workOrderVO.setDuration(cellList.get(13));
        workOrderVO.setStatus(cellList.get(14));
        workOrderVO.setOpAt(cellList.get(15));
        workOrderVO.setRemark(cellList.get(16));
        list.add(workOrderVO);
    }
}

