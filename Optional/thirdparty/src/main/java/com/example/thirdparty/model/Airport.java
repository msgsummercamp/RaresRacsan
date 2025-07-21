package com.example.thirdparty.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Airport {
    private String id;
    private String name;
    private String city;
    private String country;
    private String iata;
    private String icao;
    private String latitude;
    private String longitude;
    private int altitude;
    private String timezone;

    @Override
    public String toString() {
        return String.format("%s (%s) - %s, %s", name, iata, city, country);
    }
}
