import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Clients = () => {
  const [clients, setClients] = useState([]);
  const [error, setError] = useState("");
  const navigate = useNavigate();

  useEffect(() => {
    const fetchClients = async () => {
      const token = localStorage.getItem("token");

      if (!token) {
        navigate("/");
        return;
      }

      try {
        const response = await axios.get("http://localhost:8080/clients/searchClients", {
          headers: {
            Authorization: `Bearer ${token}`,
          },
        });
        setClients(response.data.content); // Caso esteja usando paginação
      } catch (err) {
        setError("Erro ao carregar clientes. Verifique sua autenticação.");
      }
    };

    fetchClients();
  }, [navigate]);

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/");
  };

  return (
    <div>
      <h2>Lista de Clientes</h2>
      {error && <p style={{ color: "red" }}>{error}</p>}
      <button onClick={handleLogout}>Logout</button>
      {clients.length > 0 ? (
        <ul>
          {clients.map((client) => (
            <li key={client.id}>
              {client.name} - {client.email} - {client.phone}
            </li>
          ))}
        </ul>
      ) : (
        <p>Nenhum cliente encontrado.</p>
      )}
    </div>
  );
};

export default Clients;
