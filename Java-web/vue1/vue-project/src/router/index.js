import { createRouter, createWebHistory } from 'vue-router'
import loginview from '@/components/icons/login.vue'
import indexview from '@/components/icons/index.vue'
import aiview from '@/components/icons/ai.vue'
import home from '@/components/icons/home.vue'
import setting from '@/components/icons/setting.vue'
import work from '@/components/icons/work.vue'
import { ElMessage } from 'element-plus'
import homeindex from '@/components/icons/homeindex.vue'
import { h } from 'vue'
import tomato from'@/components/icons/tomato.vue'
import a from "@/components/icons/AAAA.vue"
const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'main',
      redirect: '/login'

    },
    {
      path: '/login',
      name: 'login',
      component: loginview

    },
    {
      path: '/homeindex',
      name: h,
      component: homeindex,
      children: [
        {
          path: '',
          component: indexview
        },
        {
          path: '/index',
          name: 'index',
          component: indexview,
          meta: { k: true }
        },

        {
          path: '/ai',
          name: 'ai',
          component: aiview,

        },
        {
          path: '/setting',
          name: 'setting',
          component: setting,
          children:[{
            path:'/tomato',
            component:tomato
          },
        ]

        },
        {
          path: '/working',
          name: 'work',
          component: work,

        } 
        ,{
      path: '/things',
      component: a
    }

      ],
      meta: { k: true }
    },

   

  ]
}

)

router.beforeEach((to, from, next) => {

  if (to.meta.k) {//需要登录验证的组件
    const token = localStorage.getItem("token");
    const now = Date.now();
    const time = 1800000;
    const savetime = localStorage.getItem("savetime");
    if ((token && savetime) && (now - savetime) < time) {//有savetime,token已经登录了,且没过期

      next();

    } else {//过期
      ElMessage("登录已过期或请重新登录！");
      localStorage.clear();
      next('/login');
    }

  }
  next();


})

export default router
