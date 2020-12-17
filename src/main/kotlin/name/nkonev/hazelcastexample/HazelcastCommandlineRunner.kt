package name.nkonev.hazelcastexample

import com.hazelcast.config.MapConfig
import com.hazelcast.core.HazelcastInstance
import org.springframework.boot.CommandLineRunner
import org.springframework.stereotype.Component

@Component
class HazelcastCommandlineRunner (val instance: HazelcastInstance) : CommandLineRunner {
    override fun run(vararg args: String?) {
        val mapName = "myMap2"
        instance.config.addMapConfig(
                MapConfig()
                        .setName(mapName)
                        .setTimeToLiveSeconds(3600)
                        .setMaxIdleSeconds(3600)
        )

        val map = instance.getMap<String, String>(mapName)
        //map.put("key1", "a val")
        println(">>>>>>>>>>>>>>>>>>>>"+map["key1"])
    }

}