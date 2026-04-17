import axios from "axios"

// 추가
const API_SERVER_HOST = import.meta.env.VITE_API_SERVER

export const loginPost = async (email: string, pw: string) => {

    const header = { headers: { "Content-Type": "x-www-form-urlencoded" } }

    const form = new FormData()
    form.append('username', email)
    form.append('password', pw)

    const res = await axios.post(
        `${API_SERVER_HOST}/api/member/login`,
        form,
        header
    )

    //2초 delay
    await new Promise(resolve => setTimeout(resolve, 2000));

    return res.data
}