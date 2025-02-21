USE MaintainManagement

INSERT INTO ProductDetail (CustomerID, ProductID, ProductCode, PurchaseDate)
VALUES
(1, 1, 'P103453', '2025-01-01'),
(2, 2, 'P1345345', '2025-01-02'),
(3, 3, 'P1434343', '2025-01-03'),
(4, 1, 'P13435', '2025-01-04'),
(5, 4, 'P134535', '2025-01-05')

INSERT INTO WarrantyProduct (ProductDetailID, UnknowProductID)
VALUES 
(1, NULL), (2, NULL), (3, NULL), (4, NULL), (5, NULL),
(6, NULL), (7, NULL), (8, NULL), (9, NULL), (10, NULL),
(11, NULL), (12, NULL), (13, NULL), (14, NULL), (15, NULL),
(16, NULL), (17, NULL), (18, NULL), (19, NULL), (23, NULL),
(33, NULL), (43, NULL), (53, NULL), (63, NULL),(NULL,25),(NULL,26),(NULL,27),(NULL,28),(NULL,29),
(NULL, 1), (NULL, 2), (NULL, 3), (NULL, 4), (NULL, 5),  
(NULL, 6), (NULL, 7), (NULL, 8), (NULL, 9), (NULL, 10),  
(NULL, 11), (NULL, 12), (NULL, 13), (NULL, 14), (NULL, 15),  
(NULL, 16), (NULL, 17), (NULL, 18), (NULL, 19),
(NULL, 20), (NULL, 21), (NULL, 22), (NULL, 23),(NULL,24)

INSERT INTO WarrantyCard (WarrantyCardCode, WarrantyProductID, IssueDescription, WarrantyStatus, CreatedDate)
VALUES
('WC101', 1, 'Screen malfunction', 'fixing', '2024-01-10'),
('WC102', 2, 'Battery issue', 'completed', '2024-01-11'),
('WC103', 3, 'Overheating problem', 'fixing', '2024-01-12'),
('WC104', 4, 'Keyboard not working', 'cancel', '2024-01-13'),
('WC105', 5, 'Touchpad unresponsive', 'completed', '2024-01-14'),
('WC106', 6, 'Audio not working', 'fixing', '2024-01-15'),
('WC107', 7, 'Charging port damaged', 'completed', '2024-01-16'),
('WC108', 8, 'Camera malfunction', 'fixing', '2024-01-17'),
('WC109', 9, 'Bluetooth not connecting', 'completed', '2024-01-18'),
('WC110', 10, 'Wi-Fi connectivity issue', 'cancel', '2024-01-19'),
('WC111', 11, 'Screen flickering', 'fixing', '2024-01-20'),
('WC112', 12, 'Battery draining quickly', 'completed', '2024-01-21'),
('WC113', 13, 'Speaker distortion', 'fixing', '2024-01-22'),
('WC114', 14, 'Power button not working', 'completed', '2024-01-23'),
('WC115', 15, 'Overheating during charging', 'fixing', '2024-01-24'),
('WC116', 16, 'Random shutdowns', 'cancel', '2024-01-25'),
('WC117', 17, 'Hard drive failure', 'completed', '2024-01-26'),
('WC118', 18, 'USB port not functioning', 'fixing', '2024-01-27'),
('WC119', 19, 'Software crashes frequently', 'completed', '2024-01-23'),
('WC120', 20, 'Power button not working', 'fixing', '2024-01-29'),
('WC121', 21, 'Random shutdowns', 'cancel', '2024-01-21'),
('WC122', 22, 'Camera malfunction', 'completed', '2024-01-18'),
('WC123', 23, 'Touchpad unresponsive', 'completed', '2024-01-16'),
('WC124', 24, 'Screen brightness not adjustable', 'completed', '2024-01-29');

