import React, { useContext } from 'react'
import { Button, Row } from 'react-bootstrap';
import Container from 'react-bootstrap/Container';
import Nav from 'react-bootstrap/Nav';
import Navbar from 'react-bootstrap/Navbar';
import { Link, useNavigate } from 'react-router-dom';
import UserContext from '../context/UserContext';


const AppNavbar = () => {

    const { user, setUser } = useContext(UserContext)

    const navigate = useNavigate()

    return (
        <Navbar bg="dark" variant="dark" expand="lg">
            <Container>
                <Navbar.Brand>
                    <Link to="/" >Leave a Mark</Link>
                </Navbar.Brand>
                <Nav className="me-auto">
                    <Link to="posts">Posts</Link>
                </Nav>
                {user ?
                    <>
                        <Link to={`/users/${user.sub}`} className="me-2">Profile</Link>
                        <Button variant="danger" onClick={() => {
                            localStorage.clear()
                            setUser(null);
                            navigate("/")
                        }}>Logout </Button>
                    </>
                    :
                    <Link to="account">Login/Register</Link>
                }

            </Container>
        </Navbar>

    );
}

export default AppNavbar