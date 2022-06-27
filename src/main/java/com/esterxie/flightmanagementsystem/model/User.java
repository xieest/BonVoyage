package com.esterxie.flightmanagementsystem.model;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @Email
    @Column(unique = true)
    private String email;
    private String password;
    private String role;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date signupDate;

    @ManyToMany(targetEntity = Flight.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    List<Flight> flights = new ArrayList<>();
}
