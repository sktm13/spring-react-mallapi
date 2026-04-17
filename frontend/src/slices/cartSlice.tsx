import { createAsyncThunk, createSlice } from "@reduxjs/toolkit";
import { getCartItems, postChangeCart } from "../api/cartApi";

export const getCartItemsAsync = createAsyncThunk('getCartItemsAsync', () => {
    return getCartItems()
})

export const postChangeCartAsync = createAsyncThunk('postChangeCartAsync', (param: CartItemRequest) => {
    return postChangeCart(param)
})

//초기상태
const initState: CartItemsArray = { items: [], status: '' }

const cartSlice = createSlice({

    name: 'cartSlice',
    initialState: initState,
    reducers: {


    },
    extraReducers: (builder) => {

        builder.addCase(
            getCartItemsAsync.fulfilled, (state, action) => {
                console.log("getCartItemsAsync fulfilled", state)
                const newState = { items: action.payload, status: 'fulfilled' }
                return newState
            }
        )

            .addCase(
                getCartItemsAsync.pending, (state, _action) => {
                    state.status = 'pending'
                }
            )

            .addCase(
                getCartItemsAsync.rejected, (state, _action) => {
                    state.status = 'rejected'
                }
            )

            //수량변경
            .addCase(
                postChangeCartAsync.fulfilled, (state, action) => {
                    console.log("postCartItemsAsync fulfilled ", state)
                    const newState = { items: action.payload, status: 'fulfilled' }
                    return newState
                }
            )
            .addCase(
                postChangeCartAsync.pending, (state, _action) => {
                    state.status = 'pending'
                }
            )
            .addCase(
                postChangeCartAsync.rejected, (state, _action) => {
                    state.status = 'rejected'
                }
            )


    },
})
export default cartSlice.reducer
