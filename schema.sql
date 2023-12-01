DROP TABLE discounts;

CREATE TABLE discounts (
    id INT PRIMARY KEY AUTO_INCREMENT,
    `option` INT,
    booking_duration VARCHAR(200),
    discount DECIMAL(5, 3)
);

INSERT INTO discounts (`option`, booking_duration, discount)
VALUES
  (1, 'Monthly', 0.05),
  (2, 'Half Yearly', 0.1),
  (3, 'Yearly', 0.2);