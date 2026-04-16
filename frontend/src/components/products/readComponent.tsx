import useCustomCart from "../../hooks/useCustomCart";
import useCustomMove from "../../hooks/useCustomMove";
import { getProductImage } from "../../util/imageUtil";

function ReadComponent({ product }: { product: ProductDTO }) {
  const { moveToList, moveToModify } = useCustomMove();
  const { changeCart, cartItems } = useCustomCart();

  const handleClickAddCart = () => {
    const addedItem = cartItems.items.filter(
      (item) => item.pno === product.pno
    )[0];

    if (addedItem) {
      if (
        window.confirm("이미 추가된 상품입니다. 추가하시겠습니까?") === false
      ) {
        return;
      }
    }

    if (addedItem) {
      changeCart(addedItem.cino, product.pno, 1);
    } else {
      changeCart(null, product.pno, 1);
    }
  };

  return (
    <div className="max-w-5xl mx-auto bg-white rounded-2xl shadow-sm border border-gray-200 p-6">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
        <div className="flex flex-col gap-4">
          {product.uploadFileNames && product.uploadFileNames.length > 0 ? (
            product.uploadFileNames.map((imgFile, i) => (
              <div
                key={i}
                className="w-full bg-gray-100 rounded-2xl overflow-hidden"
              >
                <img
                  alt="product"
                  className="w-full h-320px object-cover"
                  src={getProductImage(imgFile)}
                />
              </div>
            ))
          ) : (
            <div className="w-full bg-gray-100 rounded-2xl overflow-hidden">
              <img
                alt="default"
                className="w-full h-320px object-cover"
                src={getProductImage()}
              />
            </div>
          )}
        </div>

        <div className="flex flex-col justify-between">
          <div>
            <div className="text-sm text-gray-400 mb-2">
              Product No. {product.pno}
            </div>

            <h1 className="text-3xl font-bold mb-4">{product.pname}</h1>

            <div className="text-2xl font-semibold mb-6">
              {product.price.toLocaleString()}원
            </div>

            <div className="border-t border-b border-gray-200 py-6">
              <div className="text-sm font-semibold text-gray-500 mb-2">
                Description
              </div>
              <div className="text-base leading-7 text-gray-700 whitespace-pre-wrap">
                {product.pdesc}
              </div>
            </div>
          </div>

          <div className="flex flex-wrap gap-3 mt-8">
            <button
              type="button"
              className="inline-block rounded-xl px-5 py-3 text-white bg-green-500 hover:bg-green-600 transition"
              onClick={handleClickAddCart}
            >
              Add Cart
            </button>

            <button
              type="button"
              className="inline-block rounded-xl px-5 py-3 text-white bg-red-500 hover:bg-red-600 transition"
              onClick={() => moveToModify(product.pno)}
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
        </div>
      </div>
    </div>
  );
}

export default ReadComponent;