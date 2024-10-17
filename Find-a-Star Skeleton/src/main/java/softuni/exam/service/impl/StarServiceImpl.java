package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.ConstellationSeedDto;
import softuni.exam.models.dto.StarSeedDto;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Constellation;
import softuni.exam.models.entity.Star;
import softuni.exam.models.entity.StarType;
import softuni.exam.repository.ConstellationRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.ConstellationService;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class StarServiceImpl implements StarService {


    private final StarRepository starRepository;

    private final ConstellationRepository constellationRepository;
    private final Gson gson;
    private  final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;


    @Autowired
    public StarServiceImpl(StarRepository starRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, ConstellationService constellationService, ConstellationRepository constellationRepository) {
        this.starRepository = starRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.constellationRepository = constellationRepository;
    }

    @Override
    public boolean areImported() {
        return this.starRepository.count() > 0;
    }

    @Override
    public String readStarsFileContent() throws IOException {
        Path path = Path.of("src", "main","resources","files","json","stars.json");
        return String.join("\n", Files.readAllLines(path));
    }

    @Override
    public String importStars() throws IOException {

        //[
        //  {
        //    "description": "Glowing sphere of celestial gas",
        //    "lightYears": 25.34,
        //    "name": "X",
        //    "starType": "WHITE_DWARF",
        //    "constellation": 18
        //  },

        //name – light years
        //•	The provided constellation ids will always be valid.

        //Invalid star
        //Successfully imported star Sirius - 25.34 light years



        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readStarsFileContent(), StarSeedDto[].class))
                .filter(starSeedDto -> {

                    boolean isValid = validationUtil.isValid(starSeedDto);

                    Optional<Star> starOptional = starRepository.findByName(starSeedDto.getName());

                    if(starOptional.isPresent()){
                        isValid = false;
                    }

                    sb.append(isValid ? String.format("Successfully imported star %s - %.2f light years"
                                    ,starSeedDto.getName(), starSeedDto.getLightYears())
                                    : "Invalid star")
                            .append(System.lineSeparator());


                    return isValid;
                })
                .map(starSeedDto -> {

                    Star star = this.modelMapper.map(starSeedDto, Star.class);

                   star.setStarType(StarType.valueOf(starSeedDto.getStarType()));

                   star.setConstellation(this.constellationRepository.findById(starSeedDto.getConstellation()).get());
                   this.starRepository.saveAndFlush(star);

                   return star;



                }).forEach(starRepository::save);

        return sb.toString().trim();

    }

    @Override
    public String exportStars() {

        //•	Filter only stars who are Red Giants and have never been observed and order them by the light years in ascending order.
        //•	Return the information in this format:
        //"Star: {starName}
        //"   *Distance: {lightYears} light years
        //"   **Description: {description}
        //"   ***Constellation: {constellationName}
        //. . ."

        StringBuilder sb = new StringBuilder();

        List<Star> starsByTypeAndObserversIsNull = starRepository.findAllByStarTypeAndObserversIsNullOrderByLightYearsAsc(StarType.RED_GIANT);

        starsByTypeAndObserversIsNull.stream().forEach(s-> {
            sb.append(String.format("Star: %s\n" +
                                    "   *Distance: %.2f light years\n" +
                                    "   **Description: %s\n" +
                                    "   ***Constellation: %s\n", s.getName(), s.getLightYears(),s.getDescription(),s.getConstellation().getName()));
        });

        return sb.toString();
    }


}
