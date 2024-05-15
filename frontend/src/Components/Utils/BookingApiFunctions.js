import axios from "axios";
import { getHeader } from "./UserApiFunctions";

export const api = axios.create({
  baseURL: "http://localhost:8080/api/v1/bookings/",
});

export async function saveBooking(roomId, booking) {
  try {
    const response = await api.post(`saveBooking/${roomId}`, booking);
    return response.data;
  } catch (error) {
    throw new Error(`Error booking room : ${error.message}`);
  }
}

export async function getAllBooking() {
  try {
    const response = await api.get(`getAllBookings`);
    return response.data;
  } catch (error) {
    throw new Error(`Error in fetching booking : ${error.message}`);
  }
}

export async function getBookingByConfirmationCode(confirmationCode) {
  try {
    const response = await api.get(`confirmation/${confirmationCode}`);
    return response.data;
  } catch (error) {
    throw new Error(`Error in fetching booking : ${error.message}`);
  }
}

export async function cancelBooking(bookingId) {
  try {
    const response = await api.delete(`cancelBooking/${bookingId}`);
    return response.data;
  } catch (error) {
    throw new Error(`Error in cancel booking :  ${error.message}`);
  }
}

export async function getBookingsByUserId(userId, token) {
  try {
    const response = await api.get(`/bookingsByUserId/${userId}`, {
      headers: getHeader(),
    });
    return response.data;
  } catch (error) {
    console.error("Error fetching bookings:", error.message);
    throw new Error("Failed to fetch bookings");
  }
}
