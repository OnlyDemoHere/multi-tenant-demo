package `fun`.fantasea.multitenantdemo.tenant

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource

/**
 * 实现 AbstractRoutingDataSource，将数据源选择与租户上下文信息绑定。
 */
class TenantRoutingDataSource : AbstractRoutingDataSource() {
    override fun determineCurrentLookupKey(): Any? {
        return TenantContextHolder.getContext()
    }
}
