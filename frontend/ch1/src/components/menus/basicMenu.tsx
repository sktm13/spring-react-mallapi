import { NavLink } from "react-router";
import useCustomLogin from "../../hooks/useCustomLogin";

function BasicMenu() {

    const { loginState, loginStatus } = useCustomLogin()

    const isLogin = loginStatus === 'fulfilled' || loginStatus === 'saved'

    console.log("loginState:", loginState)
    console.log("nickname:", loginState.nickname)
    return (
        <nav id='navbar' className=" flex bg-blue-300">

            <div className="w-4/5 bg-gray-500" >
                <ul className="flex p-4 text-white font-bold">
                    <li className="pr-6 text-2xl">
                        <NavLink to='/'>Main</NavLink>
                    </li>
                    <li className="pr-6 text-2xl">
                        <NavLink to='/about'>About</NavLink>
                    </li>

                    {/* 로그인한 사용자한테만 보여지고 싶음 */}
                    {isLogin &&
                        <>
                            <li className="pr-6 text-2xl">
                                <NavLink to='/todo/'>Todo</NavLink>
                            </li>
                            <li className="pr-6 text-2xl">
                                <NavLink to='/products/'>products</NavLink>
                            </li>
                        </>}
                </ul>
            </div>
            <div className="w-1/5 flex justify-end bg-orange-300 p-4 font-medium">

                {!isLogin ?
                    <div className="text-white text-sm m-1 rounded">
                        <NavLink to='/member/login'>Login</NavLink>
                    </div>
                    :
                    <div className="text-white text-sm m-1 rounded">
                        <p>{loginState.nickname} 님</p>
                        <NavLink to='/member/logout'>Logout</NavLink>
                    </div>
                }
            </div>
        </nav>

    );
}

export default BasicMenu;