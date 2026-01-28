package org.concepts.annotations;

import lombok.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Data
public class GetterSetter {
    private String name;
    private int age;
}
