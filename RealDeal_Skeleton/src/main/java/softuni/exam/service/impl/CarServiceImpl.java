package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CarDTO;
import softuni.exam.models.entity.Car;
import softuni.exam.repository.CarRepository;
import softuni.exam.service.CarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Service
public class CarServiceImpl implements CarService {

    private static final String CAR_PATH = "src/main/resources/files/json/cars.json";

    private CarRepository carRepository;

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public CarServiceImpl(CarRepository carRepository, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.carRepository = carRepository;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.carRepository.count() > 0;
    }

    @Override
    public String readCarsFileContent() throws IOException {
        return Files.readString(Path.of(CAR_PATH));
    }

    @Override
    public String importCars() throws IOException {

        StringBuilder sb = new StringBuilder();


        Arrays.stream(gson.fromJson(readCarsFileContent(), CarDTO[].class)).filter(carDTO -> {

            boolean isValid = validationUtil.isValid(carDTO);

            sb.append( isValid ? String.format("Successfully imported car - %s - %s", carDTO.getMake(), carDTO.getModel())
                    : String.format("Invalid car"));
            sb.append(System.lineSeparator());

            return  isValid;
        })
                .map(carDTO -> modelMapper.map(carDTO, Car.class)).forEach(carRepository::save);



        return sb.toString();
    }

    //Export cars order by pictures count in descending order, then by make
    //•	Extract from the database, the make, model, kilometers,
    // date of registration and count of pictures of all cars.
    // Order them first by pictures in descending order then by make alphabetically.

    //•	Return the information in this format:
    //"Car make - {make}, model - {model}
    //	Kilometers - {kilometers}
    //	Registered on - {registered on}
    //	Number of pictures - {number of pictures}
    //. . . "

    @Override
    public String getCarsOrderByPicturesCountThenByMake() {

        StringBuilder sb = new StringBuilder();

        carRepository.findCarsOrderByPicturesCountDescThenByMakeAnd().stream().forEach(car -> {
            sb.append(String.format("Car make - %s, model - %s\n" +
                    "    //\tKilometers - %d\n" +
                    "    //\tRegistered on - %s\n" +
                    "    //\tNumber of pictures - %d",
                    car.getMake(), car.getModel(), car.getKilometers(), car.getRegisteredOn(), car.getPictures().size()))
                    .append(System.lineSeparator());
        });
        return sb.toString();
    }

    @Override
    public Car findById(Long id) {
        return carRepository.findById(id).orElse(null);
    }
}
