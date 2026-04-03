import { useState } from "react"
import ResultModal from "../common/resultModal";
import useCustomLogin from "../../hooks/useCustomLogin";
import KakaoLoginComponent from "./kakaoLoginComponent";

// //상태
// interface LoginResult {
//     email: string,
//     signed: boolean
// }

// const initState: LoginResult = {
//     email: '',
//     signed: false
// }

// async function loginAction(state: LoginResult, formData: FormData) {

//     //2초간 delay
//     await new Promise(resolve => setTimeout(resolve, 2000));

//     const email = formData.get('email') as string
//     const pw = formData.get("pw") as string

//     console.log("email ", email, "pw ", pw)

//     return { email: email, signed: true }
// }

function LoginComponent() {

    const{doLogin, loginStatus, moveToPath} = useCustomLogin()

    const [email, setEmail] = useState('')
    const [pw, setPw] = useState('')

    const handleLogin = () => {
        doLogin(email, pw)
    }

    const closeModal = () => {
        moveToPath('/')
    }


    // //useActionState()대신 useDispatch() 사용

    // const dispatch = useDispatch()


    // const [state, action, isPending] = useActionState(loginAction, initState)

    // //막줄 [] 상태가 변하면 발동. loginSlice 의 reducer 발동
    // useEffect(() => {
    //     if (state.signed) {
    //         dispatch(login(state))
    //     }
    // }, [state.signed])

    //

    return (

        <div className="border-2 border-sky-200 mt-10 m-2 p-4">

            {loginStatus === 'pending' && <div className="bg-amber-300">로그인 중...</div>}

            {loginStatus === 'fulfilled' && <ResultModal title="Login Result" content="로그인 되었습니다." callbackFn={closeModal} />}

            {loginStatus === 'rejected' && <ResultModal title="Login Result" content="로그인에 실패하였습니다." callbackFn={closeModal} />}

            <div className="flex justify-center">
                <div className="text-4xl m-4 p-4 font-extrabold text-blue-500">Login Component</div>
            </div>
            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-full p-6 text-left font-bold">Email</div>
                    <input className="w-full p-6 rounded-r border border-solid border-neutral-500 shadow-md"
                        name="email" type={'text'} onChange={(e) => setEmail(e.target.value)} />
                </div>
            </div>
            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                    <div className="w-full p-6 text-left font-bold">Password</div>
                    <input className="w-full p-6 rounded-r border border-solid border-neutral-500 shadow-md"
                        name="pw" type={'password'} onChange={(e) => setPw(e.target.value)} />
                </div>
            </div>
            <div className="flex justify-center">
                <div className="relative mb-4 flex w-full justify-center">
                    <div className="w-2/5 p-6 flex justify-center font-bold">
                        <button type='submit'
                            className="rounded p-4 w-36 bg-blue-500 text-xl  text-white"
                            onClick={() => handleLogin()}>
                            LOGIN
                        </button>
                    </div>
                </div>
            </div>
            <KakaoLoginComponent/>
        </div>



        // <div className="border-2 border-sky-200 mt-10 m-2 p-4">



        // {/* // //useActionState()대신 useDispatch() 사용 */ }
        /* {isPending && <div className="bg-amber-300">로그인 처리중...</div>}
                {state.signed && <div className="bg-green-300"> 로그인 처리 완료 </div>} */
        /* <form action={action}>
                    <div className="flex justify-center">
                        <div className="text-4xl m-4 p-4 font-extrabold text-blue-500">Login Component</div>
                    </div>
                    <div className="flex justify-center">
                        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                            <div className="w-full p-6 text-left font-bold">Email</div>
                            <input className="w-full p-6 rounded-r border border-solid border-neutral-500 shadow-md"
                                name="email" type={'text'} />
                        </div>
                    </div>
                    <div className="flex justify-center">
                        <div className="relative mb-4 flex w-full flex-wrap items-stretch">
                            <div className="w-full p-6 text-left font-bold">Password</div>
                            <input className="w-full p-6 rounded-r border border-solid border-neutral-500 shadow-md"
                                name="pw" type={'password'} />
                        </div>
                    </div>
                    <div className="flex justify-center">
                        <div className="relative mb-4 flex w-full justify-center">
                            <div className="w-2/5 p-6 flex justify-center font-bold">
                                <button type='submit' className="rounded p-4 w-36 bg-blue-500 text-xl text-white">
                                    LOGIN
                                </button>
                            </div>
                        </div>
                    </div>
                </form> */

        // </div>
    )
}

export default LoginComponent
