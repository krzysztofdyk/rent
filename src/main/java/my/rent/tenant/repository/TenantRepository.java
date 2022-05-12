package my.rent.tenant.repository;

import my.rent.landlord.entity.LandlordEntity;
import my.rent.tenant.entity.TenantEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TenantRepository extends JpaRepository<TenantEntity,Long> {
    TenantEntity findByName(String name);


}
