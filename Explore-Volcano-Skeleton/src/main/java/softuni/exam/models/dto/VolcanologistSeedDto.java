package softuni.exam.models.dto;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
@XmlAccessorType(XmlAccessType.FIELD)
public class VolcanologistSeedDto {


    //•	first name - accepts char sequence (between 2 to 30 both inclusive). Unique, it can not be nullable.
    //•	last name - accepts char sequence (between 2 to 30 both inclusive). Unique, it can not be nullable.
    //•	salary - accepts positive number values. It can not be nullable.
    //•	age - accepts number values that are between 18 and 80 both inclusive. It can not be nullable.
    //•	exploring from - a date in the "yyyy-MM-dd" format. It can be nullable.

    //•	Constraint: The volcanologists table has a relation with volcanoes table. It can be nullable.

    @XmlElement(name = "first_name")
    @Size(min = 2, max = 30)
    @NotNull
    private String firstName;

    @XmlElement(name = "last_name")
    @Size(min = 2, max = 30)
    @NotNull
    private String lastName;

    @XmlElement(name = "salary")
    @NotNull
    private double salary;

    @XmlElement(name = "age")
    @DecimalMin(value = "18")
    @DecimalMax(value = "80")
    @NotNull
    private int age;

    @XmlElement(name = "exploring_from")
    private String exploringFrom;

    @XmlElement(name = "exploring_volcano_id")
    private long exploringVolcano;



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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getExploringFrom() {
        return exploringFrom;
    }

    public void setExploringFrom(String exploringFrom) {
        this.exploringFrom = exploringFrom;
    }

    public long getExploringVolcano() {
        return exploringVolcano;
    }

    public void setExploringVolcano(long exploringVolcano) {
        this.exploringVolcano = exploringVolcano;
    }
}
