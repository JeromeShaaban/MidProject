package MidProject.MidProject.peristence.repository;

import MidProject.MidProject.peristence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
}
