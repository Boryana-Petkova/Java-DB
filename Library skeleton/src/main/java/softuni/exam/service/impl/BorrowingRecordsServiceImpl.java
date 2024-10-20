package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.BorrowingRecordRootDto;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.models.entity.enums.Genre;
import softuni.exam.repository.BorrowingRecordRepository;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.BookService;
import softuni.exam.service.BorrowingRecordsService;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static softuni.exam.models.entity.enums.Genre.SCIENCE_FICTION;

@Service
public class BorrowingRecordsServiceImpl implements BorrowingRecordsService {

    public static final String BORROW_PATH = "src/main/resources/files/xml/borrowing-records.xml";

    private final BorrowingRecordRepository borrowingRecordRepository;
    private  final BookService bookService;

    private final LibraryMemberService libraryMemberService;

    private final Gson gson;
    private  final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;
    private final XmlParser xmlParser;

    public BorrowingRecordsServiceImpl(BorrowingRecordRepository borrowingRecordRepository, BookService bookService, LibraryMemberService libraryMemberService, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper, XmlParser xmlParser) {
        this.borrowingRecordRepository = borrowingRecordRepository;
        this.bookService = bookService;
        this.libraryMemberService = libraryMemberService;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
        this.xmlParser = xmlParser;
    }

    @Override
    public boolean areImported() {
        return this.borrowingRecordRepository.count() > 0;
    }

    @Override
    public String readBorrowingRecordsFromFile() throws IOException {
        return Files.readString(Path.of(BORROW_PATH));
    }

    @Override
    public String importBorrowingRecords() throws IOException, JAXBException {

        //•	If a book with the given title doesn't exist in the DB return "Invalid borrowing record".
        //•	If a library member with the given id doesn't exist in the DB return "Invalid borrowing record".

        //Successfully imported borrowing record The Lord of the Rings - 2020-01-13
        //Invalid borrowing record

        //title- borrow date

        StringBuilder sb = new StringBuilder();

        xmlParser.fromFile(BORROW_PATH, BorrowingRecordRootDto.class).getBorrowingRecordSeedDto().stream()
                .filter(borrowingRecordSeedDto -> {

                    boolean isValid = validationUtil.isValid(borrowingRecordSeedDto);

                    Book book = bookService.findByTitle(borrowingRecordSeedDto.getBook().getTitle());

                    if (book == null){
                        isValid = false;
                    }
                    LibraryMember libraryMember = libraryMemberService.findMemberById(borrowingRecordSeedDto.getMember().getId());

                    if (libraryMember == null){
                        isValid = false;
                    }



                sb.append(isValid ? String.format("Successfully imported borrowing record %s - %s"
                , borrowingRecordSeedDto.getBook().getTitle(), borrowingRecordSeedDto.getBorrowDate())
                : "Invalid borrowing record").append(System.lineSeparator());

            return  isValid;

        }).map(borrowingRecordSeedDto -> {
            BorrowingRecord borrowingRecord = modelMapper.map(borrowingRecordSeedDto, BorrowingRecord.class);
                    Book book = bookService.findByTitle(borrowingRecordSeedDto.getBook().getTitle());
                    LibraryMember libraryMember = libraryMemberService.findMemberById(borrowingRecordSeedDto.getMember().getId());
            borrowingRecord.setBook(book);
            borrowingRecord.setLibraryMember(libraryMember);

            return borrowingRecord;

        }).forEach(borrowingRecordRepository::save);

        return sb.toString().trim();

}

    @Override
    public String exportBorrowingRecords() {

        //Export the Borrowing records before 2021-09-10 from the Database

        //•	Filter only books that are SCIENCE_FICTION and order them by the borrow date in descending order.
        //•	Return the information in this format:
        //"Book title: {bookTitle}
        //"*Book author: {bookAuthor}
        //"**Date borrowed: {dateBorrowed}
        //"***Borrowed by: {firstName} {lastName}

        StringBuilder sb = new StringBuilder();



        Set<BorrowingRecord> borrowingRecordSet
                = borrowingRecordRepository.findAllByBorrowDateBeforeAndBook_GenreOrderByBorrowDateDesc(LocalDate.parse("2021-09-10"), SCIENCE_FICTION);

        borrowingRecordSet.stream().forEach(borrowingRecord -> {
            sb.append(String.format("Book title: %s\n" +
                    "*Book author: %s\n" +
                    "**Date borrowed: %s\n" +
                    "***Borrowed by: %s %s"
                    , borrowingRecord.getBook().getTitle(), borrowingRecord.getBook().getAuthor()
                    , borrowingRecord.getBorrowDate()
                    , borrowingRecord.getLibraryMember().getFirstName()
                    , borrowingRecord.getLibraryMember().getLastName())).append(System.lineSeparator());
        });


        return sb.toString().trim();
    }
}
