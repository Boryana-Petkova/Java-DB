package softuni.exam.service;

import softuni.exam.models.entity.Astronomer;
import softuni.exam.models.entity.Star;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

// TODO: Implement all methods
public interface StarService {

    boolean areImported();

    String readStarsFileContent() throws IOException;
	
	String importStars() throws IOException;

    String exportStars();


}
