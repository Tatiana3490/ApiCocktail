package com.example.apicocktail.service;

import com.example.apicocktail.domain.Ingredient;
import com.example.apicocktail.domain.Location;
import com.example.apicocktail.exception.IngredientNotFoundException;
import com.example.apicocktail.exception.LocationNotFoundException;
import com.example.apicocktail.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    //Para obtener todas las localizaciones
    public List<Location> getdAllLocations() {
        return locationRepository.findAll();
    }

    //Para obtener la ciudad
    public List<Location> getLocationByCity(String city) {
        return locationRepository.findByCity(city);
    }

    //Para obtener la localizacion por estado
    public List<Location> getLocationByState(String state) {
        return locationRepository.findByState(state);
    }

    //Para obtener la localización por pais
    public List<Location> getLocationByCountry(String country) {
        return locationRepository.findByCountry(country);
    }

    //Para obtener la localización por estado y pais
    public List<Location> getLocationByStateAndCountry(String state, String country) {
        return locationRepository.findByStateAndCountry(state, country);
    }

    //Para buscar la localización por estado, pais y ciudad
    public List<Location> getLocationByStateAndCountryAndCity(String state, String country, String city) {
        return locationRepository.findByStateAndCountryAndCity(state, country, city);
    }

    //para guardar una nueva localizacion
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    //Para actualizar una localizacion  por id
    public Location updateLocation(Long id, Location locationDetails) throws LocationNotFoundException {
        Location existingLocation = locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException("Location not found by id: " + id));

        //Actualizar los campos de las localizaciones existentes con los nuevos valores
        existingLocation.setName(locationDetails.getName());
        existingLocation.setCity(locationDetails.getCity());
        existingLocation.setState(locationDetails.getState());
        existingLocation.setCountry(locationDetails.getCountry());


        return locationRepository.save(existingLocation);
    }

    public Location updateLocationPartial(Long id, Map<String, Object> updates) {
        Location location = locationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Location not found by id: " + id));

        updates.forEach((key, value) -> {
            Field field = ReflectionUtils.findField(Location.class, key);
            if (field != null) {
                field.setAccessible(true);
                ReflectionUtils.setField(field, location, value);
            }
        });

        return locationRepository.save(location);
    }

    //Para eliminar una localizacion  por id
    public void deleteLocation(Long id) throws LocationNotFoundException{
        if (!locationRepository.existsById(id)) {
            throw new LocationNotFoundException("Location not found by id: " + id);
        }
        locationRepository.deleteById(id);
    }

    //Para obtener una localizacion  por Id
    public Location getLocationById(Long id){
        return locationRepository.findById(id)
                .orElseThrow(() -> new LocationNotFoundException("Location not found by id: " + id));
    }


}
