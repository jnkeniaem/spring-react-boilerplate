import { Suspense } from "react";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import LoadingAnimation from "./components/LoadingAnimation";
import Layout from "./pages/Layout";
import LoginPage from "./pages/LoginPage";
import HomePage from "./pages/HomePage";

function App(): React.ReactElement {
  return (
    <BrowserRouter>
      <Suspense fallback={<LoadingAnimation />}>
        <Routes>
          <Route path="/" element={<Layout />}>
            <Route path="login" element={<LoginPage />} />
            <Route path="home" element={<HomePage />} />
            {/* <Route path="profile/log" element={<LogPage />} />
            <Route path="profile" element={<ProfilePage />} />
            <Route path="pending" element={<PendingPage />} /> */}
          </Route>
          <Route path="/*" element={<div>404</div>} />
        </Routes>
      </Suspense>
    </BrowserRouter>
  );
}

export default App;
