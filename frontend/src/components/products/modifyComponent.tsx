import { useActionState, useState } from "react";
import useCustomMove from "../../hooks/useCustomMove";
import PendingModal from "../common/pendingModal";
import ResultModal from "../common/resultModal";
import jwtAxios from "../../util/jwtUtil";
import { getThumbnailImage } from "../../util/imageUtil";

interface ProductTaskResult {
  actionType: string;
  result?: string;
  error?: string;
}

const initState: ProductTaskResult = {
  actionType: "modify",
  result: "",
};

const modifyDeleteAsyncAction = async (
  state: ProductTaskResult,
  formData: FormData
): Promise<ProductTaskResult> => {
  const pno = formData.get("pno");
  const actionType = formData.get("actionType") as string;

  let res;

  if (actionType === "modify") {
    res = await jwtAxios.put(`http://localhost:8080/api/products/${pno}`, formData);
  } else if (actionType === "delete") {
    res = await jwtAxios.delete(`http://localhost:8080/api/products/${pno}`);
  }

  return { actionType: actionType, result: res?.data?.RESULT };
};

function ModifyComponent({ product }: { product: ProductDTO }) {
  const { moveToList, moveToRead } = useCustomMove();

  const [images, setImages] = useState<string[]>(product.uploadFileNames);

  const deleteOldImages = (
    event: React.MouseEvent<HTMLButtonElement>,
    target: string
  ) => {
    event.preventDefault();
    event.stopPropagation();
    setImages((prev) => prev.filter((img) => img !== target));
  };

  const [state, action, isPending] = useActionState(
    modifyDeleteAsyncAction,
    initState
  );

  return (
    <div className="bg-white border border-gray-200 rounded-2xl shadow-sm p-6">
      {isPending && <PendingModal />}

      {state.result && (
        <ResultModal
          title="처리완료"
          content="처리 완료"
          callbackFn={() => {
            if (state.actionType === "modify") {
              moveToRead(product.pno);
            }
            if (state.actionType === "delete") {
              moveToList();
            }
          }}
        />
      )}

      <form action={action} className="space-y-5">
        <div>
          <label className="block text-sm font-semibold mb-2">PNO</label>
          <input
            className="w-full px-4 py-3 rounded-xl border border-gray-300 shadow-sm bg-gray-50"
            name="pno"
            required
            defaultValue={product.pno}
          />
        </div>

        <div>
          <label className="block text-sm font-semibold mb-2">PNAME</label>
          <input
            className="w-full px-4 py-3 rounded-xl border border-gray-300 shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-200"
            name="pname"
            required
            defaultValue={product.pname}
          />
        </div>

        <div>
          <label className="block text-sm font-semibold mb-2">PRICE</label>
          <input
            className="w-full px-4 py-3 rounded-xl border border-gray-300 shadow-sm focus:outline-none focus:ring-2 focus:ring-blue-200"
            name="price"
            type="number"
            defaultValue={product.price}
          />
        </div>

        <div>
          <label className="block text-sm font-semibold mb-2">PDESC</label>
          <textarea
            className="w-full px-4 py-3 rounded-xl border border-gray-300 shadow-sm resize-none focus:outline-none focus:ring-2 focus:ring-blue-200"
            name="pdesc"
            rows={5}
            required
            defaultValue={product.pdesc}
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

        <div className="grid grid-cols-1 sm:grid-cols-2 gap-4">
          {images.map((imgFile, i) => (
            <div
              className="border border-gray-200 rounded-2xl overflow-hidden bg-gray-50"
              key={i}
            >
              <div className="w-full h-56 bg-gray-100">
                <img
                  alt="img"
                  className="w-full h-full object-cover"
                  src={getThumbnailImage(imgFile)}
                />
              </div>

              <div className="p-3">
                <button
                  className="w-full rounded-xl bg-red-500 text-white py-2 font-semibold hover:bg-red-600 transition"
                  onClick={(event) => deleteOldImages(event, imgFile)}
                >
                  DELETE
                </button>
                <input type="hidden" name="uploadFileNames" value={imgFile} />
              </div>
            </div>
          ))}
        </div>

        <div className="flex flex-wrap justify-end gap-3 pt-4">
          <button
            type="submit"
            name="actionType"
            value="delete"
            className="rounded-xl px-5 py-3 text-white bg-red-500 hover:bg-red-600 transition"
          >
            Delete
          </button>

          <button
            type="submit"
            name="actionType"
            value="modify"
            className="rounded-xl px-5 py-3 text-white bg-orange-500 hover:bg-orange-600 transition"
          >
            Modify
          </button>

          <button
            type="button"
            className="rounded-xl px-5 py-3 text-white bg-blue-500 hover:bg-blue-600 transition"
            onClick={() => moveToList()}
          >
            List
          </button>
        </div>
      </form>
    </div>
  );
}

export default ModifyComponent;