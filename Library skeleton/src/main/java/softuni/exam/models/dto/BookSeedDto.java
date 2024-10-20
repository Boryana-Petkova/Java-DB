package softuni.exam.models.dto;

import com.google.gson.annotations.Expose;

import softuni.exam.models.entity.enums.Genre;

import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;


public class BookSeedDto {

    //•	title – accepts char sequence (between 3 to 40 inclusive). The values are unique in the database.
    //•	author - accepts char sequence (between 3 to 40 inclusive).
    //•	description - a long and detailed description of the book with a character length value higher than or equal to 5.
    //•	available – accepts a true or false, representing the availability status of the book.
    //•	genre – String enumeration, one of the following – CLASSIC_LITERATURE, SCIENCE_FICTION, FANTASY
    //•	rating – accepts number values that are positive.

    @Expose
    @Size(min = 3, max = 40)
    private String title;

    @Expose
    @Size(min = 3, max = 40)
    private String author;

    @Expose
    @Size(min = 5)
    private String description;

    @Expose
    private boolean available;

    @Expose
    private Genre genre;

    @Expose
    @Positive
    private double rating;



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
