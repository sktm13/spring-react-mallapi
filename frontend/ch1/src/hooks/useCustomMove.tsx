import { useState } from "react"
import { createSearchParams, useNavigate, useSearchParams } from "react-router"

function useCustomMove():UseCustomMoveReturn {

    const navigate = useNavigate()
    const [queryParams] = useSearchParams()

    const[refresh, setRefresh] = useState<boolean>(false)


    const pageStr: string | null = queryParams.get('page')
    const sizeStr: string | null = queryParams.get('size')

    const page: number = pageStr ? Number(pageStr) : 1
    const size: number = sizeStr ? Number(sizeStr) : 10

    //page=1&size=10
    const queryDefault = createSearchParams({
        page: String(page),
        size: String(size),
    }).toString()

    const moveToModify = (tno: number) => {
        navigate({ pathname: `../modify/${tno}`, search: queryDefault })
    }

    const moveToRead = (tno: number) => {
        navigate({ pathname: `../read/${tno}`, search: queryDefault })
    }


    //원하는 페이지 링크 생성, 없다면 url주소에 있는 것 사용
    const moveToList = (pageParam?: PageParam) => {
        let queryStr = ''
        if (pageParam) {
            const pageNum = Number(pageParam.page) || 1
            const sizeNum = Number(pageParam.size) || 10
            queryStr = createSearchParams({
                page: String(pageNum),
                size: String(sizeNum),
            }).toString()

            //동일페이지 클릭 (누른 것 == 현재 것)하면 setRefresh를 반대로 바꿈
            if(queryStr === queryDefault) {
                setRefresh(!refresh)
            }

        } else {
            queryStr = queryDefault
        }
        navigate({ pathname: `../list`, search: queryStr })
    }

    return ({page, size, moveToList, refresh, moveToModify, moveToRead})
}

export default useCustomMove;