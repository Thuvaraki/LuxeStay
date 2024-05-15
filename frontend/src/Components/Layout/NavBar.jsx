import React, { useContext, useState } from "react";
import { NavLink, Link } from "react-router-dom";
import Logout from "../Auth/Logout";
import { AuthContext } from "../Auth/AuthProvider";

const NavBar = () => {
  const [showAccount, setShowAccount] = useState(false);

  const { user } = useContext(AuthContext);

  const handleAccountClick = () => {
    setShowAccount(!showAccount);
  };

  // isLoggesIn is a boolean variable that checks whether the user object obtained from the AuthContext is not null
  const isLoggesIn = user !== null;
  const userRole = localStorage.getItem("userRole");

  return (
    <nav className="navbar navbar-expand-lg bg-body-tertiary px-5 shadow mt-5 sticky-top">
      <div className="container-fluid">
        <Link to={"/"} className="navbar-brand">
          <span className="hotel-color">LuxeStay</span>
        </Link>

        <button
          className="navbar-toggler"
          type="button"
          data-bs-toggle="collapse"
          data-bs-target="#navbarScroll"
          aria-controls="navbarScroll"
          aria-expanded="false"
          aria-label="Toggle navigation"
        >
          <span className="navbar-toggler-icon"></span>
        </button>

        <div className="collapse navbar-collapse" id="navbarScroll">
          <ul className="navbar-nav me-auto my-2 my-lg-0 navbar-nav-scroll">
            <li className="nav-item">
              <NavLink
                className="nav-link"
                aria-current="page"
                to={"/browse-all-rooms"}
              >
                Browse all rooms
              </NavLink>
            </li>
            {isLoggesIn && userRole === "ROLE_ADMIN" && (
              <li className="nav-item">
                <NavLink className="nav-link" to={"/admin"}>
                  Admin
                </NavLink>
              </li>
            )}
          </ul>

          <ul className="d-flex navbar-nav">
            <li className="nav-item">
              <NavLink className="nav-link" to={"/find-booking"}>
                Find my booking
              </NavLink>
            </li>

            <li className="nav-item dropdown">
              <a
                className={`nav-link dropdown-toggle ${
                  showAccount ? "show" : ""
                }`}
                href="#"
                role="button"
                data-bs-toggle="dropdown"
                aria-expanded="false"
                onClick={handleAccountClick}
              >
                {" "}
                Account
              </a>

              <ul
                className={`dropdown-menu ${showAccount ? "show" : ""}`}
                aria-labelledby="navbarDropdown"
              >
                {!isLoggesIn ? (
                  <li>
                    <Link className="dropdown-item" to={"/login"}>
                      Login
                    </Link>
                  </li>
                ) : (
                  <li>
                    <Logout />
                  </li>
                )}

                {/* <li>
                  <Link className="dropdown-item" to={"/profile"}>
                    Profile
                  </Link>
                </li> */}
                {/* <li>
                  <hr className="dropdown-item" />
                </li> */}
              </ul>
            </li>
          </ul>
        </div>
      </div>
    </nav>
  );
};

export default NavBar;
