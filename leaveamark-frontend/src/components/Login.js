import { useFormik } from 'formik';
import React, { useContext } from 'react'
import jwt_decode from "jwt-decode"
import { useNavigate } from 'react-router-dom';
import * as Yup from 'yup';
import UserContext from '../context/UserContext';
import { login } from '../services/services';
import { Button, Form } from 'react-bootstrap';
import { ToastContainer, toast } from 'react-toastify';


const Login = () => {
    const navigate = useNavigate();

    const { setUser } = useContext(UserContext);

    const formik = useFormik({
        initialValues: {
            loginUsername: "",
            loginPassword: ""
        },

        onSubmit: values => {
            login(values.loginUsername, values.loginPassword).then((res) => {
                localStorage.setItem("jwt", res.data)
                setUser(jwt_decode(res.data))
                navigate("/")
            }).catch((err) => {
                toast.error(err.response.data.message, {
                    position: "bottom-right",
                    autoClose: 3000,
                    hideProgressBar: true,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                });
            });
        },
        validationSchema: Yup.object({
            loginUsername: Yup.string()
            .min(5,'Username length must exceed 5 letters.')
            .max(30,"Username length can not exceed 30 letters.")
            .required("Username is required."),
            loginPassword: Yup.string()
            .min(5,"Password length must exceed 5 letters.")
            .max(20,"Password length can not exceed 20 letters.")
            .required("Password is required.")
        })

    })

    return (
        <Form onSubmit={formik.handleSubmit} >
            <h1>Login</h1>
            <Form.Group >
                <Form.Label>Username</Form.Label>
                <Form.Control type="text" value={formik.values.loginUsername}
                    onChange={formik.handleChange} id="loginUsername" />
                {formik.touched.loginUsername && formik.errors.loginUsername ? (
                    <Form.Text>{formik.errors.loginUsername}</Form.Text>
                ) : null}
            </Form.Group>
            <Form.Group>
                <Form.Label>Password</Form.Label>
                <Form.Control type="password" value={formik.values.loginPassword}
                    onChange={formik.handleChange} id="loginPassword" />
                {formik.touched.loginPassword && formik.errors.loginPassword ? (
                    <Form.Text>{formik.errors.loginPassword}</Form.Text>
                ) : null}
            </Form.Group>

            <Button type="submit" className="mt-2">Login</Button>
            <ToastContainer
                position="bottom-right"
                autoClose={3000}
                hideProgressBar
                newestOnTop={false}
                closeOnClick
                rtl={false}
                pauseOnFocusLoss
                draggable
                pauseOnHover
            />
        </Form >


    )
}

export default Login