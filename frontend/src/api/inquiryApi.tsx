import jwtAxios from "../util/jwtUtil"

// 단건 조회
export const getOne = async (ino: number | string) => {
    const res = await jwtAxios.get(`/api/inquiry/${ino}`)
    return res.data
}

// 리스트 (페이징 + 검색)
export const getList = async (pageParam: PageParam) => {
    const res = await jwtAxios.get(`/api/inquiry/list`, { params: pageParam })
    return res.data
}

// 등록
export const postAdd = async (inquiry: InquiryAdd) => {
    const res = await jwtAxios.post(`/api/inquiry/`, inquiry)
    return res.data
}

// 삭제
export const deleteOne = async (ino: number) => {
    const res = await jwtAxios.delete(`/api/inquiry/${ino}`)
    return res.data
}

// 수정
export const putOne = async (inquiry: InquiryModify) => {
    const res = await jwtAxios.put(`/api/inquiry/${inquiry.ino}`, inquiry)
    return res.data
}

// 관리자 답변
export const putReply = async (ino: number, replyObj: InquiryReply) => {
    const res = await jwtAxios.put(`/api/inquiry/reply/${ino}`, replyObj)
    return res.data
}