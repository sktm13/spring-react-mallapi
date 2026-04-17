import jwtAxios from "../util/jwtUtil"

// 단건 조회
export const getOne = async (tno: number | string) => {
    const res = await jwtAxios.get(`/api/todo/${tno}`)
    return res.data
}

// 리스트
export const getList = async (pageParam: PageParam) => {
    const res = await jwtAxios.get(`/api/todo/list`, { params: pageParam })
    return res.data
}

// 등록
export const postAdd = async (todoObj: TodoAdd) => {
    const res = await jwtAxios.post(`/api/todo/`, todoObj)
    return res.data
}

// 삭제
export const deleteOne = async (tno: number) => {
    const res = await jwtAxios.delete(`/api/todo/${tno}`)
    return res.data
}

// 수정
export const putOne = async (todo: TodoModify) => {
    const res = await jwtAxios.put(`/api/todo/${todo.tno}`, todo)
    return res.data
}