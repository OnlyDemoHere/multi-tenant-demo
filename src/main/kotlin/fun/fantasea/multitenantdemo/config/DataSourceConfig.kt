package `fun`.fantasea.multitenantdemo.config

import `fun`.fantasea.multitenantdemo.tenant.TenantRoutingDataSource
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.DriverManagerDataSource
import javax.sql.DataSource

@Configuration
class DataSourceConfig {
    @Bean
    fun dataSource(): DataSource {
        val h2DataSource1 = DriverManagerDataSource().apply {
            setDriverClassName("org.h2.Driver")
            username = "sa"
            password = "password"
            url = "jdbc:h2:mem:testdb1"
        }

        val h2DataSource2 = DriverManagerDataSource().apply {
            setDriverClassName("org.h2.Driver")
            username = "sa"
            password = "password"
            url = "jdbc:h2:mem:testdb2"
        }

        return TenantRoutingDataSource().apply {
            val dataSources = mapOf(
                "tenant1" to h2DataSource1,
                "tenant2" to h2DataSource2
            ).toMutableMap<Any, Any>()
            setTargetDataSources(dataSources)
            setDefaultTargetDataSource(h2DataSource1)
        }
    }
}
