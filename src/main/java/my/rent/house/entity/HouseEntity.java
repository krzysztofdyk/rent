package my.rent.house.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.rent.landlord.entity.LandlordEntity;
import my.rent.reservation.entity.ReservationEntity;

import javax.persistence.*;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class HouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private Long unitPrice;
    private Long area;
    private String description;

    @OneToOne
    @JoinColumn (name = "landlord_entity_id")
    private LandlordEntity landlordEntity;

    @JsonIgnore
    @OneToOne (mappedBy = "houseEntity")
    private ReservationEntity reservationEntity;
}
