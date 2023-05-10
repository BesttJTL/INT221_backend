package sit.int221.sasprojectkk2.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Entity
@Table(name = "category")
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    @Id
    private Integer categoryId;
    private String categoryName;
}
