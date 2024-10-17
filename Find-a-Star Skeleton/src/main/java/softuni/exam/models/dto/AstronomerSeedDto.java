package softuni.exam.models.dto;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.time.LocalDate;

@XmlAccessorType(XmlAccessType.FIELD)
public class AstronomerSeedDto {


    @XmlElement(name = "first_name")
    @Size(min = 2,max = 30)
    @NotNull
    private String firstName;

    @XmlElement(name = "last_name")
    @Size(min = 2,max = 30)
    @NotNull
    private String lastName;
    @XmlElement(name = "salary")
    @DecimalMin(value = "15000")
    @NotNull
    private double salary;

    @XmlElement(name = "average_observation_hours")
    @DecimalMin(value = "500")
    @NotNull
    private double averageObservationHours;

    @XmlElement(name = "birthday")
    private String birthday;

    @XmlElement(name = "observing_star_id")
    private Long observingStar;



    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public double getAverageObservationHours() {
        return averageObservationHours;
    }

    public void setAverageObservationHours(double averageObservationHours) {
        this.averageObservationHours = averageObservationHours;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Long getObservingStar() {
        return observingStar;
    }

    public void setObservingStar(Long observingStar) {
        this.observingStar = observingStar;
    }
}
