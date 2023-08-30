package MidProject.MidProject.service;

import MidProject.MidProject.dto.DTOCategoryItem;
import MidProject.MidProject.dto.DTOCategoryListItem;

import java.util.List;

public interface ICategoryService {

    List<DTOCategoryListItem> findAll();

    DTOCategoryItem findById(long id);
}
