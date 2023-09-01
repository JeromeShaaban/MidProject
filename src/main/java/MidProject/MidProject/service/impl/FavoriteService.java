package MidProject.MidProject.service.impl;

import MidProject.MidProject.dto.DTOFavoriteCreation;
import MidProject.MidProject.dto.DTOFavoriteItem;
import MidProject.MidProject.dto.DTOFavoriteListItem;
import MidProject.MidProject.exception.BusinessException;
import MidProject.MidProject.exception.NotFoundException;
import MidProject.MidProject.persistence.entity.Category;
import MidProject.MidProject.persistence.entity.Favorite;
import MidProject.MidProject.persistence.repository.ICategoryRepository;
import MidProject.MidProject.persistence.repository.IFavoriteRepository;
import MidProject.MidProject.service.IFavoriteService;
import MidProject.MidProject.utils.DTOHelper;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class FavoriteService implements IFavoriteService {

    private final IFavoriteRepository iFavoriteRepository;
    private final ICategoryRepository iCategoryRepository;

    private final DTOHelper dtoHelper;
    public FavoriteService(IFavoriteRepository iFavoriteRepository, ICategoryRepository iCategoryRepository, DTOHelper dtoHelper) {
        this.iFavoriteRepository = iFavoriteRepository;
        this.iCategoryRepository = iCategoryRepository;
        this.dtoHelper = dtoHelper;
    }

    @Override
    public List<DTOFavoriteListItem> findAll() {
        return iFavoriteRepository.findAll()
                .stream()
                .map(element -> new DTOFavoriteListItem(
                        element.getId(),
                        element.getName(),
                        element.getLink(),
                        element.getDate(),
                        dtoHelper.toCategoryItem(element.getCategory())))
                .toList();
    }

    @Override
    public List<DTOFavoriteItem> findByCategory(long id) {

       iCategoryRepository.findById(id).orElseThrow(() -> new NotFoundException("No corresponding category found with the id " + id + " !!" ));

       return iFavoriteRepository.findAll()
                .stream()
                .filter(element -> element.getCategory().getId().equals(id))
                .map(element -> new DTOFavoriteItem(element.getId(), element.getName(), element.getLink(), element.getDate(), dtoHelper.toCategoryItem(element.getCategory())))
                .toList();
    }

    @Override
    public void delete(long id) {
        Favorite entity = iFavoriteRepository.findById(id).orElseThrow(() -> new NotFoundException("No corresponding favorite found with the id " + id + " !!" ));
        iFavoriteRepository.delete(entity);
    }

    @Override
    public DTOFavoriteItem createFavorite(long categoryId, DTOFavoriteCreation newFavorite) {
        Category cat = iCategoryRepository.findById(categoryId).orElseThrow(() -> new NotFoundException("No corresponding category found with the id " + categoryId + " !!" ));
        Favorite entity = new Favorite();

        if (!isUrlValid(newFavorite.getLink())) {
            throw new BusinessException("URL non valide !");
        }

        entity.setCategory(cat);
        entity.setId(newFavorite.getId());
        entity.setName(newFavorite.getName());
        entity.setLink(newFavorite.getLink());
        entity.setDate(new Date());

        try {
            entity = iFavoriteRepository.save(entity);
        } catch (DataIntegrityViolationException e) {
            throw new BusinessException("Ce lien existe déjà dans cette catégorie !");
        }
        return new DTOFavoriteItem(entity.getId(), entity.getName(), entity.getLink(), entity.getDate(), dtoHelper.toCategoryItem(entity.getCategory()));
    }

    @Override
    public void deleteMultiple(List<Long> ids) {
        ids.forEach(id -> delete(id));
    }


    private boolean isUrlValid(String link){
        try {
            URL url = new URL(link);
            HttpURLConnection huc = (HttpURLConnection) url.openConnection();
            int responseCode = huc.getResponseCode();
            return responseCode == 200;
        } catch (ProtocolException e) {
            return true;
        } catch (Exception e) {
            return false;
        }
    }


}
