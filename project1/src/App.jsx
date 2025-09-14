import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import TransportServices from "./pages/TransportServices";
import DailyCommute from "./pages/DailyCommute";
import UrgentForm from "./pages/UrgentForm";


function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/transport" element={<TransportServices />} />
        <Route path="/daily-commute" element={<DailyCommute />} />
        <Route path="/urgent" element={<UrgentForm />} />
      </Routes>
    </Router>
  );
}

export default App;
