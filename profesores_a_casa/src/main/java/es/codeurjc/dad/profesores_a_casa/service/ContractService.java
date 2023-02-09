package es.codeurjc.dad.profesores_a_casa.service;

import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import es.codeurjc.dad.profesores_a_casa.model.Contract;
import es.codeurjc.dad.profesores_a_casa.repository.ContractRepository;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@Service
public class ContractService {
    
    @Autowired
    private ContractRepository contracts;

    @PostConstruct
    public void init(){

    }

    public List<Contract> getContracts(){
        return contracts.findAll();
    }

    public ResponseEntity<Contract> getById(long id){
        Optional<Contract> contract = contracts.findById(id);
        if(contract.isPresent()) return ResponseEntity.ok(contract.get());
        else return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Contract> create(Contract contract,URI location){
        contracts.save(contract);
        return ResponseEntity.created(location).body(contract);
    }

    public ResponseEntity<Contract> update(long id,Contract upContract){
        Optional<Contract> contract=contracts.findById(id);
        if(contract.isPresent()){
            upContract.setId(id);
            contracts.save(upContract);
            return ResponseEntity.ok(upContract);
        }else return ResponseEntity.notFound().build();
    }

    public ResponseEntity<Contract> delete(long id){
        Optional<Contract> contract=contracts.findById(id);
        if(contract.isPresent()){
            contracts.deleteById(id);
            return ResponseEntity.ok(contract.get());
        }else return ResponseEntity.notFound().build();
    }
}
