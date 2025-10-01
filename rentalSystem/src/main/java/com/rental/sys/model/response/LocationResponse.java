package com.rental.sys.model.response;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocationResponse {
    private int id;
    private String name;
    private String pincode;
    private int cityId;
    private String cityName;
}