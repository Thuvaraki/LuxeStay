import React from "react";
import { Card, Col } from "react-bootstrap";
import { Link } from "react-router-dom";

const RoomCard = ({ room }) => {
  return (
    <Col key={room.id} className="mb-4" xs={12}>
      <Card>
        <Card.Body className="d-flex flex-wrap align-items-center">
          <div className="flex-shrrink-0 mr-3 mb-3 mb-md-0">
            <Link to={`/book-room/${room.id}`} className="btn btn-hotel btn-sm">
              <Card.Img
                variant="top"
                src={`data:image/png;base64, ${room.photo}`}
                alt="Room Photo"
                style={{ width: "100%", maxWidth: "200px", height: "auto" }}
              />
            </Link>
          </div>
          <div className="flex-grow-1 ml-3 px-5">
            <Card.Title>
              <div className="hotel-color">{room.roomType}</div>
            </Card.Title>
            <Card.Title>
              <div className="room-price">{room.roomPrice} / night</div>
            </Card.Title>
            <Card.Text>
              Some room information goes here for the guest to read through
            </Card.Text>
          </div>
          <div className="flex-shrink-0 mt-3">
            <Link to={`/book-room/${room.id}`} className="btn-hotel btn-sm btn">
              Book Now
            </Link>
          </div>
        </Card.Body>
      </Card>
    </Col>
  );
};

export default RoomCard;
