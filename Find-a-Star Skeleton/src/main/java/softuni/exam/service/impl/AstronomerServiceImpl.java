package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.AstronomerRootDto;
import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;
import softuni.exam.repository.AstronomerRepository;
import softuni.exam.repository.StarRepository;
import softuni.exam.service.AstronomerService;
import softuni.exam.service.StarService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

@Service
public class AstronomerServiceImpl implements AstronomerService {
    public static final String ASTRO = "src/main/resources/files/xml/astronomers.xml";

    private final AstronomerRepository astronomerRepository;

    private final StarRepository starRepository;
    private final StarService starService;

    private final Gson gson;
    private  final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    @Autowired
    public AstronomerServiceImpl(AstronomerRepository astronomerRepository, StarRepository starRepository, StarService starService, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser) {
        this.astronomerRepository = astronomerRepository;
        this.starRepository = starRepository;
        this.starService = starService;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.astronomerRepository.count() > 0;
    }

    @Override
    public String readAstronomersFromFile() throws IOException {
        return Files.readString(Path.of(ASTRO));
    }

    @Override
    public String importAstronomers() throws IOException, JAXBException {

        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(ASTRO, AstronomerRootDto.class)
                .getAstronomerSeedDtos()
                .stream()
                .filter(astronomerSeedDto -> {
                   boolean isValid = validationUtil.isValid(astronomerSeedDto);

                   Optional<Star> starOpt = this.starRepository.findById(astronomerSeedDto.getObservingStar());

                   Optional<Astronomer> astronomerOpt = this.astronomerRepository.findByFirstNameAndLastName(astronomerSeedDto.getFirstName(),
                            astronomerSeedDto.getLastName());

                    if (astronomerOpt.isPresent() || starOpt.isEmpty()) {
                        isValid = false;
                    }


                    sb
                            .append(isValid
                                    ? String.format("Successfully imported astronomer %s %s - %.2f",
                                    astronomerSeedDto.getFirstName(),
                                    astronomerSeedDto.getLastName(),
                                    astronomerSeedDto.getAverageObservationHours())
                                    : "Invalid astronomer")
                            .append(System.lineSeparator());

                    return isValid;

                })
                .map(astronomerSeedDto -> {
                   Astronomer astronomer = modelMapper.map(astronomerSeedDto, Astronomer.class);
                    Optional<Star> starOpt = this.starRepository.findById(astronomerSeedDto.getObservingStar());

                    astronomer.setObservingStar(starOpt.get());


                    return astronomer;

                })
                .forEach(astronomerRepository::save);


        return sb.toString();
    }
}
