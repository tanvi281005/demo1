import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
// import Transport from "./pages/Transport";
// import Wellbeing from "./pages/Wellbeing";
// import Market from "./pages/Market";
// import Food from "./pages/Food";
// import Academic from "./pages/Academic";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        {/* <Route path="/transport" element={<Transport />} />
        <Route path="/wellbeing" element={<Wellbeing />} />
        <Route path="/market" element={<Market />} />
        <Route path="/food" element={<Food />} />
        <Route path="/academic" element={<Academic />} /> */}
      </Routes>
    </Router>
  );
}

export default App;
