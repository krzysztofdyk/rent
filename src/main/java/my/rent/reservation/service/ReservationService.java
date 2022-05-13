package my.rent.reservation.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import my.rent.house.entity.HouseEntity;
import my.rent.house.repository.HouseRepository;
import my.rent.landlord.repository.LandlordRepository;
import my.rent.reservation.dto.ReservationRequestDto;
import my.rent.reservation.entity.ReservationEntity;
import my.rent.reservation.repository.ReservationRepository;
import my.rent.tenant.entity.TenantEntity;
import my.rent.tenant.repository.TenantRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;


@AllArgsConstructor
@Service
@Slf4j
public class ReservationService {

    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    LandlordRepository landlordRepository;
    @Autowired
    HouseRepository houseRepository;
    @Autowired
    TenantRepository tenantRepository;


    public ReservationEntity createReservation(ReservationRequestDto reservationRequestDto)  {
        log.info("Reservation: start creation");
        HouseEntity houseEntity = houseRepository.findByName(reservationRequestDto.getHouseName());
        TenantEntity tenantEntity = tenantRepository.findByName(reservationRequestDto.getTenantName());

        checkDates(reservationRequestDto);
        log.info("Reservation: check dates done ");

        ReservationEntity newReservationEntity = new ReservationEntity();
        newReservationEntity.setStartDate(reservationRequestDto.getStartDate());
        newReservationEntity.setEndDate(reservationRequestDto.getEndDate());
        newReservationEntity.setHouseEntity(houseEntity);
        newReservationEntity.setTenantEntity(tenantEntity);
        newReservationEntity.setTotalPrice(countTheTotalPrice(reservationRequestDto.getStartDate(),reservationRequestDto.getEndDate(),houseEntity.getUnitPrice()));
        log.info("Reservation: vales set ");
        reservationRepository.save(newReservationEntity);
        log.info("Reservation: " + newReservationEntity.getId() +  " was finished.");
        return newReservationEntity;
    }

    private void checkDates(ReservationRequestDto reservationRequestDto){
        LocalDate startDate = reservationRequestDto.getStartDate();
        LocalDate endDate = reservationRequestDto.getEndDate();
        HouseEntity houseEntity = houseRepository.findByName(reservationRequestDto.getHouseName());
        checkIfDatesAreChronological(startDate,endDate);
        checkIfStartDateIsNotInThePast(startDate);
        checkIfHouseIsAvailable(startDate,endDate,houseEntity);
    }

    private void checkIfDatesAreChronological(LocalDate from, LocalDate to){
        if (to.isBefore(from)){
            throw new IllegalArgumentException(String.format("End date: %s can't be earlier than start date: %s" , to, from ));
        }
    }
    private void checkIfStartDateIsNotInThePast(LocalDate from){
        if (from.isBefore(LocalDate.now())){
            throw new IllegalArgumentException(String.format("Wrong dates, start date: %s can't be before: %s.", from, LocalDate.now()));
        }
    }

    private void checkIfHouseIsAvailable(LocalDate from, LocalDate to, HouseEntity houseEntity){
        List<ReservationEntity> reservationEntityList = reservationRepository.findAllByHouseEntity(houseEntity.getName());

        boolean check = reservationEntityList.stream()
                .map(reservationEntity -> from.isAfter(reservationEntity.getStartDate()) && from.isBefore(reservationEntity.getEndDate())
                && to.isAfter(reservationEntity.getStartDate()) && to.isBefore(reservationEntity.getEndDate()))
                .anyMatch(result-> result.equals(Boolean.TRUE));

        if(check){
            throw new IllegalArgumentException("Reservation dates are overlapping") ;
        }
    }

    public ReservationEntity findReservationByHouse(String name){
        if (name != null && !name.isBlank()) {
            return reservationRepository.findByHouseEntity(name);
        } return null;
    }

    public ReservationEntity findReservationByTenant(String name){
        if (name != null && !name.isBlank()) {
            return reservationRepository.findByTenantEntity(name);
        } return null;
    }

    public ReservationEntity updateReservation(Long id, ReservationRequestDto reservationRequestDto) {
        ReservationEntity reservationEntity = reservationRepository.getById(id);
        HouseEntity houseEntity = houseRepository.findByName(reservationRequestDto.getHouseName());
        TenantEntity tenantEntity = tenantRepository.findByName(reservationRequestDto.getTenantName());

        reservationEntity.setHouseEntity(houseEntity);
        reservationEntity.setTenantEntity(tenantEntity);
        reservationEntity.setStartDate(reservationRequestDto.getStartDate());
        reservationEntity.setEndDate(reservationRequestDto.getEndDate());
        log.info("Reservation: " + reservationEntity.getId() + " was updated");
        return reservationEntity;
    }

    public void deleteReservation(Long id) {
        ReservationEntity reservationEntity = reservationRepository.getById(id);
        reservationRepository.delete(reservationEntity);
        log.info("Reservation: " + reservationEntity.getId() + " was deleted");
    }

    private ReservationEntity mapDtoToEntity(ReservationRequestDto reservationRequestDto) {
        HouseEntity houseEntity = houseRepository.findByName(reservationRequestDto.getHouseName());
        TenantEntity tenantEntity = tenantRepository.findByName(reservationRequestDto.getTenantName());
        LocalDate startDate = reservationRequestDto.getStartDate();
        LocalDate endDate = reservationRequestDto.getEndDate();
        return  ReservationEntity.builder()
                .startDate(reservationRequestDto.getStartDate())
                .endDate(reservationRequestDto.getEndDate())
                .tenantEntity(tenantEntity)
                .houseEntity(houseEntity)
                .totalPrice(countTheTotalPrice(startDate,endDate,houseEntity.getUnitPrice()))
                .build();
    }

    public ReservationRequestDto mapEntityToDto(ReservationEntity reservationEntity) {
        return ReservationRequestDto.builder()
                .houseName(reservationEntity.getHouseEntity().getName())
                .startDate(reservationEntity.getStartDate())
                .endDate(reservationEntity.getEndDate())
                .tenantName(reservationEntity.getTenantEntity().getName())
                .build();
    }

    public List<ReservationRequestDto> mapEntityToDtoList(List<ReservationEntity> reservationEntities) {
        return reservationEntities.stream()
                .map(this::mapEntityToDto)
                .collect(Collectors.toList());
    }

    private Long countTheTotalPrice (LocalDate from,LocalDate to, long unitPrice){
        long amountOfDays = ChronoUnit.DAYS.between(from,to);
        return amountOfDays*unitPrice;
    }
}
