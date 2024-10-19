package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;

import softuni.exam.models.dto.ImportCityDTO;

import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CityRepository;

import softuni.exam.service.CityService;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;


@Service
public class CityServiceImpl implements CityService {


    private static final String CITIES_FILE_PATH = "src/main/resources/files/json/cities.json";

    private final CityRepository cityRepository;

    private final CountryService countryService;

    private final ModelMapper modelMapper;
    private final Gson gson;
    private final ValidationUtils validationUtils;


    public CityServiceImpl(CityRepository cityRepository, CountryService countryService, ModelMapper modelMapper, Gson gson, ValidationUtils validationUtils) {
        this.cityRepository = cityRepository;
        this.countryService = countryService;
        this.modelMapper = modelMapper;
        this.gson = gson;
        this.validationUtils = validationUtils;
    }


    @Override
    public boolean areImported() {
        return this.cityRepository.count() > 0;
    }

    @Override
    public String readCitiesFileContent() throws IOException {
        return Files
                .readString(Path.of(CITIES_FILE_PATH));
    }

    @Override
    public String importCities() throws IOException {

        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson
                        .fromJson(readCitiesFileContent(), ImportCityDTO[].class))
                .filter(importCityDTO -> {
                    boolean isValid = validationUtils.isValid(importCityDTO);

                    Optional<City> byCityName = cityRepository.findByCityName(importCityDTO.getCityName());
                    if (byCityName.isPresent()) {
                        isValid = false;
                    }

                    sb.append(isValid
                                    ? String.format("Successfully imported city %s - %d", importCityDTO.getCityName()
                                    , importCityDTO.getPopulation())
                                    : "Invalid city")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(importCityDTO -> {
                    City city = modelMapper.map(importCityDTO, City.class);

                    Optional<Country> countryById = countryService.getCountryById(importCityDTO.getCountry());
                    if (countryById.isEmpty()) {
                        System.out.println("ERROR:  " + importCityDTO.getCityName());
                        return city;
                    }

                    city.setCountry(countryById.get());
                    return city;
                })
                .forEach(cityRepository::save);

        return sb.toString();
    }
    @Override
    public City findCityById(long cityId) {
        return cityRepository.findById(cityId).orElse(null);
    }
}
