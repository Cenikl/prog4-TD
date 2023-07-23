package com.example.prog3.model;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class Enterprise {
    private String name;
    private String description;
    private String slogan;
    private String address;
    private String email;
    private List<String> phoneNumbers;
    private FiscalIdentity identity;
    private String logoUrl;
}
