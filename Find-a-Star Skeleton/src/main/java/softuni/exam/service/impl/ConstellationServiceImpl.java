package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ConstellationSeedDto;
import softuni.exam.models.entity.Constellation;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class ConstellationServiceImpl implements ConstellationService {

    private static final String CONSTELLATIONS_FILE_PATH = "src/main/resources/files/json/constellations.json";


    private final ConstellationRepository constellationRepository;
    private final Gson gson;
    private  final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    @Autowired
    public ConstellationServiceImpl(ConstellationRepository constellationRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.constellationRepository = constellationRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.constellationRepository.count() > 0;
    }

    @Override
    public String readConstellationsFromFile() throws IOException {
        return Files.readString(Path.of(CONSTELLATIONS_FILE_PATH));
    }

    @Override
    public String importConstellations() throws IOException {


        //name – description
        //If a constellation with the same name already exists in the DB return "Invalid constellation".

        //Successfully imported constellation Andromeda - A princess chained to a rock, saved by Perseus.
        //Invalid constellation

        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readConstellationsFromFile(), ConstellationSeedDto[].class))
                .filter(constellationSeedDto -> {

                    boolean isValid = validationUtil.isValid(constellationSeedDto);

                    Optional<Constellation> constellationOptinal = constellationRepository.findConstellationByName(constellationSeedDto.getName());

                    if(constellationOptinal.isPresent()){
                        isValid = false;
                    }

                    sb.append(isValid ? String.format("Successfully imported constellation %s - %s"
                                    ,constellationSeedDto.getName(), constellationSeedDto.getDescription())
                    : "Invalid constellation")
                            .append(System.lineSeparator());


                    return isValid;
                })
                .map(constellationSeedDto -> modelMapper.map(constellationSeedDto, Constellation.class)).forEach(constellationRepository::save);

        return sb.toString().trim();
    }


}
