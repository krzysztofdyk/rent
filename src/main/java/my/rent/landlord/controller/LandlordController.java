package my.rent.landlord.controller;

import lombok.AllArgsConstructor;
import my.rent.landlord.dto.LandlordRequestDto;
import my.rent.landlord.entity.LandlordEntity;
import my.rent.landlord.service.LandlordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class LandlordController {

    private final LandlordService landlordService;

    @PostMapping("/landlords")
    @ResponseStatus(HttpStatus.CREATED)
    public HttpStatus createLandlord(@RequestBody LandlordRequestDto houseRequestDto){
        LandlordEntity landlordEntity = landlordService.createLandlord(houseRequestDto);
        return HttpStatus.CREATED;
    }

    @GetMapping ("/landlords")
    @ResponseStatus(HttpStatus.OK)
    public List<LandlordRequestDto> findAllLandlords(){
        return landlordService.findAllLandlords();
    }

    @PutMapping("/landlord/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus updateLandlord(@PathVariable Long id, @RequestBody LandlordRequestDto houseRequestDto) {
        landlordService.updateLandlord(id, houseRequestDto);
        return HttpStatus.OK;
    }

    @DeleteMapping("/landlords/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HttpStatus deleteLandlord(@PathVariable Long id) {
        landlordService.deleteLandlord(id);
        return HttpStatus.NO_CONTENT;
    }

}
