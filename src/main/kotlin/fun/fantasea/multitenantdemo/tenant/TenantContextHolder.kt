package `fun`.fantasea.multitenantdemo.tenant


class TenantContextHolder {
    companion object {
        private val context = ThreadLocal.withInitial<String?> { null }

        fun getContext(): String? = context.get()
        fun setContext(tenantId: String) = context.set(tenantId)
        fun clearContext() = context.remove()
    }
}

inline fun withTenantContext(tenantId: String, action: () -> Unit) {
    val previousContext = TenantContextHolder.getContext()
    TenantContextHolder.setContext(tenantId)
    try {
        action()
    } finally {
        if (previousContext == null) {
            TenantContextHolder.clearContext()
        } else {
            TenantContextHolder.setContext(previousContext)
        }
    }
}
