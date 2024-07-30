package `fun`.fantasea.multitenantdemo.tenant


/**
 * 利用 ThreadLocal 实现多租户上下文信息存储。
 */
class TenantContextHolder {
    companion object {
        private val context = ThreadLocal.withInitial<String?> { null }

        fun getContext(): String? = context.get()
        fun setContext(tenantId: String) = context.set(tenantId)
        fun clearContext() = context.remove()
    }
}

/**
 * 用于简化上下文切换操作。
 */
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
