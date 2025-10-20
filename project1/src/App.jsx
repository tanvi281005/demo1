import { BrowserRouter as Router, Routes, Route } from "react-router-dom";
import Home from "./pages/Home";
import TransportServices from "./pages/TransportServices";
import DailyCommute from "./pages/DailyCommute";
import UrgentForm from "./pages/UrgentForm";
import BuySellHub from "./pages/BuySellHub";
import Buy from "./pages/Buy"
import Sell from "./pages/Sell"
import Resources from "./pages/Resources";
import UploadResourceForm from "./pages/UploadResourceForm";
import StudentRegister from "./pages/StudentRegister";
import StudentLogin from "./pages/StudentLogin";
// import Home from "./pages/Home.jsx"
import RecipesPage from "./pages/RecipesPage";
import Layout from "./pages/Layout";
import MentalWellness from "./pages/MentalWellness"
import FindSupport from "./pages/FindSupport"
import CounsellorDetails from "./pages/CounsellorDetails";
import CategoryPage from "./pages/Categorypage";
import ProfilePage from "./pages/ProfilePage"
import AcademicResources from "./pages/FindResources";
import ProductDetail from "./pages/ProductDetail";
import BuySellProfile from "./pages/BuySellProfile";



function App() {
  return (
    <Router>
      <Routes>
        <Route element={<Layout />}>
        
        
        <Route path="/home" element={<Home />} />
        
        
        </Route>
        <Route path="/" element={<StudentRegister />} />
        <Route path="/api/login" element={<StudentLogin />} />
        <Route path="/transport" element={<TransportServices />} />
        <Route path="/daily-commute" element={<DailyCommute />} />
        <Route path="/urgent" element={<UrgentForm />} />
        <Route path="/buysellpage" element={<BuySellHub />} />
        <Route path="/buy" element={<Buy />} />
        <Route path="/sell" element={<Sell />} />
        <Route path="/academic" element={<Resources />} />
        <Route path="/upload" element={<UploadResourceForm />} />
        <Route path="/wellness" element={<MentalWellness />} />
        <Route path="/support" element={<FindSupport />} />
        <Route path="/counsellor/:id" element={<CounsellorDetails />} />
        <Route path="/food" element={<RecipesPage/>} />
        <Route path="/category/:category" element={<CategoryPage />} />
        <Route path="/profile" element={<ProfilePage/>}/>

        <Route path="/find-resources" element={<AcademicResources/>}/>

        <Route path="/product/:id" element={<ProductDetail />} />
        <Route path="/buysellprofile" element={<BuySellProfile />} />

      </Routes>
    </Router>
  );
}

export default App;
