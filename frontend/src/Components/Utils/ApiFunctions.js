import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:8080/api/v1/rooms/",
});

export async function addRoom(photo, roomType, roomPrice) {
  const formData = new FormData();
  formData.append("photo", photo);
  formData.append("roomType", roomType);
  formData.append("roomPrice", roomPrice);

  const response = await api.post("/add-new-room", formData);

  if (response.status === 201) {
    return true;
  } else {
    return false;
  }
}

export async function getRoomTypes() {
  try {
    const response = await api.get("/getRoomTypes");
    return response.data;
  } catch (error) {
    throw new Error("Error fetching room types");
  }
}

export async function getAllRooms() {
  try {
    const response = await api.get("/getAllRooms");
    return response.data;
  } catch (error) {
    throw new Error("Error fetching rooms");
  }
}

export async function deleteRoom(roomId) {
  try {
    const result = await api.delete(`/deleteRoom/${roomId}`);
    return result;
  } catch (error) {
    throw new Error(`Error in deleting room :  ${error.message}`);
  }
}
