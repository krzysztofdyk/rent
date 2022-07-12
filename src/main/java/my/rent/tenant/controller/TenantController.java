package my.rent.tenant.controller;

import lombok.AllArgsConstructor;
import my.rent.tenant.dto.TenantRequestDto;
import my.rent.tenant.entity.TenantEntity;
import my.rent.tenant.service.TenantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class TenantController {

    private final TenantService tenantService;

    @PostMapping("/tenants")
    @ResponseStatus(HttpStatus.CREATED)
    public HttpStatus createTenant(@RequestBody TenantRequestDto tenantRequestDto){
        TenantEntity tenantEntity = tenantService.createTenant(tenantRequestDto);
        return HttpStatus.CREATED;
    }

    @GetMapping("/tenants")
    @ResponseStatus(HttpStatus.OK)
    public List<TenantRequestDto> findAllTenants(){
        return tenantService.findAllTenants();
    }

    @PutMapping("/tenants/{id}")
    @ResponseStatus(HttpStatus.OK)
    public HttpStatus updateTenant(@PathVariable Long id, @RequestBody TenantRequestDto tenantRequestDto) {
        tenantService.updateTenant(id, tenantRequestDto);
        return HttpStatus.OK;
    }

    @DeleteMapping("/tenants/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public HttpStatus deleteTenant(@PathVariable Long id) {
        tenantService.deleteTenant(id);
        return HttpStatus.NO_CONTENT;
    }

}
