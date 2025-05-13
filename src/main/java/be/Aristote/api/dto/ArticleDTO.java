package be.Aristote.api.dto;

import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArticleDTO {

    private String title;
    private String isbn;
    private String description;
    private String imageUrl;
    private Double price;
    private Integer quantity;
    
}