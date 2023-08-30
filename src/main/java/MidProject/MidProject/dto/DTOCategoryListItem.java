package MidProject.MidProject.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DTOCategoryListItem {
    private long id;
    private String label;
    private long occurrences;
}
