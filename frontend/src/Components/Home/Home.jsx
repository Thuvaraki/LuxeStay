import React from "react";
import MainHeader from "../Layout/MainHeader";
import HotelServices from "../common/HotelServices";
import Parallax from "../common/Parallax";
import RoomSearch from "../common/RoomSearch";

const Home = () => {
  return (
    <section>
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
