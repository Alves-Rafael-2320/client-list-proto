package com.artbox.clientlist.controller;


import com.artbox.clientlist.dto.ClientDTO;
import com.artbox.clientlist.model.Client;
import com.artbox.clientlist.repository.ClientRepository;
import com.artbox.clientlist.service.ClientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.PublicKey;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;


    @GetMapping("/searchClients")
    public Page<ClientDTO> searchClients(@RequestParam(required = false) String name,
                                         @RequestParam(required = false) String email,
                                         @RequestParam(required = false) String phone,
                                         @RequestParam (defaultValue = "0") int page,
                                         @RequestParam (defaultValue = "10") int size,
                                         @RequestParam (defaultValue = "name") String sortBy,
                                         @RequestParam (defaultValue = "asc") String direction
                                         ){
        return clientService.searchClients(name, email, phone, page, size, sortBy, direction);
    }
    @GetMapping("/pagedClients")
    public Page<Client> findPagedClients(
            @RequestParam(defaultValue = "0"    ) int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ){
        Sort sort = direction.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return clientService.getPagedClients(pageable);
    }










    @GetMapping("/{id}")
    public ResponseEntity<ClientDTO> findCLientById(@PathVariable Long id){
        return clientService.findClientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    /* Old save method, new method with validation added instead
    @PostMapping
    public Client createClient(@RequestBody Client client){
        return clientRepository.save(client);
    }
    */

    @PostMapping
    public ResponseEntity<?> createClient(@Valid @RequestBody Client client, BindingResult result){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        clientService.createClient(client);
        return ResponseEntity.ok(client);
    }
    /*
    Old update method, new method with validation added instead
    @PutMapping("/{id}")
    public Client updateClient(@PathVariable Long id, @RequestBody Client clientDetails){
        Client client = clientRepository.findById(id).orElse(null);

        if (client != null){
            client.setName(clientDetails.getName());
            client.setEmail(clientDetails.getEmail());
            client.setPhone(clientDetails.getPhone());
            client.setAdress(clientDetails.getAdress());
            return clientRepository.save(client);

        }
        return null;
    }
    */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateClient(@Valid @RequestBody Client clientDetails, BindingResult result, @PathVariable Long id){
        if (result.hasErrors()){
            return ResponseEntity.badRequest().body(result.getAllErrors());
        }
        return clientService.updateClient(id, clientDetails)
                ? ResponseEntity.noContent().build():
                ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClient(@PathVariable Long id){
        return clientService.deleteClient(id)
                ?ResponseEntity.noContent().build()
                :ResponseEntity.notFound().build();
    }


}
