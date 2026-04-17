import axios, { AxiosError, type AxiosResponse, type InternalAxiosRequestConfig } from "axios";
import { getCookie, setCookie } from "./cookieUtil";

// CloudFront 들어가기 전 localhost 삭제 수정
const API_SERVER_HOST = import.meta.env.VITE_API_SERVER

const jwtAxios = axios.create({
    baseURL: API_SERVER_HOST
})

// CloudFront 들어가기 전 localhost 삭제 수정
const refreshJWT = async (accessToken: string, refreshToken: string) => {

    const header = { headers: { Authorization: `Bearer ${accessToken}` } }

    const res = await axios.get(
        `${API_SERVER_HOST}/api/member/refresh?refreshToken=${refreshToken}`,
        header
    )

    return res.data
}

//before request
const beforeReq = (config: InternalAxiosRequestConfig) => {

    const memberInfo = getCookie("member");

    if (!memberInfo) {
        return Promise.reject(new Error("REQUIRE_LOGIN"));
    }

    const { accessToken } = memberInfo

    config.headers.Authorization = `Bearer ${accessToken}`

    return config
}

//fail request
const requestFail = (err: AxiosError) => {
    return Promise.reject(err)
}

//before response
const beforeRes = async (res: AxiosResponse): Promise<AxiosResponse> => {

    const data = res.data

    if (data && data.error === 'ERROR_ACCESS_TOKEN') {

        const memberCookieValue = getCookie("member")

        const result = await refreshJWT(
            memberCookieValue.accessToken,
            memberCookieValue.refreshToken
        )

        memberCookieValue.accessToken = result.accessToken
        memberCookieValue.refreshToken = result.refreshToken

        setCookie("member", JSON.stringify(memberCookieValue), 1)

        const originalRequest = res.config
        originalRequest.headers.Authorization = `Bearer ${result.accessToken}`

        return await axios(originalRequest)
    }

    return res
}

//fail response
const responseFail = async (err: AxiosError) => {
    return Promise.reject(err)
}

jwtAxios.interceptors.request.use(beforeReq, requestFail)
jwtAxios.interceptors.response.use(beforeRes, responseFail)

export default jwtAxios