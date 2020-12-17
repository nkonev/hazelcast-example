package name.nkonev.hazelcastexample

import com.fasterxml.jackson.databind.ObjectMapper
import com.hazelcast.nio.serialization.ByteArraySerializer

class JsonSerializer(private val mapper: ObjectMapper) : ByteArraySerializer<Any> {
    override fun getTypeId() = 1

    override fun destroy() {
    }

    override fun write(`object`: Any?): ByteArray {
        return mapper.writeValueAsBytes(Wrapper(`object`))
    }

    override fun read(buffer: ByteArray?): Any? {
        return mapper.readValue(buffer, Wrapper::class.java)?.value
    }

    class Wrapper(val value: Any?)
}
