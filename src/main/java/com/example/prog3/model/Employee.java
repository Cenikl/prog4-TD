package com.example.prog3.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String firstName;

    private String lastName;

    @Enumerated(EnumType.STRING)
    private Sex sex;

    @Enumerated(EnumType.STRING)
    private Csp csp;

    private String birthDate;

    @Column(unique = true,nullable = false)
    private String matricule;

    @Column(nullable = false)
    private String address;

    @Column(nullable = false,unique = true)
    private String emailPro;

    @Column(nullable = false,unique = true)
    private String emailPerso;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private Integer child;

    @Column(nullable = false)
    private String employementDate;

    @Column(nullable = false)
    private String departureDate;

    @Column(nullable = false)
    private String cnaps;

    @Lob
    private byte[] emplImg;

    @OneToMany(mappedBy = "phoneEmployee")
    private List<Phone> phones;

    @OneToOne(mappedBy = "cinEmployee")
    private Cin cin;

    public enum Sex{
        M,F
    }

    public enum Csp{
        M1,M2,OS1,OS2,OS3,OP1
    }
}
