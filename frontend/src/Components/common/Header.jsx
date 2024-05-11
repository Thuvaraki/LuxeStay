import React from "react";

const Header = ({ title }) => {
  return (
    <header className="header">
      <div className="overlay"></div>
      <div className="container">
        <h1 className="header-title text-center mt-2">{title}</h1>
      </div>
    </header>
  );
};

export default Header;
