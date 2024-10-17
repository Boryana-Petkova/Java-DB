package softuni.exam.service;

import softuni.exam.models.entity.Constellation;

import java.io.IOException;
import java.util.Optional;

// TODO: Implement all methods

public interface ConstellationService {

    boolean areImported();

    String readConstellationsFromFile() throws IOException;

	String importConstellations() throws IOException;



}
