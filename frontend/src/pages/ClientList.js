import React, {useState, useEffect} from "react";

import axios from "axios";

const ClientList = () => {
  const [clients, setClients] = useState([]);
  
const fetchClients = async () => {
  try{
    const response = await axios.get("http://localhost:8080/clients/findAllClients");
    setClients(response.data);
  }catch (error){
    console.error("Erro ao buscar os clientes", error);
  }
};

useEffect(() => {
  fetchClients();
}, []);

return (
 <div>
  <h2>Clientes:</h2>
  {clients.length > 0 ?(
    <ul>
      {clients.map(client =>(
        <li key={client.id}>
          {client.name} - {client.phone} - {client.email}
        </li>
      ))}
    </ul>
  ):(
    <p>No Clients found</p>
  )}
 </div>
);
};

export default ClientList;
