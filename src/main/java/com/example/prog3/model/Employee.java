package com.example.prog3.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;

    public String firstName;

    public String lastName;

    public String birthDate;

    @Column(unique = true,nullable = false)
    public String matricule;

    @Lob
    public byte[] emplImg;
}
