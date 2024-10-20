package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.dto.LibraryMemberDto;
import softuni.exam.models.entity.BorrowingRecord;
import softuni.exam.models.entity.enums.Genre;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;


@Repository
public interface BorrowingRecordRepository extends JpaRepository<BorrowingRecord, Long> {


    Set<BorrowingRecord> findAllByBorrowDateBeforeAndBook_GenreOrderByBorrowDateDesc(LocalDate parse, Genre genre);
}
