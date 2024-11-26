import React from "react";
import { BrowserRouter as Router, Route, Routes } from "react-router-dom";
import Form from "./components/Form"; // Adjust the import path based on your file structure

function App() {
  return (
      <Router>
        <Routes>
          <Route path="/" element={<h1>Welcome to Job Tracking Tool</h1>} />
          <Route path="/form" element={<Form />} />
          <Route path="*" element={<h1>404 - Page Not Found</h1>} />
        </Routes>
      </Router>
  );
}

export default App;
