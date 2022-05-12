package my.rent.house.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class HouseRequestDto {
    private String name;
    private Long unitPrice;
    private Long area;
    private String description;
    private String landLordName;
}
