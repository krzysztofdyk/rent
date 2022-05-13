package my.rent.reservation.controller;

import my.rent.reservation.dto.ReservationRequestDto;
import my.rent.reservation.entity.ReservationEntity;
import my.rent.reservation.repository.ReservationRepository;
import my.rent.reservation.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ReservationController {

    @Autowired
    ReservationService reservationService;

    @Autowired
    ReservationRepository reservationRepository;

    @PostMapping("/reservations")
    @ResponseStatus(HttpStatus.CREATED)
    public HttpStatus createReservation(@RequestBody ReservationRequestDto reservationRequestDto){
        ReservationEntity reservationEntity = reservationService.createReservation(reservationRequestDto);
        return HttpStatus.CREATED;
    }

    @GetMapping("/reservations")
    @ResponseStatus(HttpStatus.OK)
    public List<ReservationRequestDto> findAllReservations(){
        List<ReservationEntity> reservationEntities = reservationRepository.findAll();
        return reservationService.mapEntityToDtoList(reservationEntities);
    }

    @GetMapping("/reservations/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationRequestDto findReservation(@PathVariable Long id){
        ReservationEntity reservationEntity = reservationRepository.getById(id);
        return reservationService.mapEntityToDto(reservationEntity);
    }

    @GetMapping("/reservations/houses/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationRequestDto findReservationByHouse(@PathVariable String name){
        ReservationEntity reservationEntity = reservationRepository.findByHouseEntity(name);
        return reservationService.mapEntityToDto(reservationEntity);
    }

    @GetMapping("/reservations/tenant/{name}")
    @ResponseStatus(HttpStatus.OK)
    public ReservationRequestDto findReservationByTenant(@PathVariable String name){
        ReservationEntity reservationEntity = reservationRepository.findByTenantEntity(name);
        return reservationService.mapEntityToDto(reservationEntity);
    }

    @PutMapping("/reservations/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ReservationRequestDto updateReservation(@PathVariable Long id, @RequestBody ReservationRequestDto reservationRequestDto){
        ReservationEntity reservationEntity = reservationService.updateReservation(id,reservationRequestDto);
        return reservationService.mapEntityToDto(reservationEntity);
    }

    @DeleteMapping("/reservations/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HttpStatus deleteReservation(@PathVariable Long id){
        reservationService.deleteReservation(id);
        return HttpStatus.NO_CONTENT;
    }

}
