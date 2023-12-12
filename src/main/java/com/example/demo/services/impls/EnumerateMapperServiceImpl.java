package com.example.demo.services.impls;

import com.example.demo.services.EnumerateMapperService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;


@Service
@RequiredArgsConstructor
public class EnumerateMapperServiceImpl implements EnumerateMapperService {
    @Override
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
