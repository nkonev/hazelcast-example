package name.nkonev.hazelcastexample

import com.hazelcast.core.HazelcastInstance
import com.hazelcast.core.HazelcastJsonValue
import com.hazelcast.map.IMap
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

public const val WEB_MAP = "web"

@Configuration
class HazelcastMapsConfig(val instance: HazelcastInstance) {

    @Bean(name = [WEB_MAP])
    fun webMap(): IMap<String, HazelcastJsonValue> {
        return instance.getMap(WEB_MAP)
    }
}