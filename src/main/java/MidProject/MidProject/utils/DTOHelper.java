package MidProject.MidProject.utils;

import MidProject.MidProject.dto.DTOCategoryItem;
import MidProject.MidProject.dto.DTOCategoryListItem;
import MidProject.MidProject.persistence.entity.Category;
import MidProject.MidProject.persistence.repository.ICategoryRepository;
import MidProject.MidProject.persistence.repository.IFavoriteRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DTOHelper {

    @Autowired private IFavoriteRepository iFavoriteRepository;

    @Autowired private ICategoryRepository iCategoryRepository;

    private final ModelMapper mapper = new ModelMapper();

    public DTOCategoryListItem toCategoryListItem(Category entity) {
        DTOCategoryListItem dto = mapper.map(entity, DTOCategoryListItem.class);
        dto.setOccurrences(
               iFavoriteRepository.findAll()
                       .stream()
                       .filter(f -> f.getCategory().getId().equals(entity.getId()))
                       .count()
        );
        return dto;
    }

    public DTOCategoryItem toCategoryItem(Category entity) {
        return mapper.map(entity, DTOCategoryItem.class);
    }


}
