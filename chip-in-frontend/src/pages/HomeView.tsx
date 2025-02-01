import { FC, useState } from "react";
import { Group } from "../types/Group";
import { Link } from "react-router-dom";

const HomeView: FC = () => {

    const [groups, setGroups] = useState<Group[]>([
        { id: 0, name: "School", createdAt: "..." },
        { id: 1, name: "College", createdAt: "..." },
        { id: 2, name: "Bangalore", createdAt: "..." },
    ])
    return (
        <div>
            <h1>Groups</h1>
            <ul>
                {groups.map(group => {
                    return (
                        <div key={group.id}>
                            <Link to="group" state={{ group }}>{group.name}</Link>
                            <br />
                        </div>
                    );
                })}
            </ul>
            <ul>
                <li>Groups will be shown here</li>
                <li>You can open these groups</li>
                <li>/groups with state passed to them</li>
                <li>/group/new</li>
            </ul>
        </div>
    );
}

export default HomeView;