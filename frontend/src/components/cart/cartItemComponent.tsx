import { getThumbnailImage } from "../../util/imageUtil";

function CartItemComponent({
  cartItem,
  changeCart,
}: {
  cartItem: CartItemResponse;
  changeCart: (cino: number | null, pno: number, amount: number) => void;
}) {
  const { cino, pno, pname, price, qty, imageFile } = cartItem;

  return (
    <li className="w-full border border-gray-200 rounded-xl p-4 bg-white shadow-sm">
      <div className="flex items-center gap-4 w-full">

        {/* 이미지 */}
        <div className="w-20 h-20 flex-shrink-0 bg-gray-100 rounded-lg overflow-hidden">
          <img
            src={getThumbnailImage(imageFile)}
            className="w-full h-full object-cover"
          />
        </div>

        {/* 가운데 영역 */}
        <div className="flex-1 min-w-0">

          {/* 이름 */}
          <div className="font-semibold truncate">
            {pname}
          </div>

          {/* 가격 */}
          <div className="text-sm text-gray-500 mt-1">
            {price.toLocaleString()}원
          </div>

          {/* 수량 */}
          <div className="flex items-center gap-2 mt-2">
            <button
              className="w-7 h-7 bg-gray-200 rounded hover:bg-gray-300"
              onClick={() => changeCart(cino, pno, -1)}
            >
              -
            </button>

            <div className="px-2">{qty}</div>

            <button
              className="w-7 h-7 bg-gray-200 rounded hover:bg-gray-300"
              onClick={() => changeCart(cino, pno, 1)}
            >
              +
            </button>
          </div>
        </div>

        {/* 오른쪽 영역 */}
        <div className="flex flex-col items-end justify-between h-full flex-shrink-0">

          <button
            className="text-xs text-red-500 hover:underline"
            onClick={() => changeCart(cino, pno, -qty)}
          >
            삭제
          </button>

          <div className="font-bold text-lg">
            {(price * qty).toLocaleString()}원
          </div>
        </div>

      </div>
    </li>
  );
}

export default CartItemComponent;