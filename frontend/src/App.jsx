import { BrowserRouter, Routes, Route } from "react-router-dom";

import Login from "./pages/Login";
import Register from "./pages/Register";
import Dashboard from "./pages/Dashboard";
import History from "./pages/History";

import WaitingApproval from "./pages/WaitingApproval";

function App() {

    return (

        <BrowserRouter>

            <Routes>

                <Route
                    path="/"
                    element={<Login />}
                />

                <Route
                    path="/register"
                    element={<Register />}
                />

                <Route
                    path="/dashboard"
                    element={<Dashboard />}
                />

                <Route
                    path="/history"
                    element={<History />}
                />

                <Route
    path="/waiting"
    element={<WaitingApproval />}
/>

            </Routes>

        </BrowserRouter>

    );

}

export default App;