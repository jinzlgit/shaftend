package com.jyd.core.domain;

import lombok.Builder;
import lombok.Data;

/**
 * @author Zhenlin Jin
 * @date 2021/8/12 21:31
 */
@Data
@Builder
public class BaseDTO {
    private String code;
    private String number;
    private String content;

    @Override
    public String toString() {
        return "BaseDTO{" +
                "code='" + code + '\'' +
                ", number='" + number + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}
