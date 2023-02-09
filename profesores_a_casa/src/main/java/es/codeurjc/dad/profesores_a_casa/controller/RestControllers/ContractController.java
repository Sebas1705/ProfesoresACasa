package es.codeurjc.dad.profesores_a_casa.controller.RestControllers;

import org.springframework.beans.factory.annotation.*;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.codeurjc.dad.profesores_a_casa.model.Contract;
import es.codeurjc.dad.profesores_a_casa.service.ContractService;
import java.util.*;

@RestController
@RequestMapping("/AppAPI/Contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;

    @GetMapping("/")
    public List<Contract> getAll(){
        return contractService.getContracts();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Contract> getContractById(@PathVariable long id){
        return contractService.getById(id);
    }

    @PostMapping("/")
    public ResponseEntity<Contract> createContract(@RequestBody Contract contract){
        return contractService.create(contract,
        ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
        .buildAndExpand(contract.getId()).toUri());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Contract> replaceContract(@PathVariable long id,@RequestBody Contract contract){
        return contractService.update(id,contract);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Contract> deleteContract(@PathVariable long id){
        return contractService.delete(id);
    }
}