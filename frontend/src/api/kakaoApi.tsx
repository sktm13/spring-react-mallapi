import axios from "axios"

// 환경변수
const API_SERVER_HOST = import.meta.env.VITE_API_SERVER
const FRONTEND_HOST = import.meta.env.VITE_FRONTEND_HOST

const rest_api_key = `a450751b3a6942dbbc641906b158ef5a`

// 수정
const redirect_uri = `${FRONTEND_HOST}/member/kakao`

const auth_code_path = `https://kauth.kakao.com/oauth/authorize`
const access_token_url = `https://kauth.kakao.com/oauth/token`

const client_secret = 'UDIZ1FtDYEaB4tpN06XE29lPxNadXG7j'


export const getKakaoLoginLink = () => {

    const kakaoURL = `${auth_code_path}?client_id=${rest_api_key}&redirect_uri=${redirect_uri}&response_type=code`;

    return kakaoURL
}


export const getAccessToken = async (authCode: string) => {

    const header = {
        headers: {
            "Content-Type": "application/x-www-form-urlencoded",
        }
    }

    const params = {
        grant_type: "authorization_code",
        client_id: rest_api_key,
        redirect_uri: redirect_uri,
        code: authCode,
        client_secret: client_secret
    }

    const res = await axios.post(access_token_url, params, header)

    return res.data.access_token
}


// 수정
export const getMemberWithAccessToken = async (accessToken: string) => {

    const res = await axios.get(
        `${API_SERVER_HOST}/api/member/kakao?accessToken=${accessToken}`
    )

    return res.data
}