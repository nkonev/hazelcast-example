package name.nkonev.hazelcastexample

import com.hazelcast.core.HazelcastInstance
import com.hazelcast.core.HazelcastJsonValue
import com.hazelcast.map.IMap
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController


const val KEY = "key1"

@RestController
class MapController(@Qualifier(WEB_MAP) private val webMap: IMap<String, HazelcastJsonValue>, val instance: HazelcastInstance) {

    @GetMapping("/get")
    fun get() : String? {
        return webMap[KEY]?.toString()
    }

    @PutMapping("/put")
    fun put() {
        val person1 = "{ \"name\": \"John\", \"age\": 35 }"
        webMap[KEY] = HazelcastJsonValue(person1)
    }

    @GetMapping("/search")
    fun search() : List<String> {
        val res = mutableListOf<String>()
        instance.sql.execute("SELECT name FROM web WHERE age > ?", 30).use {
            for (row in it) {
                val name = row.getObject<String>(0)
                println(name)
                res.add(name)
            }
        }
        return res
    }

}