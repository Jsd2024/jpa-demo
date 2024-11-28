package org.jpa.demo.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
@AllArgsConstructor

@Entity
@Table(name="Users")
@Data
@Builder
public class User {
    @Id
    private int id;
    private String name;
    private String address;
    private int age;

}
