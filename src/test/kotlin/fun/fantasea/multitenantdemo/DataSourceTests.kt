package `fun`.fantasea.multitenantdemo

import `fun`.fantasea.multitenantdemo.entity.Person
import `fun`.fantasea.multitenantdemo.entity.PersonRepository
import `fun`.fantasea.multitenantdemo.tenant.withTenantContext
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.core.io.ClassPathResource
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import javax.sql.DataSource
import kotlin.test.assertEquals

@SpringBootTest
class DataSourceTests {
    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var personRepository: PersonRepository


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

    @Test
    fun dataManipulate() {
        // 初始化 H2 数据库
        val populator = ResourceDatabasePopulator().apply {
            addScripts(ClassPathResource("init.sql"))
        }

        (dataSource as AbstractRoutingDataSource).resolvedDataSources.values
            .forEach { populator.execute(it) }

        withTenantContext("tenant1") {
            populator.execute(dataSource)
            personRepository.save(Person("A"))
        }

        withTenantContext("tenant2") {
            populator.execute(dataSource)
            personRepository.save(Person("B"))
            personRepository.save(Person("C"))
        }

        withTenantContext("tenant1") {
            assertEquals(
                1,
                personRepository.count()
            )
        }

        withTenantContext("tenant2") {
            assertEquals(
                2,
                personRepository.count()
            )
        }
    }
}
