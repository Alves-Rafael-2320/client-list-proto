package com.artbox.clientlist.controller;


import com.artbox.clientlist.model.Client;
import com.artbox.clientlist.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientRepository clientRepository;

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
    public List<Client> findBy(@RequestParam String phone){
        return clientRepository.findByPhoneContainingIgnoreCase(phone);
    }


    @GetMapping("/{id}")
    public Client getClientById(@PathVariable Long id){
        Optional<Client> client = clientRepository.findById(id);
        return client.orElse(null);
    }

    @PostMapping
    public Client createClient(@RequestBody Client client){
        return clientRepository.save(client);
    }

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

    @DeleteMapping("/{id}")
    public void deleteClient(@PathVariable Long id){
        clientRepository.deleteById(id);
    }


}
