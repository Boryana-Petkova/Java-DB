package softuni.exam.service.impl;

import com.google.gson.Gson;

import org.modelmapper.ModelMapper;

import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportCountryDTO;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


@Service
public class CountryServiceImpl implements CountryService {

    private static final String COUNTRIES_FILE_PATH = "src/main/resources/files/json/countries.json";

    private final CountryRepository countryRepository;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;


    public CountryServiceImpl(CountryRepository countryRepository, Gson gson, ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.validationUtils = validationUtils;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files
                .readString(Path.of(COUNTRIES_FILE_PATH));
    }

    @Override
    public String importCountries() throws IOException {

        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson
                        .fromJson(readCountriesFromFile(), ImportCountryDTO[].class))
                .filter(importCountryDTO -> {
                    boolean isValid = validationUtils.isValid(importCountryDTO);

                    Optional<Country> countryByCountryName = countryRepository.findCountryByCountryName(importCountryDTO.getCountryName());
                    if (countryByCountryName.isPresent()){
                        isValid = false;
                    }

                    sb.append(isValid
                                    ? String.format("Successfully imported country %s - %s", importCountryDTO.getCountryName()
                                    , importCountryDTO.getCurrency())
                                    : "Invalid country")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(importCountryDTO -> modelMapper.map(importCountryDTO, Country.class))
                .forEach(countryRepository::save);

        return sb.toString();
    }
    @Override
    public Country findCountryByName(String countryName) {
        return countryRepository.findCountryByCountryName(countryName).orElse(null);
    }
    @Override
    public Optional<Country> getCountryById(long countryId) {
        return countryRepository.getById(countryId);
    }

}
