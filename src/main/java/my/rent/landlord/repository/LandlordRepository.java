package my.rent.landlord.repository;

import my.rent.landlord.entity.LandlordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LandlordRepository extends JpaRepository<LandlordEntity,Long> {
    LandlordEntity getByName(String landLordName);
}
