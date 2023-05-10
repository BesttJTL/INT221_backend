package sit.int221.sasprojectkk2.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import net.minidev.json.annotate.JsonIgnore;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PageDTO<T> {
    private List<T> content;
    private Boolean last;
    private Boolean first;
    private Integer totalPages;
    private Integer totalElements;
    private Integer size;

    @JsonIgnore
    private Integer number;
    public Integer getPage() {
        return number;
    }


}
