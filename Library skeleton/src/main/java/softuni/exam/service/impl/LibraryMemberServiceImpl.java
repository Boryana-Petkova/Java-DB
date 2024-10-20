package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.BookSeedDto;
import softuni.exam.models.dto.LibraryMemberDto;
import softuni.exam.models.dto.LibraryMemberSeedDto;
import softuni.exam.models.entity.Book;
import softuni.exam.models.entity.LibraryMember;
import softuni.exam.repository.BookRepository;
import softuni.exam.repository.LibraryMemberRepository;
import softuni.exam.service.LibraryMemberService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;

@Service
public class LibraryMemberServiceImpl implements LibraryMemberService {

    public static final String LIBRA_PATH = "src/main/resources/files/json/library-members.json";

    private final LibraryMemberRepository libraryMemberRepository;

    private final Gson gson;
    private  final ValidationUtil validationUtil;
    private final ModelMapper modelMapper;

    public LibraryMemberServiceImpl(LibraryMemberRepository libraryMemberRepository, Gson gson, ValidationUtil validationUtil, ModelMapper modelMapper) {
        this.libraryMemberRepository = libraryMemberRepository;
        this.gson = gson;
        this.validationUtil = validationUtil;
        this.modelMapper = modelMapper;
    }


    @Override
    public boolean areImported() {
        return this.libraryMemberRepository.count() > 0;
    }

    @Override
    public String readLibraryMembersFileContent() throws IOException {
        return Files.readString(Path.of(LIBRA_PATH));
    }

    @Override
    public String importLibraryMembers() throws IOException {

        //•	If a library member with the same phone number already exists in the DB return "Invalid library member".

        //Successfully imported library member John - Doe
        //Invalid library member

        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson.fromJson(readLibraryMembersFileContent(), LibraryMemberSeedDto[].class)).filter( libraryMemberSeedDto -> {
            boolean isValid = validationUtil.isValid(libraryMemberSeedDto);

            Optional<LibraryMember> libraryMemberOptional = libraryMemberRepository.findByPhoneNumber(libraryMemberSeedDto.getPhoneNumber());

            if(libraryMemberOptional.isPresent()){
                isValid = false;
            }

            sb.append(isValid ? String.format("Successfully imported library member %s - %s"
                    , libraryMemberSeedDto.getFirstName(), libraryMemberSeedDto.getLastName())
                    : "Invalid library member").append(System.lineSeparator());

            return  isValid;

        }).map(libraryMemberSeedDto -> modelMapper.map(libraryMemberSeedDto, LibraryMember.class)).forEach(libraryMemberRepository::save);


        return sb.toString().trim();

    }

    @Override
    public LibraryMember findMemberById(Long id) {
        return libraryMemberRepository.findById(id).orElse(null);
    }
}
