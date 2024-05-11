import React, { useEffect, useState } from "react";
import { getAllRooms } from "../Utils/ApiFunctions";
import RoomCard from "./RoomCard";
import RoomFilter from "../common/RoomFilter";
import RoomPaginator from "../common/RoomPaginator";
import { Col, Container, Row } from "react-bootstrap";

const Room = () => {
  const [data, setData] = useState([]);
  const [filteredData, setFilteredData] = useState([]);
  const [isLoading, setIsLoading] = useState(false);
  const [error, setError] = useState("");
  const [currentPage, setCurrentPage] = useState(1);
  const roomsPerPage = 6;

  const fetchData = async () => {
    setIsLoading(true);
    try {
      const roomsData = await getAllRooms();
      console.log("Fetched Rooms Data:", roomsData);
      setData(roomsData);
      setFilteredData(roomsData);
    } catch (error) {
      console.error("Error fetching rooms:", error);
      setError(error.message);
    } finally {
      setIsLoading(false);
    }
  };

  useEffect(() => {
    fetchData();
  }, []);

  if (isLoading) {
    return <div>Loading rooms...</div>;
  }

  if (error) {
    return <div className="text-danger">Error: {error}</div>;
  }

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const totalPages = Math.ceil(filteredData.length / roomsPerPage);

  const renderRooms = () => {
    const startIndex = (currentPage - 1) * roomsPerPage;
    const endIndex = startIndex + roomsPerPage;

    return filteredData
      .slice(startIndex, endIndex)
      .map((room) => <RoomCard key={room.id} room={room} />);
  };

  return (
    <Container>
      <Row>
        <Col md={6} className="mb-3 mb-md-0">
          <RoomFilter data={data} setFilteredData={setFilteredData} />
        </Col>
        <Col md={6} className="d-flex align-items-center justify-content-end">
          <RoomPaginator
            currentPage={currentPage}
            totalPages={totalPages}
            onPageChange={handlePageChange}
          />
        </Col>
      </Row>

      <Row>{renderRooms()}</Row>
    </Container>
  );
};

export default Room;
