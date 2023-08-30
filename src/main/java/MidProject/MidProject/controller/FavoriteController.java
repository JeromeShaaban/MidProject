package MidProject.MidProject.controller;

import MidProject.MidProject.dto.DTOFavoriteListItem;
import MidProject.MidProject.service.IFavoriteService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@RequestMapping(path = "/api/favorites")
public class FavoriteController {

    private final IFavoriteService ifavoriteService;

    public FavoriteController(IFavoriteService favoriteService, IFavoriteService ifavoriteService) {
        this.ifavoriteService = ifavoriteService;
    }

    @GetMapping
    public List<DTOFavoriteListItem> findAll() {
        return ifavoriteService.findAll();
    }

    @DeleteMapping(path = "/{ids}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteFavorites(@PathVariable String ids) {
        ifavoriteService.deleteMultiple(
                Arrays.stream(ids.split(",")).map(Long::valueOf).toList()
        );
    }

//    @DeleteMapping(path = "/{id}")
//    @ResponseStatus(code = HttpStatus.OK)
//    public void delete(@PathVariable long id) { ifavoriteService.delete(id);}

}
