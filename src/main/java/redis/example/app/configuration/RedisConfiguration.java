package redis.example.app.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableCaching
public class RedisConfiguration {

    @Value("${redis.host}")
    private String redisHost;

    @Value("${redis.port}")
    private int redisPort;
/*
    @Value("${redis.password}")
    private String redisPassword;
*/
    @Bean
    public JedisConnectionFactory createJedisConnectionFactory() {
        RedisStandaloneConfiguration standaloneConfig = new RedisStandaloneConfiguration(redisHost, redisPort);
        //standaloneConfig.setPassword(redisPassword);

        return new JedisConnectionFactory(standaloneConfig);
    }

    @Bean
    public RedisTemplate<String, Object> createRedisTemplate() {
        RedisTemplate<String, Object> rdTemplate = new RedisTemplate<>();

        rdTemplate.setConnectionFactory(createJedisConnectionFactory());
        rdTemplate.setKeySerializer(new StringRedisSerializer());
        rdTemplate.setHashKeySerializer(new StringRedisSerializer());
        rdTemplate.setValueSerializer(new JdkSerializationRedisSerializer());
        rdTemplate.afterPropertiesSet();
        rdTemplate.setEnableTransactionSupport(true);

        return rdTemplate;
    }
}
