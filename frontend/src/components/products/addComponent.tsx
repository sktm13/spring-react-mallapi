import { useActionState } from "react";
import PendingModal from "../common/pendingModal";
import ResultModal from "../common/resultModal";
import useCustomMove from "../../hooks/useCustomMove";
import jwtAxios from "../../util/jwtUtil";

interface ProductAddResult {
  result?: number;
  error?: string;
}

const initState: ProductAddResult = {
  result: 0,
};

const addAsyncAction = async (
  _state: ProductAddResult,
  formData: FormData
): Promise<ProductAddResult> => {
  console.log("addAsyncAction.........");

  await new Promise((resolve) => setTimeout(resolve, 2000));

  const pname = formData.get("pname") as string;

  if (!pname) {
    return { error: "Insert Product Name" };
  }

  const res = await jwtAxios.post("/api/products/", formData);

  console.log(res.data);

  return { result: res.data.result };
};

function AddComponent() {
  const [state, action, isPending] = useActionState(addAsyncAction, initState);
  const { moveToList } = useCustomMove();

  const closeModal = () => {
    moveToList();
  };

  return (
    <div className="bg-white border border-gray-200 rounded-2xl shadow-sm p-6">
      {isPending && <PendingModal />}

      {state.result != 0 && (
        <ResultModal
          title="상품 추가 결과"
          content={`새로운 ${state.result} 상품 추가됨`}
          callbackFn={closeModal}
        />
      )}

      <form action={action} className="space-y-5">
        <div>
          <label className="block text-sm font-semibold mb-2">
            Product Name
          </label>
          <input
            className="w-full px-4 py-3 rounded-xl border border-gray-300 shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-200"
            name="pname"
            required
          />
        </div>

        <div>
          <label className="block text-sm font-semibold mb-2">Desc</label>
          <textarea
            className="w-full px-4 py-3 rounded-xl border border-gray-300 shadow-sm resize-none focus:outline-none focus:ring-2 focus:ring-blue-200"
            name="pdesc"
            rows={5}
            required
          />
        </div>

        <div>
          <label className="block text-sm font-semibold mb-2">Price</label>
          <input
            className="w-full px-4 py-3 rounded-xl border border-gray-300 shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-200"
            name="price"
            type="number"
            required
          />
        </div>

        <div>
          <label className="block text-sm font-semibold mb-2">Files</label>
          <input
            className="w-full px-4 py-3 rounded-xl border border-gray-300 shadow-sm bg-white"
            type="file"
            name="files"
            multiple={true}
          />
        </div>

        <div className="flex justify-end">
          <button
            type="submit"
            className="rounded-xl px-6 py-3 bg-blue-500 text-white font-semibold hover:bg-blue-600 transition"
          >
            ADD
          </button>
        </div>
      </form>
    </div>
  );
}

export default AddComponent;