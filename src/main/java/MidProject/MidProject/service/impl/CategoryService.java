package MidProject.MidProject.service.impl;

import MidProject.MidProject.dto.DTOCategoryItem;
import MidProject.MidProject.dto.DTOCategoryListItem;
import MidProject.MidProject.exception.NotFoundException;
import MidProject.MidProject.peristence.entity.Category;
import MidProject.MidProject.peristence.repository.ICategoryRepository;
import MidProject.MidProject.service.ICategoryService;
import MidProject.MidProject.utils.DTOHelper;
import jakarta.transaction.Transactional;
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
}
