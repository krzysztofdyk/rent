package my.rent.tenant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import my.rent.reservation.entity.ReservationEntity;

import javax.persistence.*;

@Builder
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TenantEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToOne(mappedBy = "tenantEntity")
    private ReservationEntity reservationEntity;



}
