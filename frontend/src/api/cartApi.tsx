import jwtAxios from "../util/jwtUtil"

export const getCartItems = async (): Promise<CartItemResponse[]> => {
    const res = await jwtAxios.get(`/api/cart/items`)
    return res.data
}

export const postChangeCart = async (cartItem: CartItemRequest) => {
    const res = await jwtAxios.post(`/api/cart/change`, cartItem)
    return res.data
}