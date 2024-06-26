import React from "react";
import { useLocation } from "react-router-dom";
import Header from "../common/Header";

const BookingSuccess = () => {
  const location = useLocation();
  const message = location.state?.message; //Retrieves the message property from the location state passed during navigation.
  const error = location.state?.error;

  return (
    <div className="container">
      <Header title="Booking Status" />
      <div className="mt-5">
        {message ? (
          <div>
            <h3 className="text-success">Booking Success!</h3>
            <p className="text-success">{message}</p>
          </div>
        ) : (
          <div>
            <h3 className="text-danger">Booking Failed!</h3>
            <p className="text-danger">{error}</p>
          </div>
        )}
      </div>
    </div>
  );
};

export default BookingSuccess;
