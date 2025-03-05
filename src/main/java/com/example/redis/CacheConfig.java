package com.example.redis;

import java.time.Duration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
@EnableCaching
public class CacheConfig {

    @Bean
    public RedisCacheManager cacheManager(RedisConnectionFactory connectionFactory) {
        // Redis를 이용해서 Spring Cache를 사용할 때 Redis 관련 설정을 하는 클래스
        RedisCacheConfiguration config = RedisCacheConfiguration
            .defaultCacheConfig()
            // null을 캐싱하는지
            .disableCachingNullValues()
            // 기본 캐시 유지 시간(Time To Live)
            .entryTtl(Duration.ofSeconds(120))
            // 캐시를 구분하는 접두사 설정
            .computePrefixWith(CacheKeyPrefix.simple())
            // 캐시에 저장할 값을 어떻게 직렬화 / 역질려화 할 것인지
            .serializeValuesWith(
                RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.java())
            );

        return RedisCacheManager
            .builder(connectionFactory)
            .cacheDefaults(config)
            .build();
    }
}
