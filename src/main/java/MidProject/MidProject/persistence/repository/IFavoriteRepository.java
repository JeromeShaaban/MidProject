package MidProject.MidProject.persistence.repository;

import MidProject.MidProject.persistence.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFavoriteRepository extends JpaRepository<Favorite, Long> {
}
