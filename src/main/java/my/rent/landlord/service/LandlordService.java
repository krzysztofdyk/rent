package my.rent.landlord.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.rent.house.dto.HouseRequestDto;
import my.rent.house.entity.HouseEntity;
import my.rent.house.repository.HouseRepository;
import my.rent.landlord.dto.LandlordRequestDto;
import my.rent.landlord.entity.LandlordEntity;
import my.rent.landlord.repository.LandlordRepository;
import my.rent.tenant.dto.TenantRequestDto;
import my.rent.tenant.entity.TenantEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
@Slf4j
public class LandlordService {

    private final LandlordRepository landlordRepository;

    public LandlordEntity createLandlord(LandlordRequestDto landlordRequestDto){
        LandlordEntity landlordEntity = mapDtoToEntity(landlordRequestDto);
        landlordRepository.save(landlordEntity);
        return landlordEntity;
    }

    private LandlordEntity mapDtoToEntity(LandlordRequestDto landlordRequestDto) {
        LandlordEntity landlordEntity = landlordRepository.getByName(landlordRequestDto.getName());
       return  LandlordEntity.builder()
                .name(landlordRequestDto.getName())
                .build();
    }

    public List<LandlordRequestDto> findAllLandlords() {
        List<LandlordEntity> landlordEntities = landlordRepository.findAll();
        return mapEntityToDtoList(landlordEntities);
    }

    public List<LandlordRequestDto> mapEntityToDtoList(List<LandlordEntity> houseEntityList){
        return houseEntityList.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    private LandlordRequestDto mapEntityToDto(LandlordEntity houseEntity) {
        return LandlordRequestDto.builder()
                .name(houseEntity.getName())
                .build();
    }

    public void deleteLandlord(Long id) {
        LandlordEntity landlordEntity = landlordRepository.getById(id);
        landlordRepository.delete(landlordEntity);
    }

    public LandlordEntity updateLandlord(Long id, LandlordRequestDto landlordRequestDto) {
        LandlordEntity landlordEntity = landlordRepository.getById(id);
        landlordEntity.setName(landlordRequestDto.getName());
        return landlordEntity;
    }
}
