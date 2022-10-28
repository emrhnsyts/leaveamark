import { useEffect, useState } from "react"
import { Card, Col, ListGroup, Row } from "react-bootstrap";
import { useNavigate, useParams } from "react-router-dom";
import SimpleDateTime from "react-simple-timestamp-to-date";
import { getPostsByUsername, getUser } from "../services/services";
import Post from "./Post";

const Profile = () => {
    const [userProfile, setUserProfile] = useState(null);
    const [profileLoading, setProfileLoading] = useState(true);
    const [loading, setLoading] = useState(true);
    const [posts, setPosts] = useState();

    const { username } = useParams()

    const navigate = useNavigate();

    useEffect(() => {
        getUser(username).then((res) => {
            setUserProfile(res.data)
            setProfileLoading(false);
        }).catch((err) => {
            navigate("/*")
        })
        getPostsByUsername(username).then((res) => {
            setPosts(res.data)
            setLoading(false);
        })
    }, [username])

    return (
        <Row className="justify-content-center">
            {profileLoading ? <div className="spinner-border" role="status"></div> :
                <Col>
                    <h1>{userProfile.username}</h1>
                    <h5>{userProfile.email}</h5>

                    Created at: <span></span>
                    <SimpleDateTime dateSeparator="-" timeSeparator=".">
                        {userProfile.createdAt}
                    </SimpleDateTime>
                </Col>

            }

            {
                loading ? <div className="spinner-border" role="status"></div> : posts.map((post) => {
                    return <Post post={post}
                        key={post.id}
                        isProfile={true}
                        setPosts={setPosts}
                    />
                })
            }
        </Row >
    )
}

export default Profile