export interface ExpenseParticipant {
    id: number;
    expenseId?: number;
    userId: number;
    paidAmount: number;
    shareAmount: number;
}