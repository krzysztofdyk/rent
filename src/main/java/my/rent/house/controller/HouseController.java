package my.rent.house.controller;

import my.rent.house.dto.HouseRequestDto;
import my.rent.house.entity.HouseEntity;
import my.rent.house.service.HouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class HouseController {

    @Autowired
    HouseService houseService;

    @PostMapping("/houses")
    @ResponseStatus(HttpStatus.CREATED)
    public HttpStatus createHouse(@RequestBody HouseRequestDto houseRequestDto){
        HouseEntity userEntity = houseService.createHouse(houseRequestDto);
        return HttpStatus.CREATED;
    }

    @GetMapping ("/houses")
    @ResponseStatus(HttpStatus.OK)
    public List<HouseRequestDto> findAllHouses(){
        return houseService.findAllHouses();
    }

    @PutMapping("/houses/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus updateHouse(@PathVariable Long id, @RequestBody HouseRequestDto houseRequestDto) {
        houseService.updateHouse(id, houseRequestDto);
        return HttpStatus.OK;
    }

    @DeleteMapping("/houses/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HttpStatus deleteHouse(@PathVariable Long id) {
        houseService.deleteHouse(id);
        return HttpStatus.NO_CONTENT;
    }

}
