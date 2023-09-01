package MidProject.MidProject.controller;

import MidProject.MidProject.dto.*;
import MidProject.MidProject.service.ICategoryService;
import MidProject.MidProject.service.IFavoriteService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/api/category")
public class CategoryController {

    private final ICategoryService iCategoryService;

    private final IFavoriteService iFavoriteService;

    public CategoryController(ICategoryService categoryService, IFavoriteService favoriteService, IFavoriteService iFavoriteService) {
        this.iCategoryService = categoryService;
        this.iFavoriteService = iFavoriteService;
    }

    @GetMapping(path = "/{id}/favorites")
    public List<DTOFavoriteItem> findByCategory(@PathVariable long id) {
        return iFavoriteService.findByCategory(id);
    }

    @GetMapping(path = "/{id}")
    public DTOCategoryItem findById(@PathVariable long id) {
        return iCategoryService.findById(id);
    }

    @GetMapping(path = "/all")
    public List<DTOCategoryListItem> findAll() {
        return iCategoryService.findAll();
    }

    @PostMapping(path = "/{id}/favorites")
    public DTOFavoriteItem createFavorite(@PathVariable(name = "id") long categoryId, @RequestBody DTOFavoriteCreation newFavorite) {
        return iFavoriteService.createFavorite(categoryId, newFavorite);
    }

    @PostMapping(path = "/all")
    public DTOCategoryItem createCategory(@RequestBody DTOCategoryCreation newCategory) {
        return iCategoryService.createCategory(newCategory);
    }

}
