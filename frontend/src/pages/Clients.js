import React, { useState, useEffect, useCallback } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Clients = () => {
  const [clients, setClients] = useState([]);
  const [newClient, setNewClient] = useState({ name: "", email: "", phone: "" });
  const [error, setError] = useState("");
  const navigate = useNavigate();

  const fetchClients = useCallback(async () => {
    const token = localStorage.getItem("token");
    if (!token) {
      navigate("/");
      return;
    }

    try {
      const response = await axios.get("http://localhost:8080/clients/searchClients", {
        headers: { Authorization: `Bearer ${token}` },
      });
      setClients(response.data.content);
    } catch (err) {
      setError("Erro ao carregar clientes.");
    }
  }, [navigate]);

  useEffect(() => {
    fetchClients();
  }, [fetchClients]);
  
  console.log(clients);
  
  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  const handleInputChange = (e) => {
    setNewClient({ ...newClient, [e.target.name]: e.target.value });
  };

  const handleCreateClient = async (e) => {
    e.preventDefault();
    const token = localStorage.getItem("token");

    try {
      const response = await axios.post("http://localhost:8080/clients", newClient, {
        headers: { Authorization: `Bearer ${token}`, "Content-Type": "application/json" },
      });

      setClients([...clients, response.data]);
      setNewClient({ name: "", email: "", phone: "" });
    } catch (err) {
      setError("Erro ao criar cliente.");
    }
  };

  const handleDeleteClient = async (id) => {
    if (!id) {
      setError("Erro: ID do cliente não encontrado.");
      console.error("Tentativa de excluir um cliente sem ID!");
      return;
    }
  
    console.log("Excluindo cliente com ID:", id); 
  
    const token = localStorage.getItem("token");
  
    try {
      await axios.delete(`http://localhost:8080/clients/${id}`, {
        headers: { Authorization: `Bearer ${token}` },
      });
  
      setClients(clients.filter((client) => client.id !== id));
    } catch (err) {
      setError("Erro ao excluir cliente.");
      console.error("Erro ao excluir cliente:", err);
    }
  };
  
  
  return (
    <div>
      <h2>Lista de Clientes</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <button onClick={handleLogout}>Logout</button>

      <h3>Criar Novo Cliente</h3>
      <form onSubmit={handleCreateClient}>
        <input type="text" name="name" placeholder="Nome" value={newClient.name} onChange={handleInputChange} required />
        <input type="email" name="email" placeholder="Email" value={newClient.email} onChange={handleInputChange} required />
        <input type="text" name="phone" placeholder="Telefone" value={newClient.phone} onChange={handleInputChange} required />
        <button type="submit">Adicionar Cliente</button>
      </form>

      <h3>Clientes Cadastrados</h3>
      {clients.length > 0 ? (
   
    <ul>
      {clients.map((client) => {
        if (!client.id) {
          console.error("Erro: Cliente sem ID!", client);
          return null;
        }
        return (
          <li key={client.id}>
            {client.name} - {client.email} - {client.phone}
            <button onClick={() => handleDeleteClient(client.id)} style={{ marginLeft: "10px", color: "red" }}>
              Excluir
            </button>
          </li>
        );
      })}
    </ul>
 
      ) : (
        <p>Nenhum cliente encontrado.</p>
      )}
    </div>
  );
};

export default Clients;
