package com.keyin;

import com.keyin.client.HTTPClientService;
import com.keyin.service.AirportCLIService;

import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        String baseUrl = "http://localhost:8080"; // Default API address
        if (args.length > 0) {
            baseUrl = args[0];
        }

        HTTPClientService httpClient = new HTTPClientService(baseUrl);
        AirportCLIService cliService = new AirportCLIService(httpClient);
        Scanner scanner = new Scanner(System.in);

        System.out.println("==================================================");
        System.out.println("        AIRPORT & FLIGHT MANAGEMENT CLI           ");
        System.out.println("==================================================");

        boolean running = true;
        while (running) {
            System.out.println("\nSelect an option:");
            System.out.println("1. What airports are in each city?");
            System.out.println("2. What aircraft has each passenger flown on?");
            System.out.println("3. What airports does each aircraft operate out of?");
            System.out.println("4. What airports have passengers used?");
            System.out.println("5. Exit");
            System.out.print("> ");

            String input = scanner.nextLine();
            try {
                switch (input) {
                    case "1":
                        System.out.println("\n--- Airports in Each City ---");
                        cliService.getAirportsInEachCity().forEach(System::println);
                        break;
                    case "2":
                        System.out.println("\n--- Aircraft Flown by Each Passenger ---");
                        cliService.getAircraftPerPassenger().forEach(System::println);
                        break;
                    case "3":
                        System.out.println("\n--- Airports per Aircraft ---");
                        cliService.getAirportsPerAircraft().forEach(System::println);
                        break;
                    case "4":
                        System.out.println("\n--- Airports Used by Passengers ---");
                        cliService.getAirportsUsedByPassengers().forEach(System::println);
                        break;
                    case "5":
                        running = false;
                        System.out.println("Exiting CLI application. Goodbye!");
                        break;
                    default:
                        System.out.println("Invalid option. Please choose between 1 and 5.");
                }
            } catch (Exception e) {
                System.err.println("Error processing request: " + e.getMessage());
            }
        }
        scanner.close();
    }
}