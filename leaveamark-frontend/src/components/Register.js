import { useFormik } from 'formik';
import * as Yup from 'yup';
import { register } from "../services/services";
import { Button, Form } from 'react-bootstrap';
import { ToastContainer, toast } from 'react-toastify';


const Register = () => {

    const formik = useFormik({
        initialValues: {
            registerUsername: "",
            email: "",
            registerPassword: "",
        },
        onSubmit: (values, onSubmitProps) => {
            register(
                values.registerUsername,
                values.registerPassword,
                values.email,
            ).then((res) => {
                toast.success('To activate your account, please click the link in the email we just sent you.', {
                    position: "bottom-right",
                    autoClose: 3000,
                    hideProgressBar: true,
                    closeOnClick: true,
                    pauseOnHover: true,
                    draggable: true,
                    progress: undefined,
                });
                onSubmitProps.setSubmitting(false);
                onSubmitProps.resetForm()
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
            })
        },
        validationSchema: Yup.object({
            registerUsername: Yup.string()
                .min(5, 'Username length must exceed 5 letters.')
                .max(30, "Username length can not exceed 30 letters.")
                .required("Username is required."),
            registerPassword: Yup.string()
                .min(5, "Password length must exceed 5 letters.")
                .max(20, "Password length can not exceed 20 letters.")
                .required("Password is required."),
            email: Yup.string()
                .email("Email must be in a proper form.")
                .required("Email is required."),
        })
    })

    return (
        (
            <>

                <Form onSubmit={formik.handleSubmit}>
                    <h1>Register</h1>
                    <Form.Group>
                        <Form.Label>Email</Form.Label>
                        <Form.Control type="text" id="email"
                            value={formik.values.email}
                            onChange={formik.handleChange} />
                        {formik.touched.email && formik.errors.email ? (
                            <Form.Text>{formik.errors.email}</Form.Text>
                        ) : null}
                    </Form.Group>
                    <Form.Group>
                        <Form.Label >Username</Form.Label>
                        <Form.Control type="text" id="registerUsername" value={formik.values.registerUsername}
                            onChange={formik.handleChange} />
                        {formik.touched.registerUsername && formik.errors.registerUsername ? (
                            <Form.Text>{formik.errors.registerUsername}</Form.Text>
                        ) : null}
                    </Form.Group>
                    <Form.Group>
                        <Form.Label>Password</Form.Label>
                        <Form.Control type="password" id="registerPassword" value={formik.values.registerPassword}
                            onChange={formik.handleChange} />
                        {formik.touched.registerPassword && formik.errors.registerPassword ? (
                            <Form.Text>{formik.errors.registerPassword}</Form.Text>
                        ) : null}
                    </Form.Group>
                    <Button type="submit" className="mt-2" >Sign Up</Button>
                </Form>
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
            </>
        )
    )
}

export default Register