import React, { useState } from "react";
import { Button, Row } from "react-bootstrap";
import RoomCard from "../Room/RoomCard";
import RoomPaginator from "../common/RoomPaginator";

const RoomSearchResults = ({ results, onClearSearch }) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [resultsPerPage] = useState(3);
  const totalResults = results.length;
  const totalPages = Math.ceil(totalResults / resultsPerPage);

  const handlePageChange = (pageNumber) => {
    setCurrentPage(pageNumber);
  };

  const startIndex = (currentPage - 1) * resultsPerPage;
  const endIndex = startIndex + resultsPerPage;
  const paginatedResults = results.slice(startIndex, endIndex);

  return (
    <>
      {results.length > 0 ? (
        <>
          <h3 className="text-center mt-3">Search Results</h3>
          <Row>
            {paginatedResults.map((room) => (
              <RoomCard key={room.id} room={room} />
            ))}
          </Row>
          <Row>
            {totalResults > resultsPerPage && (
              <RoomPaginator
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={handlePageChange}
              />
            )}
            <Button variant="secondary" onClick={onClearSearch}>
              Clear Search
            </Button>
          </Row>
        </>
      ) : (
        <p></p>
      )}
    </>
  );
};

export default RoomSearchResults;