INSERT INTO WarrantyCard (WarrantyCardCode, WarrantyProductID, IssueDescription, WarrantyStatus, CreatedDate)
VALUES
('WC125', 25, 'Fingerprint sensor not working', 'fixing', '2024-01-30'),
('WC126', 26, 'Mouse pad sensitivity issue', 'completed', '2024-01-31'),
('WC127', 27, 'Volume buttons not responding', 'cancel', '2024-02-01'),
('WC128', 28, 'Graphics card failure', 'fixing', '2024-02-02'),
('WC129', 29, 'Cooling fan noise', 'completed', '2024-02-03'),
('WC130', 30, 'Software update issue', 'cancel', '2024-02-04'),
('WC131', 31, 'Hinges are loose', 'fixing', '2024-02-05'),
('WC132', 32, 'System slow performance', 'completed', '2024-02-06'),
('WC133', 33, 'DVD drive not working', 'fixing', '2024-02-07'),
('WC134', 34, 'USB-C port damaged', 'cancel', '2024-02-08'),
('WC135', 35, 'Battery not charging', 'completed', '2024-02-09'),
('WC136', 36, 'Unresponsive power button', 'fixing', '2024-02-10'),
('WC137', 37, 'Frequent blue screen errors', 'completed', '2024-02-11'),
('WC138', 38, 'Wi-Fi keeps disconnecting', 'cancel', '2024-02-12'),
('WC139', 39, 'Screen has dead pixels', 'fixing', '2024-02-13'),
('WC140', 40, 'Laptop case cracked', 'completed', '2024-02-14'),
('WC141', 41, 'Overheating after updates', 'fixing', '2024-02-15'),
('WC142', 42, 'Keyboard backlight not working', 'completed', '2024-02-16'),
('WC143', 43, 'Sound card failure', 'cancel', '2024-02-17'),
('WC144', 44, 'Ethernet port not functioning', 'fixing', '2024-02-18'),
('WC145', 45, 'Camera blurry images', 'completed', '2024-02-19'),
('WC146', 46, 'Lid not closing properly', 'cancel', '2024-02-20'),
('WC147', 47, 'Power adapter overheating', 'fixing', '2024-02-21'),
('WC148', 48, 'Touchscreen unresponsive', 'completed', '2024-02-22'),
('WC149', 49, 'Fingerprint sensor delay', 'cancel', '2024-02-23'),
('WC150', 50, 'Laptop freezing randomly', 'fixing', '2024-02-24'),
('WC151', 51, 'Webcam not recognized', 'completed', '2024-02-25'),
('WC152', 52, 'Fan running at high speed', 'cancel', '2024-02-26'),
('WC153', 53, 'Frequent connectivity drops', 'fixing', '2024-02-27');

INSERT INTO Feedback (CustomerID, WarrantyCardID, Note, DateCreated, IsDeleted, ImageURL, VideoURL)
VALUES
(1, 2, 'The repair process was quick and efficient.', '2024-02-14', 0, 'https://via.placeholder.com/300', 'https://samplelib.com/lib/preview/mp4/sample-5s.mp4'),
(2, 3, 'The staff was very helpful and resolved my issue completely.', '2024-02-14', 0, 'https://via.placeholder.com/300', NULL),
(3, 4, 'I was satisfied with the repair, but it took a bit longer than expected.', '2024-04-14', 0, NULL, 'https://samplelib.com/lib/preview/mp4/sample-5s.mp4'),
(4, 5, 'The issue was resolved, but I had to visit the service center twice.', '2024-04-24', 0, 'https://via.placeholder.com/300', NULL),
(5, 1, 'The repair was excellent, and the product works like new.', '2024-03-04', 0, NULL, NULL),
(6, 2, 'The staff explained everything clearly and provided great service.', '2024-03-11', 0, 'https://via.placeholder.com/300', 'https://samplelib.com/lib/preview/mp4/sample-5s.mp4'),
(7, 3, 'The process was smooth, but I had to wait for parts to arrive.', '2024-03-02', 0, NULL, NULL),
(8, 4, 'I appreciate the service, but I feel the warranty coverage could be better.','2024-03-31', 0, 'https://via.placeholder.com/300', NULL),
(9, 5, 'I had a great experience with the service team.', '2024-03-25', 0, NULL, 'https://samplelib.com/lib/preview/mp4/sample-5s.mp4'),
(1, null, 'The issue was fixed promptly, and I was kept informed throughout.', '2024-03-01', 0, 'https://via.placeholder.com/300', NULL),
(1, 1, 'The service was excellent, and the staff was very professional.', '2024-03-15', 0, NULL, NULL),
(1, 2, 'I had to wait longer than expected, but the repair quality was good.', '2024-03-20', 0, NULL, NULL),
(1, 3, 'The staff went above and beyond to assist me.', '2024-03-22', 0, NULL, NULL),
(14, 4, 'I received clear communication throughout the process.', '2024-03-10', 0, NULL, NULL),
(5, 5, 'The product repair was satisfactory, but follow-up could improve.', '2024-03-08', 0, NULL, NULL),
(16, null, 'The repair team was friendly and answered all my questions.', '2024-03-12', 0, NULL, NULL),
(7, 1, 'The replacement part was not in stock, causing a delay.', '2024-03-18', 0, NULL, NULL),
(4, 2, 'I am impressed with how quickly the service was completed.', '2024-03-05', 0, NULL, NULL),
(19, null, 'The service center was clean and well-organized.', '2024-03-14', 0, NULL, NULL),
(2, 4, 'The technician was skilled and resolved the issue efficiently.', '2024-03-28', 0, NULL, NULL);

