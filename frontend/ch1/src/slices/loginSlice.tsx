import { createAsyncThunk, createSlice } from "@reduxjs/toolkit"
import { loginPost } from "../api/memberApi"
import { removeCookie, setCookie } from "../util/cookieUtil"

//API 서버의 결과를 받도록 변경 
export interface LoginInfo {
    email: string,
    nickname: string,
    accessToken: string,
    refreshToken: string,
    roleNames: string[],
    status: string
}
const initState: LoginInfo = {
    email: '',
    nickname: '',
    accessToken: '',
    refreshToken: '',
    roleNames: [],
    status: ''
}

export const loginPostAsync = createAsyncThunk('loginPostAsync', ({ email, pw }: { email: string, pw: string }) => {
    console.log("---------------loginPostAsync---------------------")
    console.log(email, pw)
    return loginPost(email, pw)
})

const loginSlice = createSlice({

    name: 'LoginSlice',
    initialState: initState,
    reducers: {

        //쿠키가 있다면 데이터를 반영
        save: (state, action) => {
            const payload = action.payload  //{소셜로그인 회원이 사용}  

            const newState = { ...payload, status: 'saved' }
            setCookie("member", JSON.stringify(newState), 1) //1일

            return newState

        },

        //login은 사용하지 않음
        logout: (state, action) => {

            console.log("logout")

            removeCookie("member")

            return { ...initState }
        },

        reset: () => initState

    },
    extraReducers: (builder) => {
        //addCase 
        //           1. fufilled  2. pending  3. rejected
        builder.addCase(loginPostAsync.fulfilled, (state, action) => {

            console.log("fulfilled")

            //로그인 되는 순간 payload속 member json 데이터를 받았기때문에 그에맞게 todo, product, logout으로 변경되도록 주입
            const newState: LoginInfo = action.payload

            newState.status = 'fulfilled'

            //cookieUtil 적용
            setCookie("member", JSON.stringify(newState), 1)

            return newState
        })
            .addCase(loginPostAsync.pending, (state, action) => {
                console.log("pending")
                state.status = 'pending'
            })
            .addCase(loginPostAsync.rejected, (state, action) => {
                console.log("rejected")
                state.status = 'rejected'
            })
    }


})
//login 제외 (이유 : asyncThunk)
export const { save, logout, reset } = loginSlice.actions

export default loginSlice.reducer
