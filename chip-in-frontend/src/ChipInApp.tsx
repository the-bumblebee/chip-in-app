import { Link, Route, BrowserRouter as Router, Routes } from 'react-router-dom';
import Group from './pages/Group';
import { FC } from 'react';
import NotFound from './pages/NotFound';
import Expense from './pages/Expense';
import Home from './pages/Home';

const ChipInApp: FC = () => {
    return (
        <Router>
            <nav>
                <Link to="/">Home</Link>
                <Link to="/group">Group</Link>
            </nav>
            <div>
                <Routes>
                    <Route path="/" element={<Home />} />
                    <Route path="/group" element={<Group />} />
                    <Route path="/group/expense" element={<Expense />} />
                    <Route path="*" element={<NotFound />} />
                </Routes>
            </div>
        </Router>
    );
}

export default ChipInApp;