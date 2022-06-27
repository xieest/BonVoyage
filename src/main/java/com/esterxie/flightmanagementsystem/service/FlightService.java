package com.esterxie.flightmanagementsystem.service;

import com.esterxie.flightmanagementsystem.model.Flight;
import com.esterxie.flightmanagementsystem.repository.FlightRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FlightService {

    @Autowired
    FlightRepository flightRepository;

    public List<Flight> getAllFlights() {
        return flightRepository.getAllByFlightsOrderByDepartureDate();
    }

    public void deleteFlight(String flightNumber) {
        Optional<Flight> flight = flightRepository.findById(flightNumber);
        if (flight.isPresent()) {
            flightRepository.delete(flight.get());
        } else {
            throw new RuntimeException("There is no flight against this ID: " + flightNumber);
        }
    }

    public List<Flight> getFlightsByCity(String keyword) {
        return flightRepository.findAllByArrivalAirportContainingIgnoreCaseOrDepartureAirportContainingIgnoreCase(keyword, keyword);
    }
}
