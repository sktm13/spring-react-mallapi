interface CartItemRequest {
    email: string,
    pno: number,
    qty: number,
    cino?: number, // ? 있을 수도 있고 없을 수도 있다
    status?: string,
}

interface CartItemResponse {
    cino: number,
    qty: number,
    pno: number,
    pname: string,
    price: number,
    imageFile: string

}

interface CartItemsArray {
    items: CartItemResponse[]
    status?: string
}
