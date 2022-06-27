package com.esterxie.flightmanagementsystem.service;

import com.esterxie.flightmanagementsystem.model.Flight;
import com.esterxie.flightmanagementsystem.repository.FlightRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Service
@Slf4j
public class RestCallService {

    @Autowired
    FlightRepository flightRepository;

    public void callingFlightApi() throws JsonProcessingException {
        String url = "http://api.aviationstack.com/v1/flights?access_key=49264b2cbe332c5dfb680bf915718c37";

        // create an instance of RestTemplate
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<String> response
                = restTemplate.getForEntity(url, String.class);

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode name = root.path("data");

        System.out.println(name.size());

        for (int i = 0; i < name.size(); i++) {
            Flight flight = Flight.builder()
                    .flight_date(LocalDate.parse(name.get(i).get("flight_date").asText()))
                    .flight_status(name.get(i).get("flight_status").asText())
                    .flight_number(name.get(i).get("flight").get("number").asText())
                    .airline_name(name.get(i).get("airline").get("name").asText())
                    .arrivalAirport(name.get(i).get("arrival").get("airport").asText())
                    .arrival_timezone(name.get(i).get("arrival").get("timezone").asText())
                    .departure_timezone(name.get(i).get("departure").get("timezone").asText())
                    .departureAirport(name.get(i).get("departure").get("airport").asText())
                    .build();


            ZonedDateTime arrivalScheduledParsed = ZonedDateTime.parse(name.get(i).get("arrival").get("scheduled").asText(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
            flight.setArrival_scheduled(arrivalScheduledParsed);

            ZonedDateTime arrivalEstimatedParsed = ZonedDateTime.parse(name.get(i).get("arrival").get("estimated").asText(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
            flight.setArrival_estimated(arrivalEstimatedParsed);

            ZonedDateTime departureScheduledParsed = ZonedDateTime.parse(name.get(i).get("departure").get("scheduled").asText(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
            flight.setDeparture_scheduled(departureScheduledParsed);

            ZonedDateTime departureEstimatedParsed = ZonedDateTime.parse(name.get(i).get("departure").get("estimated").asText(), DateTimeFormatter.ISO_ZONED_DATE_TIME);
            flight.setDeparture_estimated(departureEstimatedParsed);

            flightRepository.save(flight);
        }
    }
}
