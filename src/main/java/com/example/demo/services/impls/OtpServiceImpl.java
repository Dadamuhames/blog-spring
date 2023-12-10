package com.example.demo.services.impls;

import com.example.demo.services.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class OtpServiceImpl implements OtpService {
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    int TTL = 5;

    @Override
    public String generateOtp() {
        return "666666";
    }

    @Override
    public void storeOtp(String phoneNumber) {
        redisTemplate.opsForValue().set(phoneNumber, generateOtp(), Duration.ofSeconds(TTL));
    }

    @Override
    public String getOtp(String phoneNumber) {
        Object otp =  redisTemplate.opsForValue().get(phoneNumber);

        return otp == null ? null : otp.toString();
    }

    @Override
    public boolean isValid(String phoneNumber) {
        return false;
    }

    @Service
    public static class EnumerateMapper {
        public List<Map<String, Object>> enumerate(List<String> list) {
            AtomicInteger index = new AtomicInteger();

            return list.stream().map((image) -> {
                int ind = index.getAndIncrement();
                Map<String, Object> obj = new HashMap<>();

                obj.put("image", image);
                obj.put("index", ind + 1);

                return obj;
            }).toList();
        }
    }
}
