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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class ClientService {

    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Autowired
    private ClientRepository clientRepository;

    private ClientDTO mapToDTO(Client client){
        return new ClientDTO(client.getName(),client.getEmail(),client.getPhone());
    }

    // Custom CRUD Methods
    public Page<Client> getPagedClients(Pageable pageable){
        logger.info("Fetching client page");

        return clientRepository.findAll(pageable);
    }

    public List<ClientDTO> findAllClients(){
        logger.info("Fetching all clients from the database");

        return clientRepository.findAll(Sort.by(Sort.Order.asc("name")))
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<ClientDTO> findByName(String name){
        logger.info("Fetching client by name: {}", name);
        List<Client> client = clientRepository.findByNameContainingIgnoreCase(name);

        if (client.isEmpty()){
            logger.warn("No clients found with name: {}", name);
        }

        return clientRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<ClientDTO> findByPhone(String phone){
        logger.info("Fetching client by phone: {}", phone);
        List<Client> client = clientRepository.findByPhoneContainingIgnoreCase(phone);

        if (client.isEmpty()){
            logger.warn("No clients found with phone: {}", phone);
        }

        return clientRepository.findByPhoneContainingIgnoreCase(phone)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }

    public List<ClientDTO> findByEmail(String email){
        logger.info("Fetching client by email: {}", email);

        List<Client> client = clientRepository.findByEmailContainingIgnoreCase(email);

        if (client.isEmpty()){
            logger.warn("No clients found with email: {}", email);
        }
        return clientRepository.findByEmailContainingIgnoreCase(email)
                .stream()
                .map(this::mapToDTO)
                .toList();
    }



    //Jpa CRUD methods
    public Optional<ClientDTO> findClientById(Long id){
        logger.info("Fetching client by ID: {}", id);
        Optional<Client> client = clientRepository.findById(id);

        if (client.isEmpty()){
            logger.warn("Client with id {} was not found", id);
        }
        return client.map(this::mapToDTO);
    }

    public void createClient(Client client){
        logger.info("Creating a new client: {}", client.getName());
        clientRepository.save(client);
        logger.info("Client successfully saved with ID: {}", client.getId());
    }

    public boolean updateClient(Long id, Client clientDetails){
        logger.info("Updating client with ID: {}", id);
        Optional<Client> optionalClient = clientRepository.findById(id);
        if (optionalClient.isPresent()){
            Client client = optionalClient.get();
            client.setName(clientDetails.getName());
            client.setEmail(clientDetails.getEmail());
            client.setPhone(clientDetails.getPhone());
            client.setAdress(clientDetails.getAdress());
            clientRepository.save(client);

            logger.info("Client with ID: {} successfully updated", id);
            return true;
        }
        logger.warn("Client with ID: {} not found for update", id);
        return false;
    }

    public boolean deleteClient(Long id){
        if (clientRepository.existsById(id)){
            logger.info("Deleting client with ID: {}", id);
            clientRepository.deleteById(id);
            logger.info("Client with ID: {} successfully", id);
            return true;
        }
        logger.warn("Client with ID: {} not found for deletion", id);
        return false;
    }

}
