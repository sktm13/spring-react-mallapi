import type { LoaderFunctionArgs } from "react-router";
import { useLoaderData } from "react-router";
import ReadComponent from "../../components/products/readComponent";
import jwtAxios from "../../util/jwtUtil";

export async function loadProduct({ params }: LoaderFunctionArgs) {
  const { pno } = params;
  const res = await jwtAxios.get(`/api/products/${pno}`);

  return res.data;
}

function ReadPage() {
  const product: ProductDTO = useLoaderData() as ProductDTO;

  return (
    <div className="w-full">
      <ReadComponent product={product} />
    </div>
  );
}

export default ReadPage;