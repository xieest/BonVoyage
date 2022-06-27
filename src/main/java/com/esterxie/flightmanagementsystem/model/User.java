package com.esterxie.flightmanagementsystem.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.Email;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
	@Transient
	@Email
	private String repeatedEmail;
	private String password;
	@Transient
	private String repeatedPassword;
	private String role;

	@Temporal(TemporalType.TIMESTAMP)
	@CreationTimestamp
	private Date signupDate;

	@ManyToMany(targetEntity = Flight.class, cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
	List<Flight> flights = new ArrayList<>();
}
