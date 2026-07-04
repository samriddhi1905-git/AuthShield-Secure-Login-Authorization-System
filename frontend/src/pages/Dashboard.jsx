import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../services/api";

function Dashboard() {

    const navigate = useNavigate();

    const [stats, setStats] = useState(null);

    useEffect(() => {

        const email = localStorage.getItem("email");

        if (!email) {
            navigate("/");
            return;
        }

        api.get("/auth/dashboard/stats")
            .then((response) => {
                console.log("Dashboard Stats:", response.data);
                setStats(response.data);
            })
            .catch((error) => {
                console.error("Dashboard Error:", error);
            });

    }, [navigate]);

    const logout = () => {

        localStorage.removeItem("jwt");
        localStorage.removeItem("email");

        navigate("/");

    };

    return (

        <div className="min-h-screen bg-slate-950 text-white">

            {/* Navbar */}

            <div className="bg-slate-900 shadow-lg p-5 flex justify-between items-center">

                <h1 className="text-2xl font-bold">
                    🔒 AuthShield Dashboard
                </h1>

                <div className="flex gap-3">

                    <button
                        onClick={() => navigate("/history")}
                        className="bg-cyan-500 hover:bg-cyan-600 px-4 py-2 rounded-lg transition"
                    >
                        Login History
                    </button>

                    <button
                        onClick={logout}
                        className="bg-red-500 hover:bg-red-600 px-4 py-2 rounded-lg transition"
                    >
                        Logout
                    </button>

                </div>

            </div>

            {/* Welcome */}

            <div className="p-10">

                <div className="bg-slate-900 rounded-2xl p-8 shadow-xl">

                    <h2 className="text-4xl font-bold">
                        Welcome 👋
                    </h2>

                    <p className="text-slate-400 mt-4 text-lg">
                        Your login has been approved successfully.
                    </p>

                </div>

                {/* Status */}

                <div className="grid grid-cols-1 md:grid-cols-3 gap-6 mt-8">

                    <div className="bg-slate-900 rounded-xl p-6 shadow-lg">

                        <h3 className="text-cyan-400 text-lg font-semibold">
                            Security
                        </h3>

                        <p className="mt-3 text-3xl">
                            ✅ Protected
                        </p>

                    </div>

                    <div className="bg-slate-900 rounded-xl p-6 shadow-lg">

                        <h3 className="text-cyan-400 text-lg font-semibold">
                            Authentication
                        </h3>

                        <p className="mt-3 text-3xl">
                            🔑 JWT Active
                        </p>

                    </div>

                    <div className="bg-slate-900 rounded-xl p-6 shadow-lg">

                        <h3 className="text-cyan-400 text-lg font-semibold">
                            Email Verification
                        </h3>

                        <p className="mt-3 text-3xl">
                            📧 Approved
                        </p>

                    </div>

                </div>

                {/* Statistics */}

                <div className="grid grid-cols-1 md:grid-cols-4 gap-6 mt-8">

                    <div className="bg-slate-900 rounded-xl p-6 text-center shadow-lg">

                        <h3 className="text-slate-400">
                            Total Logins
                        </h3>

                        <p className="text-4xl font-bold mt-3 text-cyan-400">
                            {stats?.total ?? "Loading..."}
                        </p>

                    </div>

                    <div className="bg-slate-900 rounded-xl p-6 text-center shadow-lg">

                        <h3 className="text-slate-400">
                            Approved
                        </h3>

                        <p className="text-4xl font-bold mt-3 text-green-400">
                            {stats?.approved ?? "Loading..."}
                        </p>

                    </div>

                    <div className="bg-slate-900 rounded-xl p-6 text-center shadow-lg">

                        <h3 className="text-slate-400">
                            Rejected
                        </h3>

                        <p className="text-4xl font-bold mt-3 text-red-400">
                            {stats?.rejected ?? "Loading..."}
                        </p>

                    </div>

                    <div className="bg-slate-900 rounded-xl p-6 text-center shadow-lg">

                        <h3 className="text-slate-400">
                            Pending
                        </h3>

                        <p className="text-4xl font-bold mt-3 text-yellow-400">
                            {stats?.pending ?? "Loading..."}
                        </p>

                    </div>

                </div>

            </div>

        </div>

    );

}

export default Dashboard;