import React, { useContext, useEffect } from 'react'
import { useNavigate, useParams } from 'react-router-dom'
import UserContext from '../context/UserContext';
import { verifyToken } from '../services/services';

const Email = () => {
    const { token } = useParams();
    const { user } = useContext(UserContext);
    const navigate = useNavigate()

    useEffect(() => {
        if (!user) {
            verifyToken(token).then((res) => {
                navigate("/account")
            }).catch((err) => {
                navigate("/*")
            })
        }
    }, [])

    return (
        <div>Verifying</div>
    )
}

export default Email