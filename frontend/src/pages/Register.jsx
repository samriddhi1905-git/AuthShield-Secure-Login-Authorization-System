import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../services/api";

function Register() {

    const navigate = useNavigate();

    const [name, setName] = useState("");
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");

    const [message, setMessage] = useState("");

    const handleRegister = async () => {

        if (!name || !email || !password) {
            setMessage("Please fill all fields.");
            return;
        }

        try {

            const response = await api.post("/auth/register", {
                name,
                email,
                password
            });

            setMessage(response.data);

            setTimeout(() => {
                navigate("/");
            }, 2000);

        } catch (error) {

            if (error.response) {
                setMessage(error.response.data);
            } else {
                setMessage("Server not reachable");
            }

        }
    };

    return (

        <div className="min-h-screen bg-slate-950 flex items-center justify-center">

            <div className="w-full max-w-md rounded-2xl bg-slate-900 border border-slate-700 p-8 shadow-2xl">

                <h1 className="text-4xl font-bold text-white text-center">
                    AuthShield 🔒
                </h1>

                <p className="text-slate-400 text-center mt-2">
                    Create Your Account
                </p>

                <div className="mt-8">

                    <label className="text-slate-300">
                        Name
                    </label>

                    <input
                        type="text"
                        placeholder="Enter Name"
                        className="w-full mt-2 p-3 rounded-lg bg-slate-800 text-white border border-slate-700 outline-none focus:border-cyan-500"
                        value={name}
                        onChange={(e)=>setName(e.target.value)}
                    />

                    <label className="text-slate-300 mt-5 block">
                        Email
                    </label>

                    <input
                        type="email"
                        placeholder="Enter Email"
                        className="w-full mt-2 p-3 rounded-lg bg-slate-800 text-white border border-slate-700 outline-none focus:border-cyan-500"
                        value={email}
                        onChange={(e)=>setEmail(e.target.value)}
                    />

                    <label className="text-slate-300 mt-5 block">
                        Password
                    </label>

                    <input
                        type="password"
                        placeholder="Enter Password"
                        className="w-full mt-2 p-3 rounded-lg bg-slate-800 text-white border border-slate-700 outline-none focus:border-cyan-500"
                        value={password}
                        onChange={(e)=>setPassword(e.target.value)}
                    />

                    <button
                        onClick={handleRegister}
                        className="w-full mt-8 bg-cyan-500 hover:bg-cyan-600 text-white font-semibold py-3 rounded-lg transition"
                    >
                        Create Account
                    </button>

                    {message && (
                        <div className="mt-5 text-center text-green-400">
                            {message}
                        </div>
                    )}

                    <p className="text-center text-slate-400 mt-6">
                        Already have an account?
                        <Link
                            to="/"
                            className="text-cyan-400 ml-2"
                        >
                            Login
                        </Link>
                    </p>

                </div>

            </div>

        </div>

    );
}

export default Register;