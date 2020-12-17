package name.nkonev.hazelcastexample

import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator
import com.hazelcast.client.config.ClientConfig
import com.hazelcast.config.SerializerConfig
import com.hazelcast.core.HazelcastInstance
import com.hazelcast.internal.serialization.impl.JavaDefaultSerializers
import org.springframework.boot.autoconfigure.hazelcast.HazelcastClientFactory
import org.springframework.cache.interceptor.SimpleKey
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder

@Configuration
class HazelcastConfig {

    @Bean
    fun clientConfig(params: HazelcastParams) = ClientConfig().apply {
        val typedMapper: ObjectMapper = ObjectMapper()
                .apply {
                    Jackson2ObjectMapperBuilder.json().configure(this)
                    enable(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES)
                    enable(JsonGenerator.Feature.WRITE_BIGDECIMAL_AS_PLAIN)
                    enable(DeserializationFeature.READ_UNKNOWN_ENUM_VALUES_USING_DEFAULT_VALUE)
                    disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                    disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
                }
                .activateDefaultTyping(
                        LaissezFaireSubTypeValidator.instance,
                        ObjectMapper.DefaultTyping.EVERYTHING,
                        JsonTypeInfo.As.WRAPPER_ARRAY
                )


        networkConfig.addresses = params.hosts
        networkConfig.isSmartRouting = params.smartRouting
        networkConfig.connectionTimeout = params.connectionTimeout.toMillis().toInt()
        networkConfig.connectionAttemptLimit = params.connectionAttemptLimit
        networkConfig.connectionAttemptPeriod = params.connectionAttemptPeriod.toMillis().toInt()

        groupConfig.name = params.login
        groupConfig.password = params.password

        val mapper = typedMapper.copy()
            .activateDefaultTyping(
                LaissezFaireSubTypeValidator.instance,
                ObjectMapper.DefaultTyping.NON_CONCRETE_AND_ARRAYS,
                JsonTypeInfo.As.WRAPPER_ARRAY
            )

        serializationConfig.serializerConfigs.add(
            SerializerConfig()
                .setTypeClass(Any::class.java)
                .setImplementation(JsonSerializer(mapper))
        )
        serializationConfig.serializerConfigs.add(
            SerializerConfig()
                .setTypeClass(SimpleKey::class.java)
                .setImplementation(
                    JavaDefaultSerializers.JavaSerializer(
                        serializationConfig.isEnableSharedObject,
                        serializationConfig.isEnableCompression
                    ) { }
                )
        )
    }

    @Bean(destroyMethod = "shutdown")
    fun hazelcastInstance(config: ClientConfig, params: HazelcastParams): HazelcastInstance {
        val instance = HazelcastClientFactory(config).hazelcastInstance

        // getMap() call should be after you add the config

        return instance
    }
}
