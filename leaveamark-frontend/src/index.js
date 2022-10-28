import React from 'react';
import ReactDOM from 'react-dom/client';
import App from './App';
import { UserProvider } from "../src/context/UserContext"
import 'bootstrap/dist/css/bootstrap.min.css';
import "./css/index.css"
import 'react-toastify/dist/ReactToastify.css'

const root = ReactDOM.createRoot(document.getElementById('root'));
root.render(
  <UserProvider>
    <App />
  </UserProvider>
);

