package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.PictureDTO;
import softuni.exam.models.entity.Picture;
import softuni.exam.repository.PictureRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.PictureService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;

@Service
public class PictureServiceImpl implements PictureService {

    private static final String PICTURE_PATH = "src/main/resources/files/json/pictures.json";

    private final PictureRepository pictureRepository;

    private final CarService carService;

    private final Gson gson;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;

    public PictureServiceImpl(PictureRepository pictureRepository, CarService carService, Gson gson, ModelMapper modelMapper, ValidationUtil validationUtil) {
        this.pictureRepository = pictureRepository;
        this.carService = carService;
        this.gson = gson;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
    }

    @Override
    public boolean areImported() {
        return this.pictureRepository.count() > 0;
    }

    @Override
    public String readPicturesFromFile() throws IOException {
        return Files.readString(Path.of(PICTURE_PATH));
    }

    @Override
    public String importPictures() throws IOException {

        //Successfully import picture - mZ_zT_oH
        //Invalid picture
        StringBuilder sb = new StringBuilder();

        //PictureDTO [] pictureDTOs = gson.fromJson(readPicturesFromFile(), PictureDTO[].class);

        Arrays.stream(gson.fromJson(readPicturesFromFile(), PictureDTO[].class)).filter(pictureDTO ->{
            boolean isValid = validationUtil.isValid(pictureDTO);

            sb.append(isValid ? String.format("Successfully import picture - %s", pictureDTO.getName())
                    : "Invalid picture");
            sb.append(System.lineSeparator());


            return isValid;
        })
                .map (pictureDTO -> {
                    Picture picture = modelMapper.map(pictureDTO, Picture.class);
                    picture.setCar(carService.findById(pictureDTO.getCar()));

                return picture;
        })
                .forEach(pictureRepository::save);

        return sb.toString();
    }
}
