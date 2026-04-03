import { useEffect } from "react";
import { Navigate, useNavigate, useSearchParams } from "react-router";
import { getAccessToken, getMemberWithAccessToken } from "../../api/kakaoApi";
import type { AppDispatch } from "../../store";
import { useDispatch } from "react-redux";
import { save } from "../../slices/loginSlice";

const KakaoRedirectPage = () => {

    const [searchParams] = useSearchParams()
    const authCode = searchParams.get("code")

    const dispatch = useDispatch<AppDispatch>()

    const navigate = useNavigate()

    //authCode -> Access token
    useEffect(() => {

        if (authCode) {
            getAccessToken(authCode).then(accessToken => {
                console.log("access Token ");
                console.log(accessToken);

                getMemberWithAccessToken(accessToken).then(result => {
                    console.log("============");
                    console.log(result);
                    console.log("============");

                    dispatch(save(result))

                    if(result.social == true) {
                        navigate("/member/modify")
                    }else{
                        navigate("/")
                    }

                })
            })

        }
    }, [authCode])


    return (

        
        <>
            <Navigate to={'/'}></Navigate>
        </>

    )
}

export default KakaoRedirectPage;
