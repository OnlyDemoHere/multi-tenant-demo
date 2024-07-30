package `fun`.fantasea.multitenantdemo.config

import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.stereotype.Component

@Component
class SpringContextHolder : ApplicationContextAware {
    companion object {
        lateinit var applicationContext: ApplicationContext
            private set
    }

    override fun setApplicationContext(applicationContext: ApplicationContext) {
        SpringContextHolder.applicationContext = applicationContext
    }
}
