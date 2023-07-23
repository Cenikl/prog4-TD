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
public class Cin {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true,nullable = false)
    private String cinNumber;

    @Column(nullable = false)
    private String issueDate;

    @Column(nullable = false)
    private String issueLocation;

    @OneToOne
    @JoinColumn(name = "cin_employee",referencedColumnName = "id",nullable = false)
    private Employee cinEmployee;

}
