import React from "react";
import MainHeader from "../Layout/MainHeader";
import HotelServices from "../common/HotelServices";
import Parallax from "../common/Parallax";
import RoomSearch from "../common/RoomSearch";
import { useLocation } from "react-router-dom";
import { useAuth } from "../Auth/AuthProvider";

const Home = () => {
  const location = useLocation();

  const message = location.state && location.state.message;
  const currentUser = localStorage.getItem("userId");
  return (
    <section>
      {message && <p className="text-warning px-5">{message}</p>}
      {currentUser && (
        <h6 className="text-success text-center">
          You are logged-In as {currentUser}
        </h6>
      )}
      <MainHeader />
      <section className="container">
        <RoomSearch />
        <HotelServices />
        <Parallax />
      </section>
    </section>
  );
};

export default Home;
