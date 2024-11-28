package org.jpa.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Table
@Entity
//        (name="Users")
@AllArgsConstructor
public class Employee {
    @Id
    private int  eid;
    private String name;
    private String address;
    private int age;

}
