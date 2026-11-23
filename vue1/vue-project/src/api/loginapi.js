import axio from "../post/post"



const loginapi = async (mes) => {
    const result = await axio.post('/login', mes)
    return result
}


const addloginapi = async (mes) => {
    const result = await axio.post('/addlogin', mes)
    return result;
}

const chataiapi = async (mes) => {
    const result = await axio.post('/chatai', mes)
    return result;
}
const gethabitdapi = async (mes) => {
    const result = await axio.get('/gethabitd')
    return result;
}
const addhabitdapi = async (mes) => {
    const result = await axio.post('/addhabitd', mes)
    return result;
}

const deletehabitsapi = async (id) => {
    const result = await axio.post('deltethabit', id);
    return result;
}

export {deletehabitsapi}
export { loginapi }
export { addloginapi }
export { chataiapi }
export { gethabitdapi }
export { addhabitdapi }