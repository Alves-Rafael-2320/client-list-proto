package com.artbox.clientlist.controller;


import com.artbox.clientlist.model.Client;
import com.artbox.clientlist.repository.ClientRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/findAllClients")
    public List<Client> findAllClients(){
        return clientRepository.findAll(Sort.by(Sort.Order.asc("name")));
    }

    @GetMapping("/findByName")
    public List<Client> findByName(@RequestParam String name){
        return clientRepository.findByNameContainingIgnoreCase(name);
    }

    @GetMapping("/findByEmail")
    public List<Client> findByEmail(@RequestParam String email){
        return clientRepository.findByEmailContainingIgnoreCase(email);
    }

    @GetMapping("/findByPhone")
    public List<Client> findByPhone(@RequestParam String phone){
        return clientRepository.findByPhoneContainingIgnoreCase(phone);
    }


    @GetMapping("/{id}")
    public Client findClientById(@PathVariable Long id){
        Optional<Client> client = clientRepository.findById(id);
        return client.orElse(null);
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
        clientRepository.save(client);
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
        Client client = clientRepository.findById(id).orElse(null);

        if (client != null){
            client.setName(clientDetails.getName());
            client.setEmail(clientDetails.getEmail());
            client.setPhone(clientDetails.getPhone());
            client.setAdress(clientDetails.getAdress());

            clientRepository.save(client);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id){
        if(clientRepository.existsById(id)){
            clientRepository.deleteById(id);
            return ResponseEntity.ok("Client Deleted.");
        }
        return ResponseEntity.notFound().build();
    }


}
