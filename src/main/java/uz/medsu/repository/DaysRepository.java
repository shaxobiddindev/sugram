package uz.medsu.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.medsu.entity.Day;

public interface DaysRepository extends JpaRepository<Day, Integer> {
}
