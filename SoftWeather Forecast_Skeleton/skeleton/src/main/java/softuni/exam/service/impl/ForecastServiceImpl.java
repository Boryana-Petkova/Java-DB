package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ImportForecastDTO;
import softuni.exam.models.dto.ImportForecastRootDTO;
import softuni.exam.models.entity.City;
import softuni.exam.models.entity.Forecast;
import softuni.exam.repository.ForecastRepository;
import softuni.exam.service.CityService;
import softuni.exam.service.ForecastService;
import softuni.exam.util.ValidationUtils;
import softuni.exam.util.XmlParser;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class ForecastServiceImpl implements ForecastService {

    private final String FORECASTS_FILE_PATH = "src/main/resources/files/xml/forecasts.xml";


    private final ForecastRepository forecastRepository;

    private final CityService cityService;
    private final XmlParser xmlParser;
    private final Gson gson;
    private final ValidationUtils validationUtils;
    private final ModelMapper modelMapper;


    public ForecastServiceImpl(ForecastRepository forecastRepository, CityService cityService, XmlParser xmlParser, Gson gson, ValidationUtils validationUtils, ModelMapper modelMapper) {
        this.forecastRepository = forecastRepository;
        this.cityService = cityService;
        this.xmlParser = xmlParser;
        this.gson = gson;
        this.validationUtils = validationUtils;

        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.forecastRepository.count() > 0;
    }

    @Override
    public String readForecastsFromFile() throws IOException {
        return Files.readString(Path.of(FORECASTS_FILE_PATH));
    }


    @Override
    public String importForecasts() throws FileNotFoundException, JAXBException {

        StringBuilder sb = new StringBuilder();

        xmlParser
                .fromFile(FORECASTS_FILE_PATH, ImportForecastRootDTO.class)
                .getForecasts()
                .stream()
                .filter(importForecastDTO -> {
                    boolean isValid = validationUtils.isValid(importForecastDTO);

                    City city = cityService.findCityById(importForecastDTO.getCity());

                    if (city == null) {
                        isValid = false;
                    }

                    Forecast forecast = forecastRepository.findAllByCityAndDayOfWeek(city, importForecastDTO.getDayOfWeek()).orElse(null);

                    if (forecast != null) {
                        isValid = false;
                    }

                    sb
                            .append(isValid
                                    ? String.format("Successfully import forecast %s - %.2f",
                                    importForecastDTO.getDayOfWeek().toString(), importForecastDTO.getMaxTemperature())
                                    : "Invalid forecast")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(importForecastDTO -> {
                    Forecast forecast = modelMapper.map(importForecastDTO, Forecast.class);

                    City city = cityService.findCityById(importForecastDTO.getCity());
//
                    forecast.setCity(city);
//                    cityService.addAndSaveAddedForecast(forecast,city);

                    //                    Town townByName = townService.findTownByName(forecastSeedDto.getTown());
//                    apartment.setTown(townByName);

                    return forecast;
                })
                .forEach(forecastRepository::save);

        return sb.toString();
    }

    @Override
    public String exportForecasts() {
        StringBuilder sb = new StringBuilder();

        Set<Forecast> allByDayOfWeek_sunday
                = forecastRepository.findAllByDayOfWeekAndCity_PopulationLessThanOrderByMaxTemperatureDescIdAsc(DayOfWeek.SUNDAY, 150000);

        allByDayOfWeek_sunday
                .forEach(f -> {
                    sb.append(String.format("City: %s\n" +
                                            "-min temperature: %.2f\n" +
                                            "--max temperature: %.2f\n" +
                                            "---sunrise: %s\n" +
                                            "-----sunset: %s",
                                    f.getCity().getCityName(),
                                    f.getMinTemperature(),
                                    f.getMaxTemperature(),
                                    f.getSunrise(),
                                    f.getSunset()))
                            .append(System.lineSeparator());
                });

        return sb.toString().trim();
    }
}
