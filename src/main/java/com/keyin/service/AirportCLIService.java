package com.keyin.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.keyin.client.HTTPClientService;
import com.keyin.model.*;

import java.util.*;

public class AirportCLIService {
    private final HTTPClientService client;
    private final ObjectMapper objectMapper;

    public AirportCLIService(HTTPClientService client) {
        this.client = client;
        this.objectMapper = new ObjectMapper();
    }

    // Question 1: What airports are in each city?
    public List<String> getAirportsInEachCity() throws Exception {
        String json = client.get("/cities");
        List<City> cities = objectMapper.readValue(json, new TypeReference<List<City>>() {});
        List<String> results = new ArrayList<>();

        for (City city : cities) {
            StringBuilder sb = new StringBuilder();
            sb.append("City: ").append(city.getName()).append(" -> Airports: ");
            if (city.getAirports() != null && !city.getAirports().isEmpty()) {
                for (Airport airport : city.getAirports()) {
                    sb.append(airport.getName()).append(" (").append(airport.getCode()).append("), ");
                }
            } else {
                sb.append("None");
            }
            results.add(sb.toString());
        }
        return results;
    }

    // Question 2: What aircraft has each passenger flown on?
    public List<String> getAircraftPerPassenger() throws Exception {
        String json = client.get("/passengers");
        List<Passenger> passengers = objectMapper.readValue(json, new TypeReference<List<Passenger>>() {});
        List<String> results = new ArrayList<>();

        for (Passenger passenger : passengers) {
            StringBuilder sb = new StringBuilder();
            sb.append("Passenger: ").append(passenger.getFirstName()).append(" ").append(passenger.getLastName())
                    .append(" -> Aircraft Flown: ");
            if (passenger.getAircraft() != null && !passenger.getAircraft().isEmpty()) {
                for (Aircraft craft : passenger.getAircraft()) {
                    sb.append(craft.getType()).append(" (").append(craft.getAirlineName()).append("), ");
                }
            } else {
                sb.append("None");
            }
            results.add(sb.toString());
        }
        return results;
    }

    // Question 3: What airports does each aircraft take off from and land at?
    public List<String> getAirportsPerAircraft() throws Exception {
        String json = client.get("/aircraft");
        List<Aircraft> aircraftList = objectMapper.readValue(json, new TypeReference<List<Aircraft>>() {});
        List<String> results = new ArrayList<>();

        for (Aircraft craft : aircraftList) {
            StringBuilder sb = new StringBuilder();
            sb.append("Aircraft: ").append(craft.getType()).append(" [").append(craft.getAirlineName()).append("]")
                    .append(" -> Associated Airports: ");
            if (craft.getAirports() != null && !craft.getAirports().isEmpty()) {
                for (Airport airport : craft.getAirports()) {
                    sb.append(airport.getName()).append(" (").append(airport.getCode()).append("), ");
                }
            } else {
                sb.append("None");
            }
            results.add(sb.toString());
        }
        return results;
    }

    // Question 4: What airports have passengers used?
    public List<String> getAirportsUsedByPassengers() throws Exception {
        String json = client.get("/passengers");
        List<Passenger> passengers = objectMapper.readValue(json, new TypeReference<List<Passenger>>() {});
        Set<String> usedAirports = new HashSet<>();

        for (Passenger passenger : passengers) {
            if (passenger.getAircraft() != null) {
                for (Aircraft craft : passenger.getAircraft()) {
                    if (craft.getAirports() != null) {
                        for (Airport airport : craft.getAirports()) {
                            usedAirports.add(airport.getName() + " (" + airport.getCode() + ")");
                        }
                    }
                }
            }
        }
        return new ArrayList<>(usedAirports);
    }
}