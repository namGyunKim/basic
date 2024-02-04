package org.example.basic.global.utils.aws.constant;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;

import java.util.stream.Stream;

@Getter
public enum FileType {

    IMAGE("image"), VIDEO("video");

    private final String value;
    FileType(String value) {
        this.value = value;
    }

    @JsonCreator
    public static FileType create(String requestValue) {
        return Stream.of(values())
                .filter(v -> v.name().equalsIgnoreCase(requestValue) || v.getValue().equalsIgnoreCase(requestValue))
                .findFirst()
                .orElse(null);
    }

    public static FileType createByCode(String requestValue) {
        return Stream.of(values())
                .filter(v -> v.getValue().equalsIgnoreCase(requestValue))
                .findFirst()
                .orElse(null);
    }
}

