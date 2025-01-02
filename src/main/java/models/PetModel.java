package models;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Getter
@Setter
public class PetModel {
    private int id;
    private CommonModel category;
    private String name;
    private List<String> photoUrls;
    private List<CommonModel> tags;
    private String status;

    @Override
    public String toString() {
        return "PetModel{" +
                "id=" + id +
                ", category=" + category +
                ", name='" + name + '\'' +
                ", photoUrls=" + photoUrls +
                ", tags=" + tags +
                ", status='" + status + '\'' +
                '}';
    }
}
