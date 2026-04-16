import useCustomMove from "../../hooks/useCustomMove";
import { getThumbnailImage } from "../../util/imageUtil";
import PageComponent from "../common/pageComponent";

function ListComponent({
  serverData,
}: {
  serverData: PageResponseDTO<ProductDTO>;
}) {
  const { moveToList, moveToRead } = useCustomMove();

  return (
    <div className="px-6 pb-6">
      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 mt-6">
        {serverData.dtoList.map((product) => {
          const fileName = product.uploadFileNames?.[0];

          return (
            <div
              key={product.pno}
              onClick={() => moveToRead(product.pno)}
              className="cursor-pointer rounded-2xl border border-gray-200 bg-white shadow-sm hover:shadow-lg transition overflow-hidden"
            >
              <div className="w-full h-60 bg-gray-100 flex items-center justify-center overflow-hidden">
                <img
                  alt="product"
                  className="w-full h-full object-cover"
                  src={getThumbnailImage(fileName)}
                />
              </div>

              <div className="p-4">
                <div className="text-xs text-gray-400 mb-2">
                  Product No. {product.pno}
                </div>

                <div className="text-lg font-bold mb-2 truncate">
                  {product.pname}
                </div>

                <div className="text-xl font-semibold text-black">
                  {product.price.toLocaleString()}원
                </div>
              </div>
            </div>
          );
        })}
      </div>

      <div className="mt-8">
        <PageComponent serverData={serverData} movePage={moveToList} />
      </div>
    </div>
  );
}

export default ListComponent;