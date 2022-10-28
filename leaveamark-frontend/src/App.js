import { BrowserRouter, Routes, Route } from "react-router-dom";
import Login from "./components/Login";
import AppNavbar from "./components/AppNavbar";
import Posts from "./components/Posts";
import Register from "./components/Register";
import Home from "./components/Home";
import { Container } from "react-bootstrap";
import Profile from "./components/Profile";
import Account from "./components/Account";
import Error from "./components/Error";
import Email from "./components/Email";

const App = () => {

  return (
    <BrowserRouter>
      <AppNavbar />
      <Container className="pt-4">
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="posts" element={<Posts />} />
          <Route path="account" element={<Account />} />
          <Route path="users/:username" element={<Profile />} />
          <Route path="verify/:token" element={<Email />} />
          <Route path="*" element={<Error />} />
        </Routes>
      </Container>
    </BrowserRouter>
  );
}

export default App;
