package com.example.apicocktail.controller;

import com.example.apicocktail.domain.Cocktail;
import com.example.apicocktail.domain.Ingredient;
import com.example.apicocktail.domain.Location;
import com.example.apicocktail.exception.IngredientNotFoundException;
import com.example.apicocktail.exception.LocationNotFoundException;
import com.example.apicocktail.service.CocktailService;
import com.example.apicocktail.service.LocationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/location")
public class LocationController {

    private final Logger logger = LoggerFactory.getLogger(LocationController.class);
    private final LocationService locationService;

    @Autowired
    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    //Para obtener todas las localizaciones
    @GetMapping
    public ResponseEntity<List<Location>> getAllLocation(){
        logger.info("BEGIN getAllLocations");
        List<Location> locations = locationService.getdAllLocations();
        logger.info("END getAllLocations - Total locations fetched: {}", locations.size());
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    //Agregar una localizacion nueva
    @PostMapping
    public ResponseEntity<Location> addLocation(@RequestBody Location location) {
        logger.info("BEGIN addLocation - Adding new location: {}", location.getName());
        Location newLocation = locationService.saveLocation(location);
        logger.info("END addLocation - Location added: {}", newLocation.getName());
        return new ResponseEntity<>(newLocation, HttpStatus.CREATED);
    }

    //Buscar localizacion por ciudad
    @GetMapping("/city")
    public ResponseEntity<List<Location>> getLocationByCity(@RequestParam String city){
        logger.info("BEGIN getLocationByCity - Searching for city: {}", city);
        List<Location> locations = locationService.getLocationByCity(city);
        logger.info("END getLocationByCity - Total locations fetched: {}", locations.size());
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    //Buscar localizacion por pais
    @GetMapping("/country")
    public ResponseEntity<List<Location>> getLocationByCountry(@RequestParam String country){
        logger.info("BEGIN getLocationByCountry - Searching for country: {}", country);
        List<Location> locations = locationService.getLocationByCountry(country);
        logger.info("END getLocationByCountry - Total locations fetched: {}", locations.size());
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    //Buscar localizacion por estado
    @GetMapping("/state")
    public ResponseEntity<List<Location>> getLocationByState(@RequestParam String state){
        logger.info("BEGIN getLocationByState - Searching for stae: {}", state);
        List<Location> locations = locationService.getLocationByState(state);
        logger.info("END getLocationByState - Total locations fetched: {}", locations.size());
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

   //Buscar localizacion por estado y pais
    @GetMapping("/stateandcountry")
    public ResponseEntity<List<Location>> getLocationByStateAndCountry(@RequestParam String state, @RequestParam String country){
        logger.info("BEGIN getLocationsByStateAndCountry - Searching for state: {} and country: {}", state, country);
        List<Location> locations = locationService.getLocationByStateAndCountry(state, country);
        logger.info("END getLocationsByStateAndCountry - Total locations fetched: {}", locations.size());
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    //Buscar localizaciones por estado, pais y ciudad
    @GetMapping("/stateandcountryandcity")
    public ResponseEntity<List<Location>> getLocationByStateAndCountryAndCity(@RequestParam String state, @RequestParam String country, @RequestParam String city){
        logger.info("BEGIN getLocationByStateAndCountryAndCity - Searching for state: {} and country: {} and city: {}", state, country, city);
        List<Location> locations = locationService.getLocationByStateAndCountryAndCity(state, country, city);
        logger.info("END getLocationByStateAndCountryAndCity - Total locations fetched: {}", locations.size());
        return new ResponseEntity<>(locations, HttpStatus.OK);

    }


    //Para actualizar una localizacion por id
    @PutMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable Long id, @RequestBody Location locationDetails) throws LocationNotFoundException {
        logger.info("BEGIN updateLocation - Location update by id: {}", id);
        try{
            Location updateLocation = locationService.updateLocation(id, locationDetails);
            logger.info("END updateLocation - Location update by id: {}", updateLocation.getId());
            return  new ResponseEntity<>(updateLocation, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in updateLocation - Location not found by Id: {}", id, e);
            throw e;
        }

    }

    //Para actualizar parcialmente una localizacion
    @PatchMapping("/{id}")
    public ResponseEntity<Location> updateLocation(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        logger.info("BEGIN updateLocationPartial - Partially updating location by Id: {}", id);
        try {
            Location updatedLocation = locationService.updateLocationPartial(id, updates);
            logger.info("END updateLocationPartial - Location updated by Id: {}", updatedLocation.getId());
            return ResponseEntity.ok(updatedLocation);
        } catch (Exception e) {
            logger.error("Error in updateLocationPartial - Location not found by Id: {}", id, e);
            throw e;
        }
    }

    //Para obtener una localizacion por id
    @GetMapping("/{id}")
    public ResponseEntity<Location> getLocationById(@PathVariable Long id) throws LocationNotFoundException {
        logger.info("BEGIN getLocationById - Fetching location by Id: {}", id);
        try {
            Location location = locationService.getLocationById(id);
            logger.info("END getLocationById - Location found: {}", location.getId());
            return new ResponseEntity<>(location, HttpStatus.OK);
        } catch (Exception e) {
            logger.error("Error in getLocationById - Location not found by Id: {}", id, e);
            throw e;
        }
    }

    //Para eliminar una localizacion por Id
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLocation(@PathVariable Long id) throws LocationNotFoundException{
        logger.info("BEGIN deleteLocation - Deleting location by Id: {}", id);
        try {
            locationService.deleteLocation(id);
            logger.info("END deleteLocation - Location deleted by Id: {}", id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            logger.error("Error in deleteLocation - Location not found by Id: {}", id, e);
            throw e;
        }
    }

    // Manejar excepciones de localizacion no encontrada
    @ExceptionHandler(LocationNotFoundException.class)
    public ResponseEntity<String> handleLocationNotFoundException(LocationNotFoundException exception) {
        logger.error("Handling LocationNotFoundException - {}", exception.getMessage(), exception);
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.NOT_FOUND);
    }
}
