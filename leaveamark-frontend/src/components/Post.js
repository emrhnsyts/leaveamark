import React, { useContext, useEffect, useState } from 'react'
import { Button, Card, Col } from 'react-bootstrap'
import { Link, useNavigate, useParams } from 'react-router-dom';
import SimpleDateTime from 'react-simple-timestamp-to-date';
import UserContext from '../context/UserContext';
import { extractCredentials } from '../helpers/jwtHelper';
import { addLike, deleteLikeByPostIdAndUserId, deletePost, getPosts, getPostsByUsername } from '../services/services'
import { ToastContainer, toast } from 'react-toastify';

const Post = ({ post, setPosts, isProfile }) => {

    const { user, setUser } = useContext(UserContext);
    const [isLiked, setIsLiked] = useState();
    const { username } = useParams();


    useEffect(() => {
        if (user) {
            if (post.likes.some((like) => like.userId === user.userId)) {
                setIsLiked(true)
            }
            else {
                setIsLiked(false);
            }
        }
    }, [])

    const navigate = useNavigate();

    const handleDelete = () => {
        if (!extractCredentials(setUser)) {
            navigate("/account")
        }
        else {
            deletePost(post.id, user.userId)
                .then(() => {
                    if (isProfile) {
                        getPostsByUsername(username).then((res) => {
                            setPosts(res.data)
                        })
                    }
                    else {
                        getPosts().then((res) => {
                            setPosts(res.data)
                        })
                    }
                })
                .then(() => {
                    toast.warn("Post deleted successfully.", {
                        position: "bottom-right",
                        autoClose: 3000,
                        hideProgressBar: true,
                        closeOnClick: true,
                        pauseOnHover: true,
                        draggable: true,
                        progress: undefined,
                    });
                })
        }
    }


    const handleLike = (post) => {
        if (!extractCredentials(setUser)) {
            navigate("/account")
        }
        else {
            if (!isLiked) {
                addLike(user.userId, post.id).then(() => {
                    if (isProfile) {
                        getPostsByUsername(username).then((res) => {
                            setPosts(res.data)
                        })
                    }
                    else {
                        getPosts().then((res) => {
                            setPosts(res.data)
                        })
                    }

                })
                setIsLiked(true);

            }
            else {

                deleteLikeByPostIdAndUserId(post.id, user.userId).then(() => {
                    if (isProfile) {
                        getPostsByUsername(username).then((res) => {
                            setPosts(res.data)
                        })
                    }
                    else {
                        getPosts().then((res) => {
                            setPosts(res.data)
                        })
                    }

                })
                setIsLiked(false);
            }
        }
    }


    return (
        <>
            <Col md={6} className="pb-2" key={post.id}>
                <Card>
                    <Card.Body>
                        <Card.Title className="py-2 d-flex justify-content-between">
                            <div className="text-wrap" style={{ width: "15rem" }}>{post.text}</div>
                            {post.userId === user?.userId || user?.roles.includes("ROLE_ADMIN")
                                ?
                                <Button size="sm" onClick={handleDelete} variant="danger">
                                    <i className="bi bi-x"></i>
                                </Button>
                                :
                                <span></span>
                            }
                        </Card.Title>
                        <Card.Text>Created at: <span> </span>
                            <SimpleDateTime dateSeparator="-" timeSeparator=".">{post.createdAt}</SimpleDateTime>
                        </Card.Text>
                        <Button onClick={() => { handleLike(post) }} variant="primary" id="likeButton">
                            {isLiked ? <i className="bi bi-hand-thumbs-down"></i> :
                                <i className="bi bi-hand-thumbs-up"></i>} {post.likes.length}
                        </Button>
                    </Card.Body>
                    {!isProfile &&
                        <Card.Footer>Posted by: <span></span>
                            <Link to={`/users/${post.username}`}>{post.username}</Link>
                        </Card.Footer>
                    }

                </Card>
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
            </Col>
        </>
    )
}

export default Post