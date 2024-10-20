package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.BookDto;
import softuni.exam.models.dto.BookSeedDto;
import softuni.exam.models.entity.Book;
import softuni.exam.repository.BookRepository;
import softuni.exam.service.BookService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    public static final String BOOK_PATH = "src/main/resources/files/json/books.json";

    private final BookRepository bookRepository;

    private final Gson gson;
    private  final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public BookServiceImpl(BookRepository bookRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.bookRepository = bookRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.bookRepository.count() > 0;
    }

    @Override
    public String readBooksFromFile() throws IOException {
        return Files.readString(Path.of(BOOK_PATH));
    }

    @Override
    public String importBooks() throws IOException {

        //Constraint
        //•	If a book with the same title already exists in the DB return "Invalid book".

        //Successfully imported book F. Scott Fitzgerald - The Great Gatsby
        //Invalid book

        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readBooksFromFile(), BookSeedDto[].class)).filter(bookSeedDto -> {
            boolean isValid = validationUtil.isValid(bookSeedDto);

            Optional<Book> bookOptional = bookRepository.findByTitle(bookSeedDto.getTitle());
            if(bookOptional.isPresent()){
                isValid = false;
            }

            sb.append(isValid ? String.format("Successfully imported book %s - %s", bookSeedDto.getAuthor(), bookSeedDto.getTitle())
                    : "Invalid book").append(System.lineSeparator());

            return  isValid;

        }).map(bookSeedDto -> modelMapper.map(bookSeedDto, Book.class)).forEach(bookRepository::save);


        return sb.toString().trim();

    }

    @Override
    public Book findByTitle(String title) {
        return bookRepository.findByTitle(title).orElse(null);
    }
}
