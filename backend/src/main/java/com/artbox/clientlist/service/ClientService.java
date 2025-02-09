package com.artbox.clientlist.service;

import com.artbox.clientlist.dto.ClientDTO;
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

    private ClientDTO mapToDTO(Client client){
        return new ClientDTO(client.getName(),client.getEmail(),client.getPhone());
    }

    // Custom CRUD Methods
    public Page<Client> getPagedClients(Pageable pageable){
        return clientRepository.findAll(pageable);
    }

    public List<ClientDTO> findAllClients(){
        return clientRepository.findAll(Sort.by(Sort.Order.asc("name")))
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<ClientDTO> findByName(String name){
        return clientRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<ClientDTO> findByPhone(String phone){
        return clientRepository.findByPhoneContainingIgnoreCase(phone)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<ClientDTO> findByEmail(String email){
        return clientRepository.findByEmailContainingIgnoreCase(email)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }



    //Jpa CRUD methods
    public Optional<ClientDTO> findClientById(Long id){
        return clientRepository.findById(id).map(this::mapToDTO);
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
