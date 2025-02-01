import { Link, Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import { FC } from 'react';
import HomeView from './pages/HomeView';
import GroupView from './pages/GroupView';
import ExpenseView from './pages/ExpenseView';
import NotFoundView from './pages/NotFoundView';

const ChipInApp: FC = () => {
    return (
        <Router>
            <nav>
                <Link to="/">Home</Link>
            </nav>
            <div>
                <Routes>
                    <Route path="/" element={<HomeView />} />
                    <Route path="/group" element={<GroupView />} />
                    <Route path="/group/expense" element={<ExpenseView />} />
                    <Route path="*" element={<NotFoundView />} />
                </Routes>
            </div>
        </Router>
    );
}

export default ChipInApp;