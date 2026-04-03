import { createRoot } from 'react-dom/client'
import './index.css'
import { RouterProvider } from 'react-router';
import router from './router/root';
import { Provider } from 'react-redux';
import store from './store';




createRoot(document.getElementById('root')!).render(
  //리액트라우터 전용, 이제 app.tsx 쓰지 않을 것이다.

  <Provider store ={store}>
    <RouterProvider router={router}></RouterProvider>,
  </Provider>

)
