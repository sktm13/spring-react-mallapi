import useCustomLogin from "../hooks/useCustomLogin";

function AboutPage() {

    //1.선언
    const {loginStatus, moveToLoginReturn} = useCustomLogin()

    //2.사용
    // if(!loginStatus){
    //     return moveToLoginReturn()
    // }
    if(loginStatus !== 'fulfilled' && loginStatus !== 'saved'){
        return moveToLoginReturn()
    }
    return (

        <div className=" text-3xl">
            <div>About Page</div>
        </div>

    );
}

export default AboutPage;