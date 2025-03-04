package com.example.redis;

import java.util.concurrent.TimeUnit;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

@SpringBootTest
public class RestTemplateTests {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Test
    public void stringOpsTesT() {
        // 지금 RedisTemplate에 설정된 타입을 바탕으로 Redis 문자열 조작을 할거다.
        // 문자열 조작을 위한 클래스
        ValueOperations<String, String> ops = stringRedisTemplate.opsForValue();
        ops.set("simpleKey", "simpleValue");
        System.out.println(ops.get("simpleKey"));

        // 집합을 조작하기 위한 클래스
        SetOperations<String, String> setOps = stringRedisTemplate.opsForSet();
        setOps.add("hobbies", "codding", "alcohol", "games");
        System.out.println(setOps.size("hobbies"));

        stringRedisTemplate.expire("hobbies", 10, TimeUnit.SECONDS);
        stringRedisTemplate.delete("simpleKey");
    }

    @Autowired
    private RedisTemplate<String, ItemDto> itemRedisTemplate;

    @Test
    public void itemRedisTemplateTest() {
        ValueOperations<String, ItemDto> ops = itemRedisTemplate.opsForValue();
        ops.set("my:keyboard", ItemDto.builder()
                .name("Mechanical Keyboard")
                .price(250000)
                .description("OMG")
                .build());
        System.out.println(ops.get("my:keyboard"));

        ops.set("my:mouse", ItemDto.builder()
            .name("mouse mice")
            .price(100000)
            .description("OMG")
            .build());
        System.out.println(ops.get("my:mouse"));
    }
}
