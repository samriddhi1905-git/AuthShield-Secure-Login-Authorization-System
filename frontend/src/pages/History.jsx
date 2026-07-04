import { useEffect, useState } from "react";
import api from "../services/api";
import { useNavigate } from "react-router-dom";

function History() {

    const navigate = useNavigate();

    const [history, setHistory] = useState([]);

    useEffect(() => {

        const email = localStorage.getItem("email");

        if (!email) {
            navigate("/");
            return;
        }

        api.get(`/history/${email}`)
            .then((response) => {
                setHistory(response.data);
            })
            .catch((error) => {
                console.log(error);
            });

    }, [navigate]);

    return (

        <div className="min-h-screen bg-slate-950 text-white">

            {/* Navbar */}

            <div className="bg-slate-900 p-5 flex justify-between items-center">

                <h1 className="text-2xl font-bold">
                    Login History
                </h1>

                <button
                    onClick={() => navigate("/dashboard")}
                    className="bg-cyan-500 hover:bg-cyan-600 px-4 py-2 rounded-lg transition"
                >
                    Dashboard
                </button>

            </div>

            {/* Table */}

            <div className="p-8 overflow-x-auto">

                <table className="w-full border border-slate-700">

                    <thead>

                        <tr className="bg-slate-800">

                            <th className="p-3 border">Email</th>
                            <th className="p-3 border">IP Address</th>
                            <th className="p-3 border">Browser</th>
                            <th className="p-3 border">Status</th>
                            <th className="p-3 border">Login Time</th>

                        </tr>

                    </thead>

                    <tbody>

                        {history.length > 0 ? (

                            history.map((item) => (

                                <tr key={item.id} className="text-center hover:bg-slate-900">

                                    <td className="border p-3">
                                        {item.email}
                                    </td>

                                    <td className="border p-3">
                                        {item.ipAddress}
                                    </td>

                                    <td className="border p-3">
                                        {item.browser}
                                    </td>

                                    <td className="border p-3">

                                        <span
                                            className={`px-3 py-1 rounded-full font-bold text-white ${
                                                item.status === "APPROVED"
                                                    ? "bg-green-600"
                                                    : item.status === "REJECTED"
                                                    ? "bg-red-600"
                                                    : "bg-yellow-500 text-black"
                                            }`}
                                        >
                                            {item.status}
                                        </span>

                                    </td>

                                    <td className="border p-3">
                                        {new Date(item.loginTime).toLocaleString()}
                                    </td>

                                </tr>

                            ))

                        ) : (

                            <tr>

                                <td
                                    colSpan="5"
                                    className="text-center p-6 text-slate-400"
                                >
                                    No login history found.
                                </td>

                            </tr>

                        )}

                    </tbody>

                </table>

            </div>

        </div>

    );

}

export default History;