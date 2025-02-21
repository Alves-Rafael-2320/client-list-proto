import React from "react";
import { Routes, Route } from "react-router-dom";
import Login from "./pages/Login";
import Clients from "./pages/Clients";

function App() {
  return (
    <Routes>
      <Route path="/" element={<Login />} />
      <Route path="/clients" element={<Clients />} />
    </Routes>
  );
}

export default App;
