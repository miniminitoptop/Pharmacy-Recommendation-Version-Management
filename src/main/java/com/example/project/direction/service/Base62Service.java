package com.example.project.direction.service;

import io.seruco.encoding.base62.Base62;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class Base62Service {

    private static final Base62 base62Instance = Base62.createInstance();

    public String encodeDirectionId(Long directionId){
        return new String(base62Instance.encode(String.valueOf(directionId).getBytes()));
        // 8비트의 바이트형태의 데이터들을 인코딩 - 72 101 108 108 111 10 -> SGVsbG8s
    }

    public Long decodeDirectionId(String encodeDirectionId){
        String resultDirectionId = new String(base62Instance.decode(encodeDirectionId.getBytes()));
        return Long.valueOf(resultDirectionId);
    }
}
