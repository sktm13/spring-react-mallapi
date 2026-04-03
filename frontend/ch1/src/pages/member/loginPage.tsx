import { useDispatch } from "react-redux";
import LoginComponent from "../../components/member/loginComponent";
import BasicMenu from "../../components/menus/basicMenu";
import type { AppDispatch } from "../../store";
import { useEffect } from "react";
import { reset } from "../../slices/loginSlice";

function LoginPage() {


    const dispatch = useDispatch<AppDispatch>()

    // 페이지 진입 시 로그인 상태 초기화 (실무 패턴)
    useEffect(() => {
        dispatch(reset())
    }, [dispatch])


    return (

        <div className='fixed top-0 left-0 z-1055 flex flex-col h-full w-full'>
            <BasicMenu />
            <div className="flex flex-wrap w-full h-full justify-center items-center border-2">
                <LoginComponent />
            </div>
        </div>

    );
}

export default LoginPage;