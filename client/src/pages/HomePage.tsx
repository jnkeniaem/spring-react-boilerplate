import { useNavigate } from "react-router-dom";

const HomePage = () => {
  const navigator = useNavigate();

  return (
    <div>
      <h1>Home</h1>
      <button onClick={() => navigator("/login")}>Login</button>
    </div>
  );
};

export default HomePage;
