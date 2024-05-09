import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:8080/api/v1/rooms/",
});

// In React, formData typically refers to an instance of the FormData object, which is part of the JavaScript FormData API.
//  This API is used to construct and handle HTML form data in a more powerful and flexible way, particularly when dealing with
// data that needs to be sent via HTTP requests, like submitting form data to a server

export async function addRoom(photo, roomType, roomPrice) {
  const formData = new FormData();
  formData.append("photo", photo);
  formData.append("roomType", roomType);
  formData.append("roomPrice", roomPrice);

  const response = await api.post("/add-new-room", formData);
  console.log(response);
  if (response.status === 201) {
    return true;
  } else {
    return false;
  }
}

export async function getRoomTypes() {
  try {
    const response = await api.get("/getRoomTypes");
    console.log("response" + response.data);
    return response.data;
  } catch (error) {
    console.error("Error fetching room types:", error);
    throw new Error("Error fetching room types");
  }
}
