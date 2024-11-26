import React, { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import moment from "moment";
// The moment function is used for parsing, validating, manipulating, and formatting dates and times in JavaScript
import { saveBooking } from "../Utils/BookingApiFunctions";
import { Form, FormControl } from "react-bootstrap";
import { getRoomById } from "../Utils/ApiFunctions";
import BookingSummary from "./BookingSummary";
import { useAuth } from "../Auth/AuthProvider";

const BookingForm = () => {
  const [isValidated, setIsValidated] = useState(false);
  const [roomPrice, setRoomPrice] = useState(0);
  const [isSubmitted, setIsSubmitted] = useState(false);
  const [errorMessage, setErrorMessage] = useState("");

  const { roomId } = useParams();
  const navigate = useNavigate();
  const currentUser = localStorage.getItem("userId");

  const [booking, setBooking] = useState({
    guestFullName: "",
    guestEmail: currentUser,
    checkInDate: "",
    checkOutDate: "",
    numOfAdults: "",
    numOfChildren: "",
  });

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setBooking({ ...booking, [name]: value });
    setErrorMessage("");
  };

  const getRoomPriceById = async (roomId) => {
    try {
      const response = await getRoomById(roomId);
      // console.log("roomPrice", response.roomPrice);
      setRoomPrice(response.roomPrice);
    } catch (error) {
      throw new Error(error.message);
    }
  };

  useEffect(() => {
    getRoomPriceById(roomId);
  }, [roomId]);

  const calculatePayment = () => {
    const checkInDate = moment(booking.checkInDate);
    const checkOutDate = moment(booking.checkOutDate);
    const diffInDays = checkOutDate.diff(checkInDate, "days");
    const pricePerDay = roomPrice ? roomPrice : 0;
    const totalPayment = diffInDays * pricePerDay;
    return totalPayment;
  };

  const isGuestCountValid = () => {
    const adultCount = parseInt(booking.numOfAdults);
    const childCount = parseInt(booking.numOfChildren);
    const totalCount = adultCount + childCount;
    return totalCount >= 1 && adultCount >= 1;
  };
  // converting the dates to moment object, so that we r able to apply functions like diff, isSameOrAfter easily

  const isCheckOutDateValid = () => {
    // console.log("Check-In Date:", booking.checkInDate);
    // console.log("Check-Out Date:", booking.checkOutDate);
    if (
      !moment(booking.checkOutDate).isSameOrAfter(moment(booking.checkInDate))
    ) {
      setErrorMessage("Check-out date must come before check-in date");
      return false;
    } else {
      setErrorMessage("");
      return true;
    }
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    const form = e.currentTarget;
    if (
      form.checkValidity() === false ||
      !isGuestCountValid() ||
      !isCheckOutDateValid()
    )
      e.stopPropagation(); // Stop event propagation if form is invalid or validations fail
    else {
      setIsSubmitted(true);
    }
    setIsValidated(true);
  };

  const handleFormSubmit = async () => {
    try {
      const confirmationCode = await saveBooking(roomId, booking);
      setIsSubmitted(true);
      console.log("confirmationCode", confirmationCode);
      navigate("/booking-status", { state: { message: confirmationCode } }); //state will be accessible in the destination component (BookingSuccess) through useLocation().
    } catch (error) {
      setErrorMessage(error.message);
      console.log("error", error.message);
      navigate("/booking-status", { state: { error: error.message } });
    }
  };

  return (
    <>
      <div className="container mb-5">
        <div className="row">
          <div className="col-md-6">
            <div className="card card-body mt-5">
              <h2 className="text-center hotel-color">Reserve Room</h2>
              <Form noValidate validated={isValidated} onSubmit={handleSubmit}>
                {/* /noValidate - Disabling HTML5 form validation */}
                <Form.Group>
                  <Form.Label htmlFor="guestName" className="hotel-color mt-3">
                    Full Name :
                  </Form.Label>
                  <FormControl
                    required
                    type="text"
                    name="guestFullName"
                    id="guestName"
                    placeholder="Enter your full name"
                    value={booking.guestFullName}
                    onChange={handleInputChange}
                  />
                  <Form.Control.Feedback type="invalid">
                    Please enter your fullname.
                  </Form.Control.Feedback>
                </Form.Group>
                <Form.Group>
                  <Form.Label htmlFor="email" className="hotel-color mt-3">
                    Email :
                  </Form.Label>
                  <FormControl
                    required
                    type="email"
                    name="guestEmail"
                    id="email"
                    placeholder="Enter your Email"
                    value={booking.guestEmail}
                    onChange={handleInputChange}
                  />
                  <Form.Control.Feedback type="invalid">
                    Please enter your Email
                  </Form.Control.Feedback>
                </Form.Group>
                <fieldset style={{ border: "2px" }}>
                  <div className="row">
                    <div className="col- 6 mt-3">
                      <Form.Label
                        htmlFor="check-in-date"
                        className="hotel-color"
                      >
                        Check-In Date :
                      </Form.Label>
                      <FormControl
                        required
                        type="date"
                        name="checkInDate"
                        id="check-in-date"
                        placeholder="check-in date"
                        value={booking.checkInDate}
                        onChange={(e) => handleInputChange(e)}
                      />
                      <Form.Control.Feedback type="invalid">
                        Please select a check-in date
                      </Form.Control.Feedback>
                    </div>

                    <div className="col- 6 mt-3">
                      <Form.Label
                        htmlFor="check-out-date"
                        className="hotel-color"
                      >
                        Check-Out Date :
                      </Form.Label>
                      <FormControl
                        required
                        type="date"
                        name="checkOutDate"
                        id="check-out-date"
                        placeholder="check-out date"
                        value={booking.checkOutDate}
                        onChange={(e) => handleInputChange(e)}
                      />
                      <Form.Control.Feedback type="invalid">
                        Please select a check-out date
                      </Form.Control.Feedback>
                    </div>
                  </div>
                </fieldset>
                <fieldset style={{ border: "2px" }}>
                  <div className="row">
                    <div className="col- 6">
                      <Form.Label
                        htmlFor="numOfAdults"
                        className="hotel-color mt-3"
                      >
                        Adults :
                      </Form.Label>
                      <FormControl
                        required
                        type="number"
                        name="numOfAdults"
                        id="numOfAdults"
                        placeholder="0"
                        value={booking.numOfAdults}
                        onChange={handleInputChange}
                        min={1}
                      />
                      <Form.Control.Feedback type="invalid">
                        Please select at least 1 adult.
                      </Form.Control.Feedback>
                    </div>

                    <div className="col- 6 mt-3">
                      <Form.Label
                        htmlFor="numOfChildren"
                        className="hotel-color"
                      >
                        Children :
                      </Form.Label>
                      <FormControl
                        required
                        type="number"
                        name="numOfChildren"
                        id="numOfChildren"
                        placeholder="0"
                        value={booking.numOfChildren}
                        onChange={handleInputChange}
                        min={0}
                      />
                      <Form.Control.Feedback type="invalid">
                        Select 0 if no children
                      </Form.Control.Feedback>
                    </div>
                    {errorMessage && (
                      <p className="error-message text-danger">
                        {errorMessage}
                      </p>
                    )}
                  </div>
                </fieldset>
                <div className="form-group mt-3 mb-2">
                  <button type="submit" className="btn-hotel">
                    Continue
                  </button>
                </div>
              </Form>
            </div>
          </div>

          <div className="col-md-6">
            {isSubmitted && (
              <BookingSummary
                booking={booking}
                payment={calculatePayment()}
                onConfirm={handleFormSubmit}
                isFormValid={isValidated}
              />
            )}
          </div>
        </div>
      </div>
    </>
  );
};

export default BookingForm;
