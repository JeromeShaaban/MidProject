package MidProject.MidProject.persistence.repository;

import MidProject.MidProject.persistence.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICategoryRepository extends JpaRepository<Category, Long> {
}
