import { FC, useEffect, useState } from "react";
import { OwedAmount } from "../types/OwedAmount";
import { Expense } from "../types/Expense";
import { Link, useLocation, useNavigate } from "react-router-dom";
import { Group } from "../types/Group";

const GroupView: FC = () => {

    // A - 600 -- -400
    // B - 0 -- 200
    // C - 0 -- 100
    // D - 0 -- 100

    // useEffect -- owedAmounts and expenses
    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        if (!location.state)
            navigate("/");
    }, [navigate]);

    const group: Group = location.state.group;


    const [owedAmounts, setOwedAmount] = useState<OwedAmount[]>([
        { id: 0, payer: "Arun", payee: "Akash", amount: 200 },
        { id: 1, payer: "Veena", payee: "Akash", amount: 200 },
        { id: 2, payer: "Chloe", payee: "Veena", amount: 100 },
    ]);

    const [expenses, setExpenses] = useState<Expense[]>([
        { id: 0, description: "Tea", createdAt: "..." }, // groupId is optional and will be same fpr all expense
        { id: 1, description: "Beer", createdAt: "..." }
    ]);

    return (
        <div>
            <h2>{group.name}</h2>
            <ul>
                {owedAmounts.map(owedAmount =>
                    <li key={owedAmount.id}>{owedAmount.payer} owes {owedAmount.payee} Rs. {owedAmount.amount}</li>
                )}
            </ul>
            <h3>Expenses</h3>
            <ul>
                {expenses.map(expense =>
                    <div key={expense.id}>
                        <Link to={"expense"} state={{ expense: expense }}>{expense.description}</Link>
                    </div>
                )}
            </ul>
        </div>
    );
}

export default GroupView;