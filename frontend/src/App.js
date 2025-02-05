import React from "react";

import {BrowserRouter as Router, Routes, Route} from "react-router-dom";

import ClientList from "./pages/ClientList";

function App(){
  return(
    <Router>
      <div>
        <h1>Artbox Vidraçaria</h1>
        <Routes>
          <Route path="/" element = {<ClientList />}></Route>
        </Routes>
      </div>
    </Router>
  );
}

export default App;