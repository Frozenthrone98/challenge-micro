package com.mamani.mscustomeralternative.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name" ,nullable = false, length = 50)
    private String fullName;

    @Column(name = "gender" ,nullable = false, length = 20)
    private String gender;

    @Column(name = "age" ,nullable = false)
    private int age;

    @Column(name = "identification_number" ,nullable = false, length = 12)
    private String identificationNumber;

    @Column(name = "address" ,nullable = false, length = 100)
    private String address;

    @Column(name = "phone_number" ,nullable = false, length = 9)
    private String phoneNumber;

    @Column(name = "created_at",nullable = false)
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "person")
    private Customer customer;
}
