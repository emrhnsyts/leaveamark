import React, { useContext, useEffect, useState } from 'react'
import { Button, Col, Container, Form, Navbar, Row } from 'react-bootstrap';
import { addPost, getPosts } from '../services/services';
import * as Yup from 'yup';
import { useFormik } from 'formik';
import UserContext from '../context/UserContext';
import { extractCredentials } from '../helpers/jwtHelper';
import { useNavigate } from "react-router-dom";
import Post from './Post';
import { ToastContainer, toast } from 'react-toastify';



const Posts = () => {
    const [posts, setPosts] = useState(null);
    const [loading, setLoading] = useState(true);

    const { user, setUser } = useContext(UserContext);

    const navigate = useNavigate();

    useEffect(() => {
        getPosts().then((res) => {
            setPosts(res.data)
            setLoading(false)
        })
    }, [])



    const formik = useFormik({
        initialValues: {
            text: "",
        },

        onSubmit: (values, onSubmitProps) => {
            if (!extractCredentials(setUser)) {
                navigate("/login")
            }
            else {
                addPost(values.text, user.userId).then((res) => {
                    getPosts().then(res => {
                        setPosts(res.data)
                        toast.success("You have successfully posted.", {
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

                    })
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
            }
        },
        validationSchema: Yup.object({
            text: Yup.string().min(3).max(50).required()
        }),

    })


    return (
        <>
            <Row>
                {loading ? <div className="spinner-border" role="status"></div> : posts.map((post) => {
                    return <Post post={post}
                        key={post.id}
                        setPosts={setPosts}
                        isProfile={false}
                    />
                })}

            </Row>
            {user && <Navbar className="py-2" fixed="bottom" bg="dark" expand="lg">
                <Container>
                    <Form.Control size="lg" type="text" id="text" placeholder="Post" onChange={formik.handleChange}
                        value={formik.values.text} />
                    <Button className="ms-2" size="lg" variant="danger" onClick={formik.handleSubmit}>
                        Submit
                    </Button>
                </Container>
            </Navbar >
            }
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
}

export default Posts