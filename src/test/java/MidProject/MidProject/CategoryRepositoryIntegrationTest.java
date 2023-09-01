package MidProject.MidProject;

import MidProject.MidProject.persistence.entity.Category;
import MidProject.MidProject.persistence.repository.ICategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@DataJpaTest
public class CategoryRepositoryIntegrationTest {

	@Autowired
	private TestEntityManager entityManager;

	@Autowired
	private ICategoryRepository iCategoryRepository;

	@Test
	public void givenSetOfCategories_whenFindAll_thenReturnAllCategories() {
		String[] labels = new String[] {"Cat 1", "Cat 2", "Cat 3"};
		Arrays.asList(labels).forEach(l -> entityManager.persist(new Category(null, l)));
		entityManager.flush();

		List<Category> categories = iCategoryRepository.findAll();

		assertThat(categories).hasSize(3).extracting(Category::getLabel).containsOnly(labels);
	}

	@Test
	public void givenSetOfCategories_whenSavingDuplicateLabel_thenThrowsException() {
		String[] labels = new String[] {"Cat 1", "Cat 2", "Cat 3"};
		Arrays.asList(labels).forEach(l -> entityManager.persist(new Category(null, l)));
		entityManager.flush();

		assertThatThrownBy(() -> iCategoryRepository.save(new Category(null, "Cat 2"))).isInstanceOf(DataIntegrityViolationException.class).hasMessageContaining("UNIQUECATEGORYLABEL");
	}
}