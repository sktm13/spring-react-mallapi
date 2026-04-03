import { lazy, Suspense } from "react"
import { Navigate } from "react-router"
import { loadProducts } from "../pages/products/listPage"
import { loadProduct } from "../pages/products/readPage"

const ProductsIndex = lazy(() => import("../pages/products/indexPage"))
const ProductsList = lazy(() => import("../pages/products/listPage"))
const ProductsAdd = lazy(() => import("../pages/products/addPage"))
const ProductsRead = lazy(() => import("../pages/products/readPage"))
const ProductsModify = lazy(() => import("../pages/products/modifyPage"))


const Loading = () => <div>Products Loading....</div>

export default function productsRouter() {
    return {
        path: "products",
        Component: ProductsIndex,
        children: [
            {
                //default 설정
                path: "",
                element: <Navigate to={'/products/list'}></Navigate>
            },
            {
                path: "list",
                element: <Suspense fallback={<Loading />}><ProductsList /></Suspense>,
                loader: loadProducts
            },
            {
                path: "add",
                element: <Suspense fallback={<Loading />}><ProductsAdd /></Suspense>
            },
            {
                path: "read/:pno",
                element: <Suspense fallback={<Loading />}><ProductsRead /></Suspense>,
                loader: loadProduct
                
            },
            {
                path: "modify/:pno",
                element: <Suspense fallback={<Loading />}><ProductsModify /></Suspense>,
                loader: loadProduct
                
            }
            
        ]
    }
}