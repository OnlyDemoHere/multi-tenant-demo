package `fun`.fantasea.multitenantdemo.tenant

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import javax.sql.DataSource

/**
 * 实现 AbstractRoutingDataSource，将数据源选择与租户上下文信息绑定。
 */
class TenantRoutingDataSource : AbstractRoutingDataSource() {
    override fun determineCurrentLookupKey(): Any? {
        return TenantContextHolder.getContext()
    }

    /**
     * 刷新租户信息，应用数据源改动。
     */
    private fun refresh() {
        this.afterPropertiesSet()
    }

    fun addDataSource(tenantId: String, dataSource: DataSource) {
        if (resolvedDataSources.containsKey(tenantId)) throw Exception("tenant $tenantId already exists")
        setTargetDataSources((resolvedDataSources + mapOf(tenantId to dataSource)) as Map<Any, Any>)
        refresh()
    }

    fun removeDataSource(tenantId: String) {
        if (!resolvedDataSources.containsKey(tenantId)) throw Exception("tenant $tenantId does not exist")
        setTargetDataSources((resolvedDataSources - tenantId) as Map<Any, Any>)
        refresh()
    }
}
