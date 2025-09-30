package com.rental.sys.convertor.entities;

import com.rental.sys.entities.City;
import com.rental.sys.entities.Location;
import com.rental.sys.model.request.LocationRequestModel;

public class LocationModelToEntity {

    public static Location convert(LocationRequestModel requestModel, City city) {
        Location location = new Location();
        location.setName(requestModel.getName());
        location.setPincode(requestModel.getPincode());
        location.setCity(city);
        return location;
    }
}