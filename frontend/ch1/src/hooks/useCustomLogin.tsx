import { useDispatch, useSelector } from "react-redux"
import type { AppDispatch, RootState } from "../store"
import { Navigate, useNavigate } from "react-router"
import { loginPostAsync, logout, save } from "../slices/loginSlice"
import { useEffect } from "react"
import { getCookie } from "../util/cookieUtil"

const useCustomLogin = () => {

    const dispatch = useDispatch<AppDispatch>()
    //로그인 상태 객체 
    const loginState = useSelector((state: RootState) => state.loginSlice)

    //로그인 여부
    const loginStatus = loginState.status

    //새로고침 대응 
    useEffect(() => {
        if (loginStatus !== 'fulfilled') {

            // const member = getCookie("member")

            // if (member) {
            //     dispatch(save(member))
            // }

            const cookieData = getCookie("member")
            if (cookieData) {
                dispatch(save(cookieData))
            }
        }
    }, [])


    const navigate = useNavigate()

    const doLogin = async (email: string, pw: string) => {
        dispatch(loginPostAsync({ email, pw }))
    }

    const doLogout = () => {
        dispatch(logout(null))
    }

    const moveToLogin = () => {
        navigate("/member/login")
    }

    const moveToLoginReturn = () => { //--------로그인 페이지로 이동 컴포넌트 (로그인 안되어있는데 로그인필요한 페이지 눌렀을때)
        return <Navigate replace to="/member/login" />
    }

    const moveToPath = (path: string) => { //----------------페이지 이동 
        navigate({ pathname: path }, { replace: true })
    }

    return { loginState, loginStatus, doLogin, doLogout, moveToLogin, moveToLoginReturn, moveToPath }

}
export default useCustomLogin