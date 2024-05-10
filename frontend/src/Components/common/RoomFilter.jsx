import React, { useState } from "react";

const RoomFilter = ({ data, setFilteredData }) => {
  const [toFilter, setToFilter] = useState("");

  const handleSelectChange = (e) => {
    const selectedType = e.target.value;
    setToFilter(selectedType);
    const filteredRooms = data.filter((room) =>
      room.roomType.toLowerCase().includes(selectedType.toLowerCase())
    );
    setFilteredData(filteredRooms);
  };

  const clearFilter = () => {
    setToFilter("");
    setFilteredData(data);
  };

  const roomTypes = ["", ...new Set(data.map((room) => room.roomType))];

  return (
    <div className="input-group mb-3">
      <span className="input-group-text" id="room-type-filter">
        Filter rooms by types
      </span>
      <select
        className="form-select"
        onChange={handleSelectChange}
        value={toFilter}
      >
        <option value="">select a room type to filter...</option>
        {roomTypes.map((type, index) => (
          <option key={index} value={String(type)}>
            {String(type)}
          </option>
        ))}
      </select>
      <button className="btn btn-hotel" type="button" onClick={clearFilter}>
        Clear Filter
      </button>
    </div>
  );
};

export default RoomFilter;
