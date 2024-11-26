package org.jpa.demo.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Employee {
    private String name;
    private String add;
    private int age;

}
