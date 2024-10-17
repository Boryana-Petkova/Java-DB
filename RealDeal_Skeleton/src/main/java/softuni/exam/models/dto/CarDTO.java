package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.time.LocalDate;

public class CarDTO {

    //•	make – a char sequence (between 2 to 20 exclusive). – без 20
    //•	model – a char sequence (between 2 to 20 exclusive).
    //•	kilometers – a number (must be positive).
    //•	registeredOn – a date.
    //The combination of make, model and kilometers makes a car unique.

    @Expose
    private String make;
    @Expose
    private String model;
    @Expose
    private Integer kilometers;

    @Expose
    private String registeredOn;

    @Size(min = 2, max = 19)
    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }
    @Size(min = 2, max = 19)
    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    @Positive
    public Integer getKilometers() {
        return kilometers;
    }

    public void setKilometers(Integer kilometers) {
        this.kilometers = kilometers;
    }

    public String getRegisteredOn() {
        return registeredOn;
    }

    public void setRegisteredOn(String registeredOn) {
        this.registeredOn = registeredOn;
    }
}
