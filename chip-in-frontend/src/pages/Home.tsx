import { FC } from "react";


const Home: FC = () => {
    return (
        <div>
            <h1>Home Page</h1>
            <ul>
                <li>Groups will be shown here</li>
                <li>You can open these groups</li>
                <li>/groups with state passed to them</li>
                <li>/group/new</li>
            </ul>
        </div>
    );
}

export default Home;