package `fun`.fantasea.multitenantdemo

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class MultiTenantDemoApplication

fun main(args: Array<String>) {
    runApplication<MultiTenantDemoApplication>(*args)
}
