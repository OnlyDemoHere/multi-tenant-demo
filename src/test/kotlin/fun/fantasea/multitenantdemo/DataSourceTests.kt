package `fun`.fantasea.multitenantdemo

import `fun`.fantasea.multitenantdemo.tenant.withTenantContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import javax.sql.DataSource
import kotlin.test.assertEquals

@SpringBootTest
class DataSourceTests {
    @Autowired
    private lateinit var dataSource: DataSource

    @Test
    fun defaultDataSource() {
        dataSource.connection.use {
            assertEquals("jdbc:h2:mem:testdb1", it.metaData.url)
        }
    }

    @Test
    fun dataSourceSwitch() {
        withTenantContext("tenant1") {
            dataSource.connection.use {
                assertEquals("jdbc:h2:mem:testdb1", it.metaData.url)
            }
        }

        withTenantContext("tenant2") {
            dataSource.connection.use {
                assertEquals("jdbc:h2:mem:testdb2", it.metaData.url)
            }
        }
    }
}
