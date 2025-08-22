package org.example.enums;

import lombok.Getter;

@Getter
public enum LogType {
    INFO(1, "Информационный лог"),
    ERROR(2, "Ошибка"),
    UNKNOWN(3, "Херня");

    private final Integer id;
    private final String description;

    LogType(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public static LogType findById(Integer id) {
        if (id == null) {
            return UNKNOWN;
        }
        for (LogType type : values()) {
            if (type.id.equals(id)) {
                return type;
            }
        }
        return UNKNOWN;
    }
}
