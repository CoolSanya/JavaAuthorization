import { lazy, Suspense } from "react";
import { Route, Routes } from "react-router";
import './App.css';

import AdminLayout from "./components/containers/AdminLayout";
import UsersListPage from "./components/admin/users/List";

const DefaultLayout = lazy(()=>import("./components/containers/DefaultLayout"));
const HomePage = lazy(()=>import("./components/Home"));

function App() {
  return (
    <>
      <Routes>
        <Route
          path="/"
          element={
            <Suspense fallback={<div>Loading ...</div>}>
              <DefaultLayout />
            </Suspense>
          }
        >
          <Route
            index
            element={
              <Suspense fallback={<div>Loading ...</div>}>
                <HomePage />
              </Suspense>
            }
          />
        </Route>

        <Route path="/admin" element={<AdminLayout/>}>
          <Route index element={<UsersListPage />} />
        </Route>
      </Routes>
    </>
  );
}

export default App;
