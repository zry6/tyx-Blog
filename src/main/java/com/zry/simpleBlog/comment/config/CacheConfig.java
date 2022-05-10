package com.zry.simpleBlog.comment.config;

import org.springframework.cache.CacheManager;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.time.Duration;

/**
 * @author zry
 * @create 2022-05-10 18:19
 */
@Configuration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate) {

        // 配置序列化（解决乱码的问题）,过期时间120秒
        RedisCacheConfiguration config = RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofDays(1))
                .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getKeySerializer()))
                .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(redisTemplate.getValueSerializer()));

        return RedisCacheManager.builder(redisTemplate.getRequiredConnectionFactory())
                .cacheDefaults(config)
                .build();
    }

    /**
     * key值为className+methodName+参数值列表
     *
     * @return
     */
    @Bean
    public KeyGenerator keyGenerator() {
        return (o, method, args) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(o.getClass().getName()).append("#");
            sb.append(method.getName()).append("(");
            for (Object obj : args) {
                if (obj != null) { // 在可选参数未给出时时，会出现null，此时需要跳过
                    sb.append(obj).append(",");
                }
            }
            sb.append(")");
            return sb.toString();
        };
    }
}
