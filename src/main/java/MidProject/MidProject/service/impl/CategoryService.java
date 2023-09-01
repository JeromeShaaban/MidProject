package MidProject.MidProject.service.impl;

import MidProject.MidProject.dto.*;
import MidProject.MidProject.exception.BusinessException;
import MidProject.MidProject.exception.NotFoundException;
import MidProject.MidProject.persistence.entity.Category;
import MidProject.MidProject.persistence.repository.ICategoryRepository;
import MidProject.MidProject.service.ICategoryService;
import MidProject.MidProject.utils.DTOHelper;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional public class CategoryService implements ICategoryService {

    private final ICategoryRepository iCategoryRepository;
    private final DTOHelper dtoHelper;

    public CategoryService(ICategoryRepository iCategoryRepository, DTOHelper dtoHelper) {
        this.iCategoryRepository = iCategoryRepository;
        this.dtoHelper = dtoHelper;
    }

    @Override
    public List<DTOCategoryListItem> findAll() {
        return iCategoryRepository.findAll()
                .stream()
                .map(dtoHelper::toCategoryListItem)
                .toList();
    }

    @Override
    public DTOCategoryItem findById(long id) {
        Category cat = iCategoryRepository.findById(id).orElseThrow(() -> new NotFoundException("No corresponding category found with the id " + id + " !!" ));
        return dtoHelper.toCategoryItem(cat);
    }

    @Override
    public DTOCategoryItem createCategory(DTOCategoryCreation newCategory) {

        Category entity = new Category();

        entity.setId(newCategory.getId());
        entity.setLabel(newCategory.getLabel());

        try {
            entity = iCategoryRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Cette catégorie existe déjà !");
        }
        return new DTOCategoryItem(entity.getId(), entity.getLabel());
    }
}
