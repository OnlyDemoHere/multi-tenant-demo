package `fun`.fantasea.multitenantdemo.config

import `fun`.fantasea.multitenantdemo.tenant.TenantRoutingDataSource
import `fun`.fantasea.multitenantdemo.util.DataSourceUtils
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DataSourceConfig {
    @Bean
    fun dataSource(): DataSource {
        // 定义数据源
        val h2DataSource1 = DataSourceUtils.produceDataSource("testdb1")
        val h2DataSource2 = DataSourceUtils.produceDataSource("testdb2")

        // 配置标识符与数据源的映射关系
        val tenantRoutingDataSource = TenantRoutingDataSource().apply {
            val dataSources = mapOf(
                "tenant1" to h2DataSource1,
                "tenant2" to h2DataSource2
            ).toMutableMap<Any, Any>()
            setTargetDataSources(dataSources)
            setDefaultTargetDataSource(h2DataSource1)
        }

        return tenantRoutingDataSource
    }
}
