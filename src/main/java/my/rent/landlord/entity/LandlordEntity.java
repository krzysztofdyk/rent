package my.rent.landlord.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.rent.house.entity.HouseEntity;

import javax.persistence.*;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LandlordEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToOne
    private HouseEntity houseEntity;


}
