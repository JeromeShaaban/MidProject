package MidProject.MidProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;

@Data
@AllArgsConstructor
public class DTOFavoriteListItem {
    private Long id;
    private String name;
    private String link;
    private Date update;
    private DTOCategoryItem categoryDto;
}
