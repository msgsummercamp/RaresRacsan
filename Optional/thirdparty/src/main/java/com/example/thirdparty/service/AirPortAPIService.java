package com.example.thirdparty.service;

import com.example.thirdparty.model.Airport;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.ArrayList;
import java.util.List;

@Service
public class AirPortAPIService {
    @Value("${thirdparty.airport.api.url}")
    private String apiUrl;

    @Autowired
    private RestTemplate restTemplate;

    private final ObjectMapper objectMapper = new ObjectMapper();

    public String fetchApiResponse() {
        try {
            return restTemplate.getForObject(apiUrl, String.class);
        } catch (Exception e) {
            throw new RuntimeException("Failed to fetch data from AirPort API", e);
        }
    }

    public List<Airport> parseAirportsFromResponse(String jsonResponse) {
        List<Airport> airports = new ArrayList<>();

        try {
            JsonNode rootNode = objectMapper.readTree(jsonResponse);
            JsonNode dataArray = rootNode.get("data");

            if (dataArray != null && dataArray.isArray()) {
                for (JsonNode airportNode : dataArray) {
                    JsonNode attributes = airportNode.get("attributes");
                    String id = airportNode.get("id").asText();

                    Airport airport = new Airport(
                            id,
                            attributes.get("name").asText(),
                            attributes.get("city").asText(),
                            attributes.get("country").asText(),
                            attributes.get("iata").asText(),
                            attributes.get("icao").asText(),
                            attributes.get("latitude").asText(),
                            attributes.get("longitude").asText(),
                            attributes.get("altitude").asInt(),
                            attributes.get("timezone").asText()
                    );

                    airports.add(airport);
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse airport data", e);
        }

        return airports;
    }

    public List<Airport> fetchAndParseAirports() {
        String response = fetchApiResponse();
        return parseAirportsFromResponse(response);
    }
}