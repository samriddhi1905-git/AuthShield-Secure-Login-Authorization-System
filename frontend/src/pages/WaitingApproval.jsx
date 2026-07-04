import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

function WaitingApproval() {

    const navigate = useNavigate();

    useEffect(() => {

        const checkApproval = async () => {

            const jwt = localStorage.getItem("jwt");

            if (jwt) {
                navigate("/dashboard");
            }

        };

        const interval = setInterval(checkApproval, 2000);

        return () => clearInterval(interval);

    }, [navigate]);

    return (

        <div className="min-h-screen bg-slate-950 flex justify-center items-center">

            <div className="bg-slate-900 p-10 rounded-2xl shadow-xl w-[450px] text-center">

                <div className="text-6xl">
                    📧
                </div>

                <h1 className="text-3xl font-bold text-white mt-5">
                    Waiting For Approval
                </h1>

                <p className="text-slate-400 mt-4">
                    We have sent a login approval email to your registered email address.
                </p>

                <p className="text-slate-400 mt-3">
                    Please approve the login before continuing.
                </p>

                <div className="mt-6">

                    <div className="animate-spin rounded-full h-10 w-10 border-b-2 border-cyan-400 mx-auto"></div>

                    <p className="text-cyan-400 mt-4">
                        Waiting for approval...
                    </p>

                </div>

                <button
                    onClick={() => navigate("/")}
                    className="mt-8 bg-cyan-500 hover:bg-cyan-600 px-6 py-3 rounded-lg text-white"
                >
                    Back To Login
                </button>

            </div>

        </div>

    );

}

export default WaitingApproval;