import React from 'react';
import { BrowserRouter as Router, Routes, Route, Navigate } from 'react-router-dom';
import GatewayList from './component/GatewayList';
import CreateGateway from './component/CreateGateway';
import GatewayDetails from './component/GatewayDetails';

function App() {
  return (
    <Router>
      <Routes>
        <Route exact path="/gateways" element={<GatewayList />} />
        <Route exact path="/add-gateway" element={<CreateGateway />} />
        <Route exact path="/gateways/:id" element={<GatewayDetails />} />
        <Route exact path="/" element={<Navigate to="/gateways" />} />
      </Routes>
    </Router>
  );
}

export default App;
