package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.SellerRootDTO;
import softuni.exam.models.dto.SellerSeedDTO;
import softuni.exam.models.entity.Seller;
import softuni.exam.repository.SellerRepository;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;


@Service
public class SellerServiceImpl implements SellerService {

    private static final String SELLER_PATH = "src/main/resources/files/xml/sellers.xml";

    private final SellerRepository sellerRepository;

    private final XmlParser xmlParser;

    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;


    public SellerServiceImpl(SellerRepository sellerRepository, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.sellerRepository = sellerRepository;

        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;

    }

    @Override
    public boolean areImported() {
        return this.sellerRepository.count() > 0;
    }

    @Override
    public String readSellersFromFile() throws IOException {
        return Files.readString(Path.of(SELLER_PATH));
    }

    @Override
    public String importSellers() throws FileNotFoundException, JAXBException {


        //Invalid seller
        //Successfully import seller Juanes - kjuanes2@google.com.br

        StringBuilder sb = new StringBuilder();
        xmlParser
                .fromFile(SELLER_PATH, SellerRootDTO.class)
                .getSellers()
                .stream()
                .filter(sellerSeedDTO -> {

                    boolean isValid = validationUtil.isValid(sellerSeedDTO);

                    sb.append(isValid ? String.format("Successfully import seller %s - %s", sellerSeedDTO.getLastName(), sellerSeedDTO.getEmail())
                            : "Invalid seller");
                    sb.append(System.lineSeparator());

                    return isValid;

                })
                .map(sellerSeedDTO -> modelMapper.map(sellerSeedDTO, Seller.class)).forEach(sellerRepository::save);

        return sb.toString();
    }

    @Override
    public Seller findById(Long id) {
        return sellerRepository.findById(id).orElse(null);
    }
}
