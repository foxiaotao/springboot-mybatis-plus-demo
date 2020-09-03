package workorder.step1;

import com.sdgtt.DemoApplication;
import com.sdgtt.util.JsonUtils;
import com.sdgtt.model.auto.WorkOrder;
import com.sdgtt.service.IWorkOrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import workorder.step1.ReaderTest2;

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
public class WoSaveDataByMybatisCoreTest {

    @Autowired
    private IWorkOrderService workOrderService;

    /**
     * 将所有excel数据解析到list中
     * 框架saveBatch 为循环单条插入，不能满足需要
     * 导入数据
     * @throws Exception
     */
    @Test
    public void workOrderTest2() throws Exception {
        log.info("开始");
        ReaderTest2 readerTest = new ReaderTest2();
        readerTest.read();
        log.info("开始 saveBatch");
        workOrderService.saveBatch(readerTest.getList(), 10000);
        log.info("完成 saveBatch");
    }

    /**
     * 批量导入
     * 导入数据
     * @throws Exception
     */
    @Test
    public void workOrderTest3() throws Exception {
        log.info("开始");
        ReaderTest2 readerTest = new ReaderTest2();
        readerTest.read();
        log.info("开始 saveBatch");
        workOrderService.insertAllListInnerPage(readerTest.getList());
        log.info("完成 saveBatch");
    }


    @Test
    public void queryPage() {
        List<WorkOrder> allUser = workOrderService.findAllUser();
        System.out.println(JsonUtils.serialize(allUser));
    }

}
