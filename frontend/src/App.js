import { Routes, Route } from "react-router-dom";
import AddRoom from "./Components/Room/AddRoom";
import ExistingRoom from "./Components/Room/ExistingRoom";
import EditRoom from "./Components/Room/EditRoom";
import Home from "./Components/Home/Home";
import Footer from "./Components/Layout/Footer";
import NavBar from "./Components/Layout/NavBar";
import RoomListing from "./Components/Room/RoomListing";
import Admin from "./Components/Admin/Admin";
import Checkout from "./Components/Booking/Checkout";
import BookingStatus from "./Components/Booking/BookingStatus";
import Bookings from "./Components/Booking/Bookings";
import FindBooking from "./Components/Booking/FindBooking";
import Login from "./Components/Auth/Login";
import Registration from "./Components/Auth/Registration";
import Profile from "./Components/Auth/Profile";
import Logout from "./Components/Auth/Logout";
import { AuthProvider } from "./Components/Auth/AuthProvider";
import RequireAuth from "./Components/Auth/RequireAuth";

function App() {
  return (
    <AuthProvider>
      <div>
        <NavBar />
        <Routes>
          <Route path="/" element={<Home />} />
          <Route path="/existing-rooms" element={<ExistingRoom />} />
          <Route path="/add-room" element={<AddRoom />} />
          <Route path="/edit-room/:roomId" element={<EditRoom />} />
          <Route path="/browse-all-rooms" element={<RoomListing />} />
          <Route path="/admin" element={<Admin />} />

          <Route
            path="/book-room/:roomId"
            element={
              <RequireAuth>
                <Checkout />
              </RequireAuth>
            }
          />
          <Route path="/booking-status" element={<BookingStatus />} />
          <Route path="/existing-bookings" element={<Bookings />} />
          <Route path="/find-booking" element={<FindBooking />} />
          <Route path="/login" element={<Login />} />
          <Route path="/register" element={<Registration />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/logout" element={<Logout />} />
        </Routes>
        <Footer />
      </div>
    </AuthProvider>
  );
}

export default App;
