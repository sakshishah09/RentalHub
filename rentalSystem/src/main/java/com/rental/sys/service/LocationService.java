package com.rental.sys.service;

import com.rental.sys.convertor.entities.LocationModelToEntity;
import com.rental.sys.convertor.model.LocationEntityToModel;
import com.rental.sys.entities.City;
import com.rental.sys.entities.Location;
import com.rental.sys.model.request.LocationRequestModel;
import com.rental.sys.model.response.LocationResponse;
import com.rental.sys.repo.CityRepo;
import com.rental.sys.repo.LocationRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LocationService {

    @Autowired
    private LocationRepo locationRepo;

    @Autowired
    private CityRepo cityRepo;

    // Fetch all locations
    public List<LocationResponse> getAllLocations() {
        List<Location> locations = locationRepo.findAll();
        return locations.stream()
                .map(LocationEntityToModel::convert)
                .collect(Collectors.toList());
    }

    // Create new location
    public LocationResponse createLocation(LocationRequestModel requestModel) throws Exception {
        City city = cityRepo.findById(requestModel.getCityId())
                .orElseThrow(() -> new Exception("City not found with id: " + requestModel.getCityId()));

        Location location = LocationModelToEntity.convert(requestModel, city);
        Location savedLocation = locationRepo.save(location);
        return LocationEntityToModel.convert(savedLocation);
    }
}