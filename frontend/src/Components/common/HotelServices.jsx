import React from "react";
import { Container, Row, Col, Card } from "react-bootstrap";
import Header from "./Header";
import {
  FaClock,
  FaCocktail,
  FaParking,
  FaSnowflake,
  FaTshirt,
  FaUtensils,
  FaWifi,
} from "react-icons/fa";

const HotelServices = () => {
  return (
    <>
      <Container className="mb-2">
        <Header title={"Our Services"} />

        <Row>
          <h4 className="text-center mt-3">
            Services at <span className="hotel-color"> LuxeStay </span>
            <span className="gap-2">
              <FaClock /> - 24 Hour Front Desk
            </span>
          </h4>
        </Row>

        <hr />

        <Row xs={1} md={2} lg={3} className="g-4 mt-2">
          <Col>
            <Card>
              <Card.Body>
                <div className="d-flex align-items-center">
                  <FaWifi className="hotel-color me-2" />
                  <h5 className="hotel-color mb-2">WiFi</h5>
                </div>
                <Card.Text>
                  Stay connected with high-speed internet access.
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>

          <Col>
            <Card>
              <Card.Body>
                <div className="d-flex align-items-center">
                  <FaUtensils className="hotel-color me-2" />
                  <h5 className="hotel-color mb-2">Breakfast</h5>
                </div>
                <Card.Text>
                  Start your day with a delicious breakfast buffet
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>

          <Col>
            <Card>
              <Card.Body>
                <div className="d-flex align-items-center">
                  <FaTshirt className="hotel-color me-2" />
                  <h5 className="hotel-color mb-2">Laundry</h5>
                </div>
                <Card.Text>
                  Keep your clothes clean and fresh with our laundry service.
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>

          <Col>
            <Card>
              <Card.Body>
                <div className="d-flex align-items-center">
                  <FaCocktail className="hotel-color me-2" />
                  <h5 className="hotel-color mb-2">Mini-bar</h5>
                </div>
                <Card.Text>
                  Enjoy a refreshing drink or snack from our in-room mini-bar.
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>

          <Col>
            <Card>
              <Card.Body>
                <div className="d-flex align-items-center">
                  <FaParking className="hotel-color me-2" />
                  <h5 className="hotel-color mb-2">Parking</h5>
                </div>
                <Card.Text>
                  Park your car conveniently in our on-site parking lot.
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>

          <Col>
            <Card>
              <Card.Body>
                <div className="d-flex align-items-center">
                  <FaSnowflake className="hotel-color me-2" />
                  <h5 className="hotel-color mb-2">Air conditioning</h5>
                </div>
                <Card.Text>
                  Stay cool and comfortable with our air conditioning system.
                </Card.Text>
              </Card.Body>
            </Card>
          </Col>
        </Row>
      </Container>
    </>
  );
};

export default HotelServices;
