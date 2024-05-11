import { Routes, Route } from "react-router-dom";
import AddRoom from "./Components/Room/AddRoom";
import ExistingRoom from "./Components/Room/ExistingRoom";
import EditRoom from "./Components/Room/EditRoom";
import Home from "./Components/Home/Home";
import Footer from "./Components/Layout/Footer";
import NavBar from "./Components/Layout/NavBar";
import RoomListing from "./Components/Room/RoomListing";
import Admin from "./Components/Admin/Admin";

function App() {
  return (
    <div>
      <NavBar />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/existing-rooms" element={<ExistingRoom />} />
        <Route path="/add-room" element={<AddRoom />} />
        <Route path="/edit-room/:roomId" element={<EditRoom />} />
        <Route path="/browse-all-rooms" element={<RoomListing />} />
        <Route path="/admin" element={<Admin />} />
      </Routes>
      <Footer />
    </div>
  );
}

export default App;
