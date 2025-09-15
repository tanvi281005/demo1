import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import TransportServices from "./pages/TransportServices";
import DailyCommute from "./pages/DailyCommute";
import UrgentForm from "./pages/UrgentForm";
import BuySellHub from "./pages/BuySellHub";
import Buy from "./pages/Buy"
import Electronics from "./pages/Electronics"
import Stationery from "./pages/Stationery"
import Vehicles from "./pages/Vehicles"
import Coats from "./pages/Coats"
import Miscellaneous from "./pages/Miscellaneous"
import Sell from "./pages/Sell"
import Resources from "./pages/Resources";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/transport" element={<TransportServices />} />
        <Route path="/daily-commute" element={<DailyCommute />} />
        <Route path="/urgent" element={<UrgentForm />} />
        <Route path="/buysellpage" element={<BuySellHub />} />
        <Route path="/buy" element={<Buy />} />
        <Route path="/electronics" element={<Electronics />} />
        <Route path="/stationery" element={<Stationery />} />
        <Route path="/vehicles" element={<Vehicles />} />
        <Route path="/coats" element={<Coats />} />
        <Route path="/miscellaneous" element={<Miscellaneous />} />
        <Route path="/sell" element={<Sell />} />
        <Route path="/academic" element={<Resources />} />
      </Routes>
    </Router>
  );
}

export default App;
