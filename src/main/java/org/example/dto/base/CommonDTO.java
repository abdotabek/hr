package org.example.dto.base;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PROTECTED)
public class CommonDTO {
    Long id;
    String name;

    /*@Override
    public String toString() {
        return name;
    }*/
    @Override
    public String toString() {
        return "CommonDTO {name='" + name + "'}";
    }
}
