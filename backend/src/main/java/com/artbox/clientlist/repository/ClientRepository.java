package com.artbox.clientlist.repository;

import com.artbox.clientlist.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long> {

    Page<Client> findAll(Pageable pageable);

    List<Client> findAll(Sort sort);
    List<Client> findByNameContainingIgnoreCase(String name);
    List<Client> findByEmailContainingIgnoreCase(String email);
    List<Client> findByPhoneContainingIgnoreCase(String phone);

}
