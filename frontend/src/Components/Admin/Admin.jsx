import React from "react";
import { Link } from "react-router-dom";
import Header from "../common/Header";

const Admin = () => {
  return (
    <section className="container" mt-5>
      <Header title="Welcome to Admin Panel" />
      <hr />
      <Link to="/existing-rooms">Manage Rooms</Link>
      <p></p>
      <Link to="/existing-bookings">Manage Bookings</Link>
    </section>
  );
};

export default Admin;
