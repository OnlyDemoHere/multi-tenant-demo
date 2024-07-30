package `fun`.fantasea.multitenantdemo.tenant

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource

class TenantRoutingDataSource : AbstractRoutingDataSource() {
    override fun determineCurrentLookupKey(): Any? {
        return TenantContextHolder.getContext()
    }
}
