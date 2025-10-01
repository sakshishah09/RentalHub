package com.rental.sys.controller;

import com.rental.sys.model.response.LocationResponse;
import com.rental.sys.model.request.LocationRequestModel;
import com.rental.sys.response.RestResponse;
import com.rental.sys.service.LocationService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/location")
public class LocationController {

    @Autowired
    private LocationService locationService;

    @Operation(summary = "Get all locations", description = "Fetches all locations for dropdowns")
    @GetMapping("/all")
    public RestResponse getAllLocations() {
        try {
            List<LocationResponse> locations = locationService.getAllLocations();
            return RestResponse.build().withSuccess("Locations fetched successfully", locations);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.build().withError("Failed to fetch locations");
        }
    }

    @Operation(summary = "Create new location", description = "Adds a new location")
    @PostMapping("/create")
    public RestResponse createLocation(@RequestBody LocationRequestModel requestModel) {
        try {
            LocationResponse response = locationService.createLocation(requestModel);
            return RestResponse.build().withSuccess("Location created successfully", response);
        } catch (Exception e) {
            e.printStackTrace();
            return RestResponse.build().withError(e.getMessage());
        }
    }
}