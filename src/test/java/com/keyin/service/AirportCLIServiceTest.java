package com.keyin.service;

import com.keyin.client.HTTPClientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AirportCLIServiceTest {

    @Mock
    private HTTPClientService mockHttpClient;

    private AirportCLIService cliService;

    @BeforeEach
    void setUp() {
        cliService = new AirportCLIService(mockHttpClient);
    }

    @Test
    void testGetAirportsInEachCity() throws Exception {
        String mockJsonResponse = "[{"
                + "\"id\":1,\"name\":\"St. John's\",\"state\":\"NL\",\"population\":110000,"
                + "\"airports\":[{\"id\":10,\"name\":\"St. John's International\",\"code\":\"YYT\"}]"
                + "}]";

        when(mockHttpClient.get("/cities")).thenReturn(mockJsonResponse);

        List<String> result = cliService.getAirportsInEachCity();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).contains("St. John's"));
        assertTrue(result.get(0).contains("YYT"));
        verify(mockHttpClient, times(1)).get("/cities");
    }

    @Test
    void testGetAircraftPerPassenger() throws Exception {
        String mockJsonResponse = "[{"
                + "\"id\":1,\"firstName\":\"Logan\",\"lastName\":\"Marsh\",\"phoneNumber\":\"709-555-0199\","
                + "\"aircraft\":[{\"id\":100,\"type\":\"Boeing 737\",\"airlineName\":\"Air Canada\",\"numberOfPassengers\":180}]"
                + "}]";

        when(mockHttpClient.get("/passengers")).thenReturn(mockJsonResponse);

        List<String> result = cliService.getAircraftPerPassenger();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).contains("Logan Marsh"));
        assertTrue(result.get(0).contains("Boeing 737"));
        verify(mockHttpClient, times(1)).get("/passengers");
    }

    @Test
    void testGetAirportsPerAircraft() throws Exception {
        String mockJsonResponse = "[{"
                + "\"id\":100,\"type\":\"Airbus A320\",\"airlineName\":\"WestJet\",\"numberOfPassengers\":150,"
                + "\"airports\":[{\"id\":10,\"name\":\"Halifax Stanfield\",\"code\":\"YHZ\"}]"
                + "}]";

        when(mockHttpClient.get("/aircraft")).thenReturn(mockJsonResponse);

        List<String> result = cliService.getAirportsPerAircraft();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).contains("Airbus A320"));
        assertTrue(result.get(0).contains("YHZ"));
        verify(mockHttpClient, times(1)).get("/aircraft");
    }

    @Test
    void testGetAirportsUsedByPassengers() throws Exception {
        String mockJsonResponse = "[{"
                + "\"id\":1,\"firstName\":\"Jane\",\"lastName\":\"Doe\",\"phoneNumber\":\"709-555-0100\","
                + "\"aircraft\":[{"
                + "  \"id\":100,\"type\":\"Boeing 737\",\"airlineName\":\"Air Canada\",\"numberOfPassengers\":180,"
                + "  \"airports\":[{\"id\":10,\"name\":\"Toronto Pearson\",\"code\":\"YYZ\"}]"
                + "}]"
                + "}]";

        when(mockHttpClient.get("/passengers")).thenReturn(mockJsonResponse);

        List<String> result = cliService.getAirportsUsedByPassengers();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(result.get(0).contains("Toronto Pearson (YYZ)"));
        verify(mockHttpClient, times(1)).get("/passengers");
    }
}