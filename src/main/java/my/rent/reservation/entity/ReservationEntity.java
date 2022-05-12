package my.rent.reservation.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.rent.house.entity.HouseEntity;
import my.rent.landlord.entity.LandlordEntity;
import my.rent.tenant.entity.TenantEntity;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReservationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToOne
    @JoinColumn (name = "house_entity_id")
    private HouseEntity houseEntity;

    private LocalDate startDate;
    private LocalDate endDate;

    @OneToOne
    @JoinColumn (name = "tenant_entity_id")
    private TenantEntity tenantEntity;

    private Long totalPrice;

}
