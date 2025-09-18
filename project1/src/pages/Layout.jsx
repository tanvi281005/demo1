// export default function Layout() {
//   return (
//     <>
//       <Navbar />
//       <div style={{ paddingTop: "70px", padding: "20px" }}>
//   <Outlet />
// </div>

//     </>
//   );
// }
// Layout.js (replace the wrapper div)
import { Outlet } from "react-router-dom";
import Navbar from "./Navbar";

export default function Layout() {
  return (
    <>
      <Navbar />
      <div className="page-wrapper">
        <Outlet />
      </div>
    </>
  );
}


