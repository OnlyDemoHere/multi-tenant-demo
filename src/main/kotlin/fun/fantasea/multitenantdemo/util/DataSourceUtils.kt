package `fun`.fantasea.multitenantdemo.util

import org.springframework.jdbc.datasource.DriverManagerDataSource

object DataSourceUtils {
    /**
     * 快速构造一个 H2 数据源对象。
     */
    fun produceDataSource(databaseName : String) = DriverManagerDataSource().apply {
        setDriverClassName("org.h2.Driver")
        username = "sa"
        password = "password"
        url = "jdbc:h2:mem:$databaseName;DB_CLOSE_DELAY=-1"
    }
}
