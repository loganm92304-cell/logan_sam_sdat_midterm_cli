package com.keyin;

import java.util.Scanner;

import com.keyin.service.AirportCLIService;

public class App {
    public static void main(String[] args) {
        String baseUrl = "http://localhost:8080"; // Default API address
        if (args.length > 0 && args[0] != null) {
            String providedUrl = args[0].trim();
            if (!providedUrl.isEmpty()) {
                baseUrl = providedUrl;
            }
        }

        try {
            HttpClientService httpClient = new HttpClientService(baseUrl);
            AirportCLIService cliService = new AirportCLIService(httpClient);
            System.out.println("==================================================");
            System.out.println("        AIRPORT & FLIGHT MANAGEMENT CLI           ");
            System.out.println("==================================================");

            try (Scanner scanner = new Scanner(System.in)) {
                boolean running = true;
                while (running) {
                    System.out.println("\nSelect an option:");
                    System.out.println("1. What airports are in each city?");
                    System.out.println("2. What aircraft has each passenger flown on?");
                    System.out.println("3. What airports does each aircraft operate out of?");
                    System.out.println("4. What airports have passengers used?");
                    System.out.println("5. Exit");
                    System.out.print("> ");

                    if (!scanner.hasNextLine()) {
                        System.out.println("\nNo input received. Exiting CLI application.");
                        break;
                    }

                    String input = scanner.nextLine().trim();
                    try {
                        switch (input) {
                            case "1" -> {
                                System.out.println("\n--- Airports in Each City ---");
                                cliService.getAirportsInEachCity().forEach(System.out::println);
                            }
                            case "2" -> {
                                System.out.println("\n--- Aircraft Flown by Each Passenger ---");
                                cliService.getAircraftPerPassenger().forEach(System.out::println);
                            }
                            case "3" -> {
                                System.out.println("\n--- Airports per Aircraft ---");
                                cliService.getAirportsPerAircraft().forEach(System.out::println);
                            }
                            case "4" -> {
                                System.out.println("\n--- Airports Used by Passengers ---");
                                cliService.getAirportsUsedByPassengers().forEach(System.out::println);
                            }
                            case "5" -> {
                                running = false;
                                System.out.println("Exiting CLI application. Goodbye!");
                            }
                            default -> System.out.println("Invalid option. Please choose between 1 and 5.");
                        }
                    } catch (Exception e) {
                        System.err.println("Error processing request: " + e.getMessage());
                    }
                }
            }
        } catch (Exception e) {
            System.err.println("Unable to initialize CLI: " + e.getMessage());
        }
    }
}