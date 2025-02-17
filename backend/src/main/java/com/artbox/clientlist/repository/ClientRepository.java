package com.artbox.clientlist.repository;

import com.artbox.clientlist.model.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;



public interface ClientRepository extends JpaRepository<Client, Long> {

    Page<Client> findAll(Pageable pageable);

    List<Client> findByNameContainingIgnoreCase(String name);
    List<Client> findByEmailContainingIgnoreCase(String email);
    List<Client> findByPhoneContainingIgnoreCase(String phone);

    @Query("SELECT c FROM Client c WHERE " +
            "(:name IS NULL OR LOWER(c.name) LIKE LOWER(CONCAT('%', :name, '%'))) AND" +
            "(:email IS NULL OR LOWER(c.email) LIKE LOWER(CONCAT('%', :email,'%'))) AND" +
            "(:phone IS NULL OR c.phone LIKE CONCAT('%', :phone, '%'))")
    List<Client> searchClients(@Param("name") String name,
                               @Param("email") String email,
                               @Param("phone") String phone);
}
