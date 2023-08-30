package MidProject.MidProject.peristence.repository;

import MidProject.MidProject.peristence.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IFavoriteRepository extends JpaRepository<Favorite, Long> {
}
