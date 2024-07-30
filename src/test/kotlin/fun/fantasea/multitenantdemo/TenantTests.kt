package `fun`.fantasea.multitenantdemo

import `fun`.fantasea.multitenantdemo.tenant.TenantContextHolder
import `fun`.fantasea.multitenantdemo.tenant.withTenantContext
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import kotlin.test.assertEquals

@SpringBootTest
class TenantTests {

    @Test
    fun tenantSwitch() {
        withTenantContext("testA") {
            assertEquals(
                "testA",
                TenantContextHolder.getContext(),
            )
        }

        withTenantContext("testB") {
            assertEquals(
                "testB",
                TenantContextHolder.getContext(),
            )
        }
    }

    @Test
    fun tenantInit() {
        assertEquals(
            null,
            TenantContextHolder.getContext(),
        )
    }

    @Test
    fun tenantClear() {
        TenantContextHolder.setContext("testC")
        assertEquals(
            "testC",
            TenantContextHolder.getContext(),
        )
        TenantContextHolder.clearContext()
        assertEquals(
            null,
            TenantContextHolder.getContext(),
        )
    }
}
