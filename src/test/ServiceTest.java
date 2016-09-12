import com.gy.wm.dbpipeline.impl.MysqlPipeline;
import com.gy.wm.model.CrawlData;
import org.junit.Test;

/**
 * <类详细说明>
 *
 * @Author： Hunahai
 * @Version: 2016-09-12
 **/
public class ServiceTest {
    private MysqlPipeline mysqlPipeline = new MysqlPipeline();
    @Test
    public void testInsertToSql()   {
        CrawlData crawlData = new CrawlData();
        crawlData.setTid("asdfjksjfiaf");
        crawlData.setCount(1234);
        mysqlPipeline.insertTosql(crawlData);
    }

}
