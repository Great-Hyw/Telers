import axios from 'axios'
import router from '@/router'
import { ElMessage } from "element-plus";
const axio = axios.create({
    baseURL: '/api',
    timeout: 50000
})
//拦截回复
axio.interceptors.response.use(
    response => {
        if (response.status == 401) {
            router.push('/login');
            ElMessage("请先登录！");
        }
        if(response.data.code==0){
            ElMessage(response.data.mes);
        }
        return response.data
    },
    error => {
        console.error('请求错误:', error)
        return Promise.reject(error)
    }
)
//拦截请求
axio.interceptors.request.use(
    config => {
        const token=localStorage.getItem("token");
        config.headers.token = token;
        console.log(config.headers.token);
        
        return config
    }


)
export default axio