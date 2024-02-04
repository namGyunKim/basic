package org.example.basic.global.utils.aws.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum FileDirectory {

    PRODUCT("product");

    private final String value;

    FileDirectory(String value) {
        this.value = value;
    }

    @JsonCreator
    public static FileDirectory create(String requestValue) {
        return Stream.of(values())
                .filter(v -> v.name().equalsIgnoreCase(requestValue) || v.getValue().equalsIgnoreCase(requestValue))
                .findFirst()
                .orElse(null);
    }

    public static FileDirectory createByCode(String requestValue) {
        return Stream.of(values())
                .filter(v -> v.getValue().equalsIgnoreCase(requestValue))
                .findFirst()
                .orElse(null);
    }
}

