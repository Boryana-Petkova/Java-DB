package softuni.exam.models.dto;

import softuni.exam.models.entity.enums.Rating;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class SellerSeedDTO {

    //•	firstName – a char sequence (between 2 to 20 exclusive).
    //•	lastName – a char sequence (between 2 to 20 exclusive).
    //•	email – an email – (must contain '@' and '.' – dot). The email of a seller is unique.
    //•	rating – enumerated value must be one of these GOOD, BAD or UNKNOWN. Cannot be null.
    //•	town – a char sequence – the name of a town. Cannot be null.


    //<sellers> // This is the root
    //    <seller> // This is the DTO
    //        <first-name>B</first-name>
    //        <last-name>Marquet</last-name>
    //        <email>bmarquet0@reverbnation.com</email>
    //        <rating>UNKNOWN</rating>
    //        <town>Huangli</town>

    @XmlElement(name = "first-name")
    @Size(min = 2, max = 19)
    private String firstName;

    @XmlElement(name = "last-name")
    private String lastName;

    @XmlElement
    @Email
    private String email;

    @XmlElement (name = "rating")
    private Rating rating;

    @XmlElement(name = "town")
    private String town;


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Size(min = 2, max = 19)
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @NotNull
    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    @NotBlank
    public String getTown() {
        return town;
    }

    public void setTown(String town) {
        this.town = town;
    }
}
