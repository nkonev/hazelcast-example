package name.nkonev.hazelcastexample

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component
import java.time.Duration

@ConfigurationProperties(prefix = "hazelcast")
class HazelcastParams {
    lateinit var hosts: List<String>
    lateinit var login: String
    lateinit var password: String
    var connectionTimeout: Duration = Duration.ofSeconds(5)
    var connectionAttemptLimit: Int = 500
    var connectionAttemptPeriod: Duration = Duration.ofSeconds(3)
    var smartRouting: Boolean = true
}
