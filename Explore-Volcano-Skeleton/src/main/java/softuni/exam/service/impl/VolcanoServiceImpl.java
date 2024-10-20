package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountrySeedDto;
import softuni.exam.models.dto.VolcanoSeedDto;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Volcano;
import softuni.exam.models.entity.Volcanologist;
import softuni.exam.repository.VolcanoRepository;
import softuni.exam.service.CountryService;
import softuni.exam.service.VolcanoService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class VolcanoServiceImpl implements VolcanoService {

    public static final String VOLCANO_PATH = "src/main/resources/files/json/volcanoes.json";
    private final VolcanoRepository volcanoRepository;
    private final CountryService countryService;

    private final Gson gson;
    private  final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public VolcanoServiceImpl(VolcanoRepository volcanoRepository, CountryService countryService, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.volcanoRepository = volcanoRepository;
        this.countryService = countryService;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.volcanoRepository.count() > 0;
    }

    @Override
    public String readVolcanoesFileContent() throws IOException {
        return Files.readString(Path.of(VOLCANO_PATH));
    }

    @Override
    public String importVolcanoes() throws IOException {

        //•	If a volcano with the same name already exists in the DB return "Invalid volcano".
        //•	When the import is finished:
        //"Successfully imported volcano {volcanoName} of type {volcanoType}"
        //•	The provided country ids will always be valid.

        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readVolcanoesFileContent(), VolcanoSeedDto[].class))
                .filter(volcanoSeedDto -> {

            boolean isValid = validationUtil.isValid(volcanoSeedDto);


            Optional<Volcano> volcanoOptional = volcanoRepository.findByName(volcanoSeedDto.getName());

            if (volcanoOptional.isPresent()){
                isValid = false;
            }
            sb.append(isValid ? String.format("Successfully imported volcano %s of type %s", volcanoSeedDto.getName(), volcanoSeedDto.getVolcanoType())
                    : "Invalid volcano").append(System.lineSeparator());

            return  isValid;

        }).map(volcanoSeedDto -> {
            Volcano volcano = modelMapper.map(volcanoSeedDto, Volcano.class);
            Country country = countryService.getCountryById(volcanoSeedDto.getCountry()).orElse(null);


            country.getVolcanoes().add(volcano);
            countryService.saveAddedVolcanoInCountry(country);
            volcano.setCountry(country);

            return volcano;

        }).forEach(volcanoRepository::save);

        return sb.toString().trim();
    }

    @Override
    public Volcano findVolcanoById(Long volcanoId) {
        return volcanoRepository.findById(volcanoId).orElse(null);
    }

    @Override
    public void addAndSaveAddedVolcano(Volcano volcano, Volcanologist volcanologist) {
        volcano.getVolcanologists().add(volcanologist);
        volcanoRepository.save(volcano);

    }

    @Override
    public String exportVolcanoes() {

        //•	Filter only volcanoes that are active with an elevation
        // of more than 3000m. and have information about the last eruption. Order the results descending by elevation.
        //•	Return the information in this format:
        //"Volcano: {volcano name}
        //   *Located in: {country name}
        //   **Elevation: {elevation}
        //   ***Last eruption on: {lastEruption}
        //. . ."

        //{
        //    "name": "Mount St. Helens",
        //    "elevation": 2549,
        //    "volcanoType": "STRATOVOLCANO",
        //    "isActive": false,
        //    "lastEruption": "1980-05-18",
        //    "country": 4
        //  },
        //  {
        //    "name": "Mount Kilimanjaro",
        //    "elevation": 5895,
        //    "volcanoType": "STRATOVOLCANO",
        //    "isActive": true,
        //    "lastEruption": null,
        //    "country": 9
        //  },

        //Това в Repo може да се напише
        // @Query(value = "SELECT * FROM volcanoes WHERE elevation > 3000" +
        //            " AND last_eruption IS NOT NULL AND is_active = 1 ORDER BY elevation DESC", nativeQuery = true)
        //    Set<Volcano> findByElevationGreaterThanAndActiveIsTrueAndLastEruptionIsNotNullOrderByElevationDesc();

        StringBuilder sb = new StringBuilder();



      List<Volcano> volcanoList = volcanoRepository.findAllByElevationGreaterThanAndIsActiveTrueAndLastEruptionIsNotNullOrderByElevationDesc(3000);

       volcanoList.forEach(volcano -> {
           sb.append(String.format("Volcano: %s\n" +
                   "   *Located in: %s\n" +
                   "   **Elevation: %d\n" +
                   "   ***Last eruption on: %s", volcano.getName(), volcano.getCountry().getName()
                   , volcano.getElevation(),volcano.getLastEruption()))
                   .append(System.lineSeparator());
       });

        return sb.toString().trim();
    }
}