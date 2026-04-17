import {
  createSearchParams,
  useLoaderData,
  type LoaderFunctionArgs,
} from "react-router";
import ListComponent from "../../components/products/listComponent";
import jwtAxios from "../../util/jwtUtil";

export async function loadProducts({ request }: LoaderFunctionArgs) {
  const url = new URL(request.url);
  const page = url.searchParams.get("page") || "1";
  const size = url.searchParams.get("size") || "10";

  const queryStr = createSearchParams({ page, size }).toString();

  const res = await jwtAxios.get(
    `/api/products/list?${queryStr}`
  );

  return res.data;
}

const ListPage = () => {
  const pageResponse = useLoaderData() as PageResponseDTO<ProductDTO>;

  return (
    <div className="w-full bg-white rounded-2xl shadow-sm border border-gray-200">
      <div className="text-2xl font-bold px-6 pt-6 pb-2">Products List</div>
      <ListComponent serverData={pageResponse} />
    </div>
  );
};

export default ListPage;