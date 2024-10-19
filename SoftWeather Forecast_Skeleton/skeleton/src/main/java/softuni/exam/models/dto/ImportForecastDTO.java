package softuni.exam.models.dto;

import softuni.exam.models.entity.City;

import javax.validation.constraints.*;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.time.DayOfWeek;
import java.time.LocalTime;

@XmlAccessorType(XmlAccessType.FIELD)
public class ImportForecastDTO {


    @NotNull
    @XmlElement(name = "day_of_week")
    private DayOfWeek dayOfWeek;
    @NotNull
    @XmlElement(name = "max_temperature")
    @DecimalMin(value = "-20")
    @DecimalMax(value = "60")
    private double maxTemperature;
    @NotNull
    @XmlElement(name = "min_temperature")
    @DecimalMin(value = "-50")
    @DecimalMax(value = "40")
    private double minTemperature;
    @NotNull
    @XmlElement
    private String sunrise;
    @NotNull
    @XmlElement
    private String sunset;

    @NotNull
    @XmlElement
    private long city;

    public DayOfWeek getDayOfWeek() {
        return dayOfWeek;
    }

    public double getMaxTemperature() {
        return maxTemperature;
    }

    public double getMinTemperature() {
        return minTemperature;
    }

    public String getSunrise() {
        return sunrise;
    }

    public String getSunset() {
        return sunset;
    }

    public long getCity() {
        return city;
    }

    public void setDayOfWeek(DayOfWeek dayOfWeek) {
        this.dayOfWeek = dayOfWeek;
    }

    public void setMaxTemperature(double maxTemperature) {
        this.maxTemperature = maxTemperature;
    }

    public void setMinTemperature(double minTemperature) {
        this.minTemperature = minTemperature;
    }

    public void setSunrise(String sunrise) {
        this.sunrise = sunrise;
    }

    public void setSunset(String sunset) {
        this.sunset = sunset;
    }

    public void setCity(long city) {
        this.city = city;
    }
}
