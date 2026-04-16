export const IMAGE_HOST =
    "https://yujin-mall-upload-prod-seoul.s3.ap-northeast-2.amazonaws.com";

/** 원본 이미지 반환 */
export const getProductImage = (fileName?: string) => {
    if (!fileName || fileName.trim() === "") {
        return `${IMAGE_HOST}/default.jpeg`;
    }

    return `${IMAGE_HOST}/${fileName}`;
};

/** 썸네일 이미지 반환 */
export const getThumbnailImage = (fileName?: string) => {
    if (!fileName || fileName.trim() === "") {
        return `${IMAGE_HOST}/s_default.jpeg`;
    }

    return `${IMAGE_HOST}/s_${fileName}`;
};