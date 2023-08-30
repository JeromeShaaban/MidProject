package MidProject.MidProject.service;

import MidProject.MidProject.dto.DTOFavoriteCreation;
import MidProject.MidProject.dto.DTOFavoriteItem;
import MidProject.MidProject.dto.DTOFavoriteListItem;

import java.util.List;

public interface IFavoriteService {
    List<DTOFavoriteListItem> findAll();

    void deleteMultiple(List<Long> ids);

    List<DTOFavoriteItem> findByCategory(long id);

    void delete(long id);

    DTOFavoriteItem createFavorite(long categoryId, DTOFavoriteCreation newFavorite);
}
