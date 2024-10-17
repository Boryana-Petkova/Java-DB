package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.OfferRootDTO;
import softuni.exam.models.entity.Offer;
import softuni.exam.repository.OfferRepository;
import softuni.exam.service.CarService;
import softuni.exam.service.OfferService;
import softuni.exam.service.SellerService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Service
public class OfferServiceImpl implements OfferService {

    private static final String OFFER_PATH = "src/main/resources/files/xml/offers.xml";

    private final OfferRepository offerRepository;

    private final CarService carService;
    private final SellerService sellerService;

    private final XmlParser xmlParser;

    private final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public OfferServiceImpl(OfferRepository offerRepository, CarService carService, SellerService sellerService, XmlParser xmlParser, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.offerRepository = offerRepository;
        this.carService = carService;
        this.sellerService = sellerService;
        this.xmlParser = xmlParser;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }

    @Override
    public boolean areImported() {
        return this.offerRepository.count() > 0;
    }

    @Override
    public String readOffersFileContent() throws IOException {
        return Files.readString(Path.of(OFFER_PATH));
    }

    @Override
    public String importOffers() throws IOException, JAXBException {

        //Successfully import offer 2019-12-23T17:02:39 - true
        //Invalid offer

        //addedOn - status

        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(OFFER_PATH, OfferRootDTO.class)
                .getOffersSeedDTOs()
                .stream()
                .filter(offerSeedDTO -> {

            boolean isValid = validationUtil.isValid(offerSeedDTO);

            sb.append(isValid ? String.format("Successfully import offer %s - %s", offerSeedDTO.getAddedOn(), offerSeedDTO.isHasGoldStatus())
                    : "Invalid offer");
            sb.append(System.lineSeparator());

            return isValid;

        })
                .map(offerSeedDTO -> {
                    Offer offer = modelMapper.map(offerSeedDTO, Offer.class);
                    offer.setSeller(sellerService.findById(offerSeedDTO.getSeller().getId()));
                    offer.setCar(carService.findById(offerSeedDTO.getCar().getId()));
                    return offer;
                })
                .forEach(offerRepository::save);

        return sb.toString();
    }
}
