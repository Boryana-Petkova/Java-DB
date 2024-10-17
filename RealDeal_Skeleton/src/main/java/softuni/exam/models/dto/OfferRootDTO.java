package softuni.exam.models.dto;

import softuni.exam.models.entity.Offer;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "offers")
@XmlAccessorType(XmlAccessType.FIELD)
public class OfferRootDTO {

    @XmlElement(name = "offer")
    private List<OfferSeedDTO> offersSeedDTOs;

    public List<OfferSeedDTO> getOffersSeedDTOs() {
        return offersSeedDTOs;
    }

    public void setOffersSeedDTOs(List<OfferSeedDTO> offersSeedDTOs) {
        this.offersSeedDTOs = offersSeedDTOs;
    }
}
