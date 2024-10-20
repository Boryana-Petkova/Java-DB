package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Volcano;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface VolcanoRepository extends JpaRepository<Volcano, Long> {


    Optional<Volcano> findByName(String name);


    List<Volcano> findAllByElevationGreaterThanAndIsActiveTrueAndLastEruptionIsNotNullOrderByElevationDesc(int elevation);
}
