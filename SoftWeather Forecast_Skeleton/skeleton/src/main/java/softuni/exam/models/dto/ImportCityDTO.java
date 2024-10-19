package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.Min;
import javax.validation.constraints.Size;

public class ImportCityDTO {

    @Expose
    @Size(min = 2, max = 60)
    private String cityName;
    @Expose
    @Size(min = 2)
    private String description;
    @Expose
    @Min(value = 500)
    private long population;
    @Expose
    private long country;



    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getPopulation() {
        return population;
    }

    public void setPopulation(long population) {
        this.population = population;
    }

    public long getCountry() {
        return country;
    }

    public void setCountry(long country) {
        this.country = country;
    }

}
