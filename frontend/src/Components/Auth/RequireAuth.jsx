import React from "react";
import { Navigate, useLocation } from "react-router-dom";

const RequireAuth = ({ children }) => {
  const user = localStorage.getItem("userId");
  const location = useLocation(); //useLocation hook to get information about the current URL path, including the pathname and any search parameters or state associated with the route.
  if (!user) {
    return <Navigate to="/login" state={{ path: location.pathname }} />; //passes the current route (location.pathname) as part of the state object to the /login page.
  }
  return children;
};
export default RequireAuth;
