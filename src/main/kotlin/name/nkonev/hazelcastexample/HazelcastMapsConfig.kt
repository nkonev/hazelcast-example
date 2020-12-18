package name.nkonev.hazelcastexample

import com.hazelcast.core.HazelcastInstance
import com.hazelcast.core.IMap
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

public const val WEB_MAP = "webMap"

@Configuration
class HazelcastMapsConfig(val instance: HazelcastInstance) {

    @Bean(name = [WEB_MAP])
    fun webMap(): IMap<String, String> {
        return instance.getMap<String, String>(WEB_MAP)
    }
}