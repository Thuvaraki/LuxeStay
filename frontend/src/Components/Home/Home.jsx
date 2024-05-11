import React from "react";
import MainHeader from "../Layout/MainHeader";
import HotelServices from "../common/HotelServices";
import Parallax from "../common/Parallax";

const Home = () => {
  return (
    <section>
      <MainHeader />
      <section className="container">
        <HotelServices />
        <Parallax />
      </section>
    </section>
  );
};

export default Home;
