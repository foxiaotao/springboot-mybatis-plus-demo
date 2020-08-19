package com.sdgtt.util.poi;

import java.util.List;

/**
 * @author tao
 * @version  2020-08-19 12:13
 */
public interface Reader {
    /**
     * 数据放回接口
     *
     * @param filePath 文件路径
     * @param sheetName sheet名称
     * @param sheetIndex sheet序号
     * @param curRow 行号
     * @param cellList 一行的所有单元格字段
     */
    void read(String filePath, String sheetName, int sheetIndex, int curRow, List<String> cellList);

}
