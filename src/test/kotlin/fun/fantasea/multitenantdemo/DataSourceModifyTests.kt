package `fun`.fantasea.multitenantdemo

import `fun`.fantasea.multitenantdemo.config.SpringContextHolder
import `fun`.fantasea.multitenantdemo.tenant.TenantRoutingDataSource
import `fun`.fantasea.multitenantdemo.tenant.withTenantContext
import `fun`.fantasea.multitenantdemo.util.DataSourceUtils
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.getBean
import org.springframework.boot.test.context.SpringBootTest
import javax.sql.DataSource
import kotlin.test.assertEquals

@SpringBootTest
class DataSourceModifyTests {
    @Autowired
    private lateinit var dataSource: DataSource

    @Test
    fun addRemoveDataSource() {
        val tenantRoutingDataSource = SpringContextHolder.applicationContext.getBean<TenantRoutingDataSource>()
        withTenantContext("tenant3") {
            dataSource.connection.use {
                assertEquals(
                    "jdbc:h2:mem:testdb1",
                    it.metaData.url
                )
            }
        }

        tenantRoutingDataSource.addDataSource("tenant3", DataSourceUtils.produceDataSource("testdb3"))

        withTenantContext("tenant3") {
            dataSource.connection.use {
                assertEquals(
                    "jdbc:h2:mem:testdb3",
                    it.metaData.url
                )
            }
        }

        tenantRoutingDataSource.removeDataSource("tenant3")

        // 移除数据源后，tenant3 不存在，自动切换回到默认数据源
        withTenantContext("tenant3") {
            dataSource.connection.use {
                assertEquals(
                    "jdbc:h2:mem:testdb1",
                    it.metaData.url
                )
            }
        }
    }
}
