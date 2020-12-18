package name.nkonev.hazelcastexample

import com.hazelcast.core.IMap
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RestController

const val KEY = "key1"

@RestController
class MapController(@Qualifier(WEB_MAP) private val webMap: IMap<String, String>) {

    @GetMapping("/get")
    fun get() : String? {
        return webMap[KEY]
    }

    @PutMapping("/put")
    fun put() {
        webMap[KEY] = "avalue1"
    }
}