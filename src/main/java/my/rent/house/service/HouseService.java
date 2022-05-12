package my.rent.house.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.rent.house.dto.HouseRequestDto;
import my.rent.house.entity.HouseEntity;
import my.rent.house.repository.HouseRepository;
import my.rent.landlord.entity.LandlordEntity;
import my.rent.landlord.repository.LandlordRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
@Slf4j
public class HouseService {

    private final HouseRepository houseRepository;
    private final LandlordRepository landlordRepository;

    public HouseEntity createHouse(HouseRequestDto houseRequestDto){
        HouseEntity houseEntity = mapDtoToEntity(houseRequestDto);
        houseRepository.save(houseEntity);
        log.info("House: " + houseRequestDto.getName() +  " was created.");
        return houseEntity;
    }

    private HouseEntity mapDtoToEntity(HouseRequestDto  houseRequestDto) {
        LandlordEntity landlordEntity = landlordRepository.getByName(houseRequestDto.getLandLordName());
       return  HouseEntity.builder()
                .name(houseRequestDto.getName())
                .unitPrice(houseRequestDto.getUnitPrice())
                .area(houseRequestDto.getArea())
                .description(houseRequestDto.getDescription())
                .landlordEntity(landlordEntity)
                .build();
    }

    public List<HouseRequestDto> findAllHouses() {
        List<HouseEntity> houseEntities = houseRepository.findAll();
        log.info("All houses were provided.");
        return mapEntityToDtoList(houseEntities);
    }

    public List<HouseRequestDto> mapEntityToDtoList(List<HouseEntity> houseEntityList){
        return houseEntityList.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    private HouseRequestDto mapEntityToDto(HouseEntity houseEntity) {
        return HouseRequestDto.builder()
                .name(houseEntity.getName())
                .unitPrice(houseEntity.getUnitPrice())
                .area(houseEntity.getArea())
                .description(houseEntity.getDescription())
                .landLordName(houseEntity.getLandlordEntity().getName())
                .build();
    }

    public void deleteHouse(Long id) {
        HouseEntity houseEntity = houseRepository.getById(id);
        houseRepository.delete(houseEntity);
        log.info("House: " + houseEntity.getName() + " was deleted");
    }

    public HouseEntity updateHouse(Long id, HouseRequestDto houseRequestDto) {
        LandlordEntity landlordEntity = landlordRepository.getByName(houseRequestDto.getLandLordName());
        HouseEntity houseEntity = houseRepository.getById(id);
        houseEntity.setName(houseRequestDto.getName());
        houseEntity.setUnitPrice(houseRequestDto.getUnitPrice());
        houseEntity.setArea(houseRequestDto.getArea());
        houseEntity.setDescription(houseRequestDto.getDescription());
        houseEntity.setLandlordEntity(landlordEntity);
        log.info("House: " + houseEntity.getName() + " was updated");
        return  houseEntity;
    }
}
