import axios from "axios";

export const api = axios.create({
  baseURL: "http://localhost:8080/auth",
});

export async function registerUser(registration) {
  try {
    const response = await api.post("/registerUser", registration);
    return response.data;
  } catch (error) {
    console.log(error);
    if (error.response && error.response.data) {
      throw new Error(error.response.data);
    } else {
      throw new Error(`User registration unsuccessful : ${error.message}`);
    }
  }
}

export async function loginUser(login) {
  try {
    const response = await api.post("/login", login);
    if (response.status >= 200 && response.status < 300) {
      return response.data;
    } else {
      return null;
    }
  } catch (error) {
    return null;
  }
}
