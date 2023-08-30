package MidProject.MidProject.peristence.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Date;


@Entity
@Table(name = "favorite", uniqueConstraints = { @UniqueConstraint(name = "UniqueFavoriteLink", columnNames = { "link", "category_id"})})
@Getter
@Setter
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "link")
    private String link;

    @Column(columnDefinition = "date", name = "date")
    private Date date;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id", nullable = false, referencedColumnName = "id", foreignKey = @ForeignKey(name = "FK_CATEGORY_FAVORITE"))
    @Fetch(FetchMode.JOIN)
    private Category category;
}
