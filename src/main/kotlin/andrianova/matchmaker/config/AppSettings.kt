package andrianova.matchmaker.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@PropertySource("classpath:application.properties")
class AppSettings {

    @Value("\${groupSize}")
    var groupSize = 3

    @Value("\${maxAllowedDistance}")
    var maxAllowedDistance = 2.0

    @Value("\${maxTimeInQueue}")
    var maxTimeInQueue = 180L
}
