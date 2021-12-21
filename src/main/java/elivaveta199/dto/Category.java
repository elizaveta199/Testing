
package elivaveta199.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data

public class Category {

    @JsonProperty("id")
    public Integer id;
    @JsonProperty("products")
    public List<Product> products = new ArrayList<Product>();
    @JsonProperty("title")
    public String title;

}