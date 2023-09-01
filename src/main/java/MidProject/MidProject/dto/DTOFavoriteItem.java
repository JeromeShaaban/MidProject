package MidProject.MidProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOFavoriteItem {
    private long id;
    private String name;
    private String link;
    private Date date;
    private DTOCategoryItem categoryDto;
}
