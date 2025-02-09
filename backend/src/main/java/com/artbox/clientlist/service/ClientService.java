package com.artbox.clientlist.service;

import com.artbox.clientlist.model.Client;
import com.artbox.clientlist.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;


    // Custom CRUD Methods
    public Page<Client> getPagedClients(Pageable pageable){
        return clientRepository.findAll(pageable);
    }

    public List<Client> findAllClients(){
        return clientRepository.findAll(Sort.by(Sort.Order.asc("name")));
    }

    public List<Client> findByName(String name){
        return clientRepository.findByNameContainingIgnoreCase(name);
    }

    public List<Client> findByPhone(String phone){
        return clientRepository.findByPhoneContainingIgnoreCase(phone);
    }

    public List<Client> findByEmail(String email){
        return clientRepository.findByEmailContainingIgnoreCase(email);
    }



    //Jpa CRUD methods
    public Optional<Client> findClientById(Long id){
        return clientRepository.findById(id);
    }

    public void createClient(Client client){
        clientRepository.save(client);
    }

    public boolean updateClient(Long id, Client clientDetails){
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()){
            Client client = optionalClient.get();
            client.setName(clientDetails.getName());
            client.setEmail(clientDetails.getEmail());
            client.setPhone(clientDetails.getPhone());
            client.setAdress(clientDetails.getAdress());
            clientRepository.save(client);
            return true;
        }
        return false;
    }

    public boolean deleteClient(Long id){
        if (clientRepository.existsById(id)){
            clientRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
