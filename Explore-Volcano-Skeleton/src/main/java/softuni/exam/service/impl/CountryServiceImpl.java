package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountrySeedDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class CountryServiceImpl implements CountryService {

    public static final String COUNTRY_PATH = "src/main/resources/files/json/countries.json";
    private final CountryRepository countryRepository;

    private final Gson gson;
    private  final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public CountryServiceImpl(CountryRepository countryRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.countryRepository = countryRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFromFile() throws IOException {
        return Files.readString(Path.of(COUNTRY_PATH));
    }

    @Override
    public String importCountries() throws IOException {


        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readCountriesFromFile(), CountrySeedDto[].class)).filter(countrySeedDto -> {

            boolean isValid = validationUtil.isValid(countrySeedDto);

            Optional<Country> countryOpt = countryRepository.findCountryByName(countrySeedDto.getName());

            if (countryOpt.isPresent()){
                isValid = false;
            }
            sb.append(isValid ? String.format("Successfully imported country %s - %s", countrySeedDto.getName(), countrySeedDto.getCapital())
            : "Invalid country").append(System.lineSeparator());

            return  isValid;

        }).map(countrySeedDto -> modelMapper.map(countrySeedDto, Country.class)).forEach(countryRepository::save);



        return sb.toString().trim();
    }

    @Override
    public void saveAddedVolcanoInCountry(Country country) {
        countryRepository.save(country);
    }

    @Override
    public Optional<Country> getCountryById(long countryId) {
        return countryRepository.findById(countryId);
    }
}
