package com.rental.sys.convertor.model;

import com.rental.sys.entities.Location;
import com.rental.sys.model.response.LocationResponse;

public class LocationEntityToModel {

    public static LocationResponse convert(Location location) {
        LocationResponse response = new LocationResponse();
        response.setId(location.getId());
        response.setName(location.getName());
        response.setPincode(location.getPincode());
        response.setCityId(location.getCity().getId());
        response.setCityName(location.getCity().getName());
        return response;
    }
}