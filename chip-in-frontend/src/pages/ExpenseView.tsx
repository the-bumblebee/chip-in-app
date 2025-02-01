// import { useState } from "react";

import { FC, useEffect, useState } from "react";
import { useLocation, useNavigate } from "react-router-dom";
import { ExpenseParticipant } from "../types/ExpenseParticipant";
import { Expense } from "../types/Expense";


const ExpenseView: FC = () => {
    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        if (location.state === null) {
            navigate("/");
        }
    }, []);

    if (location.state === null) {
        return null;
    }

    // 0 - A
    // 1 - B
    // 2 - C
    // 3 - D
    const expense: Expense = location.state.expense;
    const [expenseParticipants, setExpenseParticipants] = useState<ExpenseParticipant[]>([
        { id: 0, userId: 0, paidAmount: 600, shareAmount: 200 },
        { id: 0, userId: 1, paidAmount: 0, shareAmount: 200 },
        { id: 0, userId: 2, paidAmount: 0, shareAmount: 200 },
    ]);

    const totalPaidAmount: number = expenseParticipants.filter(expenseParticipant => expenseParticipant.paidAmount != 0)
        .reduce((sumAmount, participantB) => {
            return sumAmount + participantB.paidAmount
        }, 0);
    const peoplePaid: number[] = expenseParticipants.filter(expenseParticipant => expenseParticipant.paidAmount != 0)
        .map(participant => participant.id);

    return (
        <div>
            <h1>{expense.description}</h1>
            <div>
                {peoplePaid.join(", ")} paid Rs. {totalPaidAmount}.
            </div>
            <ul>
                {expenseParticipants.map(expenseParticipant => {
                    return (
                        <li key={expenseParticipant.userId}>{expenseParticipant.userId} owes {expenseParticipant.shareAmount}</li>
                    );
                })}
            </ul>
        </div>
    );
}

export default ExpenseView;