import axios from "axios"

const apiUrl = "/api/v1"

export const getPosts = () => {
    return axios(`${apiUrl}/posts`)
}

export const register = (username, password, email) => {
    return axios.post(`${apiUrl}/users/register`, {
        username, password, email
    })
}

export const login = (username, password) => {
    return axios.post(`${apiUrl}/users/login`, {
        username, password
    })
}


export const addPost = (text, userId) => {
    return axios.post(`${apiUrl}/posts`, {
        text, userId
    },
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("jwt")}`
            }
        })
}

export const addLike = (userId, postId) => {
    return axios.post(`${apiUrl}/likes`, {
        userId, postId
    },
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("jwt")}`
            }
        })
}

export const deleteLikeByPostIdAndUserId = (postId, userId) => {
    return axios.delete(`${apiUrl}/likes/${postId}/${userId}`,
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("jwt")}`
            }
        })
}

export const getUser = (username) => {
    return axios(`${apiUrl}/users/${username}`)
}

export const getPostsByUsername = (username) => {
    return axios(`${apiUrl}/posts/${username}`)
}

export const deletePost = (postId, userId) => {
    return axios.delete(`${apiUrl}/posts/${postId}/${userId}`,
        {
            headers: {
                Authorization: `Bearer ${localStorage.getItem("jwt")}`
            }
        })
}

export const verifyToken = (token) => {
    return axios.post(`${apiUrl}/emails/${token}`)
}