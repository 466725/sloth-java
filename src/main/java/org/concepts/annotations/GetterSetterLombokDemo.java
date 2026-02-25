package org.concepts.annotations;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Simple domain model to demonstrate common Lombok annotations.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetterSetterLombokDemo {
    private String name;
    private int age;

    public static void main(String[] args) {
        GetterSetterLombokDemo person = GetterSetterLombokDemo.builder()
                .name("Alex Chen")
                .age(30)
                .build();

        // Generated getter and setter methods.
        System.out.println("Before update: " + person);
        person.setAge(31);
        System.out.println("After age update: " + person);
        person.setName("Alan Zheng");
        System.out.println("After name update: " + person);
    }
}
