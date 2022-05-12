package my.rent.tenant.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.rent.landlord.dto.LandlordRequestDto;
import my.rent.landlord.entity.LandlordEntity;
import my.rent.landlord.repository.LandlordRepository;
import my.rent.tenant.dto.TenantRequestDto;
import my.rent.tenant.entity.TenantEntity;
import my.rent.tenant.repository.TenantRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
@Slf4j
public class TenantService {

    private final TenantRepository tenantRepository;

    public TenantEntity createTenant(TenantRequestDto tenantRequestDto){
        TenantEntity tenantEntity = mapDtoToEntity(tenantRequestDto);
        tenantRepository.save(tenantEntity);
        return tenantEntity;
    }

    private TenantEntity mapDtoToEntity(TenantRequestDto tenantRequestDto) {
        TenantEntity tenantEntity = tenantRepository.findByName(tenantRequestDto.getName());
        return  TenantEntity.builder()
                .name(tenantRequestDto.getName())
                .build();
    }

    public List<TenantRequestDto> findAllTenants() {
        List<TenantEntity> tenantEntities = tenantRepository.findAll();
        return mapEntityToDtoList(tenantEntities);
    }

    public List<TenantRequestDto> mapEntityToDtoList(List<TenantEntity> tenantEntityList){
        return tenantEntityList.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    private TenantRequestDto mapEntityToDto(TenantEntity tenantEntity) {
        return TenantRequestDto.builder()
                .name(tenantEntity.getName())
                .build();
    }

    public void deleteTenant(Long id) {
        TenantEntity tenantEntity = tenantRepository.getById(id);
        tenantRepository.delete(tenantEntity);
    }

    public TenantEntity updateTenant(Long id, TenantRequestDto tenantRequestDto) {
        TenantEntity landlordEntity = tenantRepository.getById(id);
        landlordEntity.setName(tenantRequestDto.getName());
        return landlordEntity;
    }


}
