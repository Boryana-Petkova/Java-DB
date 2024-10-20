package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;
import softuni.exam.models.entity.Car;

import javax.validation.constraints.Size;
import java.time.LocalDateTime;

public class PictureDTO {

    //•	name – a char sequence (between 2 to 20 exclusive). The name of a picture is unique.

    @Expose
    private String name;
    @Expose
    private String dateAndTime;


    // we put Long because we see in files that car is Id infact
    @Expose
    private Long car;

    public PictureDTO() {

    }

    @Size(min = 2, max = 19)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDateAndTime() {
        return dateAndTime;
    }

    public void setDateAndTime(String dateAndTime) {
        this.dateAndTime = dateAndTime;
    }

    public Long getCar() {
        return car;
    }

    public void setCar(Long car) {
        this.car = car;
    }
}
