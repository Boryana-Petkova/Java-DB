package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


public class VolcanoSeedDto {

    //•	name - accepts char sequence (between 2 to 30 both inclusive). The values are unique in the database. It cannot be nullable.
    //•	elevation - The highest point of the volcano. Accepts only positive numbers. It cannot be nullable.
    //•	volcano type - categorization of the volcanoes. String enumeration,
    // one of the following – CINDER_CONE, STRATOVOLCANO, SHIELD_VOLCANO, LAVA_DOME, CALDERA. It can be nullable.
    //•	is active – accepts a true or false, representing whether the volcano is active or not. It cannot be nullable.
    //•	last eruption – indicates when the last eruption occurred. It can be nullable.

    //•	Constraint: The volcanoes table has a relation with the countries table. It can be nullable.

    @Expose
    @Size(min = 2, max = 30)
    private String name;

    @Expose
    @Positive
    private int elevation;

    @Expose
    private String volcanoType;

    @Expose
    private boolean isActive;

    @Expose
    private String lastEruption;

    @Expose
    private long country;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getElevation() {
        return elevation;
    }

    public void setElevation(int elevation) {
        this.elevation = elevation;
    }

    public String getVolcanoType() {
        return volcanoType;
    }

    public void setVolcanoType(String volcanoType) {
        this.volcanoType = volcanoType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getLastEruption() {
        return lastEruption;
    }

    public void setLastEruption(String lastEruption) {
        this.lastEruption = lastEruption;
    }

    public long getCountry() {
        return country;
    }

    public void setCountry(long country) {
        this.country = country;
    }
}
