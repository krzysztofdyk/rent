package my.rent.reservation.repository;

import my.rent.reservation.entity.ReservationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<ReservationEntity,Long> {
    ReservationEntity findByHouseEntity(String name);
    ReservationEntity findByTenantEntity(String name);

    List<ReservationEntity> findAllByHouseEntity(String name);


}
