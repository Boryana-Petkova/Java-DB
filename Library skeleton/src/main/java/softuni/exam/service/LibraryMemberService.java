package softuni.exam.service;

import softuni.exam.models.dto.LibraryMemberDto;
import softuni.exam.models.entity.LibraryMember;

import java.io.IOException;

// TODO: Implement all methods
public interface LibraryMemberService {

    boolean areImported();

    String readLibraryMembersFileContent() throws IOException;
	
	String importLibraryMembers() throws IOException;


    LibraryMember findMemberById(Long id);
}
