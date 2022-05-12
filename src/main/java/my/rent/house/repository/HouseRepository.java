package my.rent.house.repository;

import my.rent.house.entity.HouseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseRepository extends JpaRepository<HouseEntity,Long> {
    HouseEntity findByName(String house);

}
