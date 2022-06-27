package com.app.flightmanagementsystem.model;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;
import java.time.ZonedDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "flights")
public class Flight {
    @Id
    private String flight_number;
    private LocalDate flight_date;
    private String flight_status;
    private String airline_name;
    @Column(name = "arrival_airport")
    private String arrivalAirport;
    private String arrival_timezone;
    private ZonedDateTime arrival_scheduled;
    private ZonedDateTime arrival_estimated;
    @Column(name = "departure_airport")
    private String departureAirport;
    private String departure_timezone;
    private ZonedDateTime departure_scheduled;
    private ZonedDateTime departure_estimated;
}
