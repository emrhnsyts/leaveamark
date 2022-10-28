import React, { useContext } from 'react'
import Register from './Register'
import Login from "./Login"
import { Col, Row } from 'react-bootstrap'
import UserContext from '../context/UserContext'
import { Navigate } from 'react-router-dom'

const Account = () => {
    const { user } = useContext(UserContext);

    return (
        <>
            {user ? <Navigate to="/" /> :
                <Row>
                    <Col>
                        <Login />
                    </Col>
                    <Col>
                        <Register />
                    </Col>
                </Row >
            }
        </>
    )
}

export default Account