USE MaintainManagement


INSERT INTO WarrantyProduct (ProductDetailID, UnknownProductID)
VALUES 
(1, NULL), (2, NULL), (3, NULL), (4, NULL), (5, NULL),
(6, NULL), (7, NULL), (8, NULL), (9, NULL), (10, NULL),
(11, NULL), (12, NULL), (13, NULL), (14, NULL), (15, NULL),
(16, NULL), (17, NULL), (18, NULL), (19, NULL), (23, NULL),
(33, NULL), (43, NULL), (53, NULL),(NULL,25),(NULL,26),(NULL,27),(NULL,28),(NULL,29),
(NULL, 1), (NULL, 2), (NULL, 3), (NULL, 4), (NULL, 5),  
(NULL, 6), (NULL, 7), (NULL, 8), (NULL, 9), (NULL, 10),  
(NULL, 11), (NULL, 12), (NULL, 13), (NULL, 14), (NULL, 15),  
(NULL, 16), (NULL, 17), (NULL, 18), (NULL, 19),
(NULL, 20), (NULL, 21), (NULL, 22), (NULL, 23),(NULL,24)

INSERT INTO WarrantyCard (WarrantyCardCode, WarrantyProductID, IssueDescription, WarrantyStatus, CreatedDate)
VALUES
('WC101', 1, 'Screen malfunction', 'waiting', '2024-01-10'),
('WC102', 2, 'Battery issue', 'waiting', '2024-01-11'),
('WC103', 3, 'Overheating problem', 'waiting', '2024-01-12'),
('WC104', 4, 'Keyboard not working', 'waiting', '2024-01-13'),
('WC105', 5, 'Touchpad unresponsive', 'waiting', '2024-01-14'),
('WC106', 6, 'Audio not working', 'waiting', '2024-01-15'),
('WC107', 7, 'Charging port damaged', 'waiting', '2024-01-16'),
('WC108', 8, 'Camera malfunction', 'waiting', '2024-01-17'),
('WC109', 9, 'Bluetooth not connecting', 'waiting', '2024-01-18'),
('WC110', 10, 'Wi-Fi connectivity issue', 'waiting', '2024-01-19'),
('WC111', 11, 'Screen flickering', 'waiting', '2024-01-20'),
('WC112', 12, 'Battery draining quickly', 'waiting', '2024-01-21'),
('WC113', 13, 'Speaker distortion', 'waiting', '2024-01-22'),
('WC114', 14, 'Power button not working', 'waiting', '2024-01-23'),
('WC115', 15, 'Overheating during charging', 'waiting', '2024-01-24'),
('WC116', 16, 'Random shutdowns', 'waiting', '2024-01-25'),
('WC117', 17, 'Hard drive failure', 'waiting', '2024-01-26'),
('WC118', 18, 'USB port not functioning', 'waiting', '2024-01-27'),
('WC119', 19, 'Software crashes frequently', 'waiting', '2024-01-23'),
('WC120', 20, 'Power button not working', 'waiting', '2024-01-29'),
('WC121', 21, 'Random shutdowns', 'waiting', '2024-01-21'),
('WC122', 22, 'Camera malfunction', 'waiting', '2024-01-18'),
('WC123', 23, 'Touchpad unresponsive', 'waiting', '2024-01-16'),
('WC124', 24, 'Screen brightness not adjustable', 'waiting', '2024-01-29'),
('WC125', 25, 'Fingerprint sensor not working', 'waiting', '2024-01-30'),
('WC126', 26, 'Mouse pad sensitivity issue', 'waiting', '2024-01-31'),
('WC127', 27, 'Volume buttons not responding', 'waiting', '2024-02-01'),
('WC128', 28, 'Graphics card failure', 'waiting', '2024-02-02'),
('WC129', 29, 'Cooling fan noise', 'waiting', '2024-02-03'),
('WC130', 30, 'Software update issue', 'waiting', '2024-02-04'),
('WC131', 31, 'Hinges are loose', 'waiting', '2024-02-05'),
('WC132', 32, 'System slow performance', 'waiting', '2024-02-06'),
('WC133', 33, 'DVD drive not working', 'waiting', '2024-02-07'),
('WC134', 34, 'USB-C port damaged', 'waiting', '2024-02-08'),
('WC135', 35, 'Battery not charging', 'waiting', '2024-02-09'),
('WC136', 36, 'Unresponsive power button', 'waiting', '2024-02-10'),
('WC137', 37, 'Frequent blue screen errors', 'waiting', '2024-02-11'),
('WC138', 38, 'Wi-Fi keeps disconnecting', 'waiting', '2024-02-12'),
('WC139', 39, 'Screen has dead pixels', 'waiting', '2024-02-13'),
('WC140', 40, 'Laptop case cracked', 'waiting', '2024-02-14'),
('WC141', 41, 'Overheating after updates', 'waiting', '2024-02-15'),
('WC142', 42, 'Keyboard backlight not working', 'waiting', '2024-02-16'),
('WC143', 43, 'Sound card failure', 'waiting', '2024-02-17'),
('WC144', 44, 'Ethernet port not functioning', 'waiting', '2024-02-18'),
('WC145', 45, 'Camera blurry images', 'waiting', '2024-02-19')

INSERT INTO WarrantyCard (WarrantyCardCode, WarrantyProductID, IssueDescription, WarrantyStatus, CreatedDate)
VALUES
('WC201', 1, 'Screen malfunction', 'fixing', '2014-01-10'),
('WC202', 2, 'Battery issue', 'done', '2014-01-11'),
('WC203', 3, 'Overheating problem', 'refix', '2014-01-12'),
('WC204', 4, 'Keyboard not working', 'completed', '2014-01-13'),
('WC205', 5, 'Touchpad unresponsive', 'cancel', '2014-01-14'),
('WC206', 6, 'Audio not working', 'fixing', '2014-01-15'),
('WC207', 7, 'Charging port damaged', 'done', '2014-01-16'),
('WC208', 8, 'Camera blurry', 'refix', '2014-01-17'),
('WC209', 9, 'WiFi not working', 'completed', '2014-01-18'),
('WC210', 10, 'Speaker issues', 'cancel', '2014-01-19'),
('WC211', 11, 'Bluetooth connection drops', 'fixing', '2014-01-20'),
('WC212', 12, 'Random shutdowns', 'done', '2014-01-21'),
('WC213', 13, 'Touchscreen unresponsive', 'refix', '2014-01-22'),
('WC214', 14, 'Microphone not detecting sound', 'completed', '2014-01-23'),
('WC215', 15, 'Power button stuck', 'cancel', '2014-01-24'),
('WC216', 16, 'USB not recognized', 'fixing', '2014-01-25'),
('WC217', 17, 'Headphone jack not working', 'done', '2014-01-26'),
('WC218', 18, 'Fingerprint sensor issue', 'refix', '2014-01-27'),
('WC219', 19, 'Auto restart problem', 'completed', '2014-01-28'),
('WC220', 20, 'Overheating while charging', 'cancel', '2014-01-29'),
('WC221', 21, 'System lagging frequently', 'fixing', '2014-01-30'),
('WC222', 22, 'Backlight flickering', 'done', '2014-01-31'),
('WC223', 23, 'Device fails to boot', 'refix', '2014-02-01'),
('WC224', 24, 'Unstable battery percentage', 'completed', '2014-02-02');


Insert into WarrantyCardProcess (WarrantyCardID, HandlerID, Action, ActionDate, Note)
values
(1,2,'create',GETDATE(),'create card'),
(2,2,'create',GETDATE(),'create card'),
(3,2,'create',GETDATE(),'create card'),
(4,2,'create',GETDATE(),'create card'),
(5,2,'create',GETDATE(),'create card'),
(6,2,'create',GETDATE(),'create card'),
(7,2,'create',GETDATE(),'create card'),
(8,2,'create',GETDATE(),'create card'),
(9,2,'create',GETDATE(),'create card'),
(10,2,'create',GETDATE(),'create card'),
(11,2,'create',GETDATE(),'create card'),
(12,2,'create',GETDATE(),'create card'),
(13,2,'create',GETDATE(),'create card'),
(14,2,'create',GETDATE(),'create card'),
(15,2,'create',GETDATE(),'create card'),
(16,2,'create',GETDATE(),'create card'),
(17,2,'create',GETDATE(),'create card'),
(18,2,'create',GETDATE(),'create card'),
(19,2,'create',GETDATE(),'create card'),
(20,2,'create',GETDATE(),'create card'),
(21,2,'create',GETDATE(),'create card'),
(22,2,'create',GETDATE(),'create card'),
(23,2,'create',GETDATE(),'create card'),
(24,2,'create',GETDATE(),'create card'),
(25,2,'create',GETDATE(),'create card'),
(26,2,'create',GETDATE(),'create card'),
(27,2,'create',GETDATE(),'create card'),
(28,2,'create',GETDATE(),'create card'),
(29,2,'create',GETDATE(),'create card'),
(30,2,'create',GETDATE(),'create card'),
(31,2,'create',GETDATE(),'create card'),
(32,2,'create',GETDATE(),'create card'),
(33,2,'create',GETDATE(),'create card'),
(34,2,'create',GETDATE(),'create card'),
(35,2,'create',GETDATE(),'create card'),
(36,2,'create',GETDATE(),'create card'),
(37,2,'create',GETDATE(),'create card'),
(38,2,'create',GETDATE(),'create card'),
(39,2,'create',GETDATE(),'create card'),
(40,2,'create',GETDATE(),'create card'),
(41,2,'create',GETDATE(),'create card'),
(42,2,'create',GETDATE(),'create card'),
(43,2,'create',GETDATE(),'create card'),
(44,2,'create',GETDATE(),'create card'),
(45,2,'create',GETDATE(),'create card'),
(46,2,'create',GETDATE(),'create card');


INSERT INTO Feedback (CustomerID, WarrantyCardID, Note, DateCreated, IsDeleted)
VALUES
(1, 2, 'The repair process was quick and efficient.', '2024-02-14', 0 ),
(2, 3, 'The staff was very helpful and resolved my issue completely.', '2024-02-14', 0 ),
(3, 4, 'I was satisfied with the repair, but it took a bit longer than expected.', '2024-04-14', 0 ),
(4, 5, 'The issue was resolved, but I had to visit the service center twice.', '2024-04-24', 0 ),
(5, 1, 'The repair was excellent, and the product works like new.', '2024-03-04', 0 ),
(6, 2, 'The staff explained everything clearly and provided great service.', '2024-03-11', 0 ),
(7, 3, 'The process was smooth, but I had to wait for parts to arrive.', '2024-03-02', 0 ),
(8, 4, 'I appreciate the service, but I feel the warranty coverage could be better.','2024-03-31', 0),
(9, 5, 'I had a great experience with the service team.', '2024-03-25', 0),
(1, null, 'The issue was fixed promptly, and I was kept informed throughout.', '2024-03-01', 0),
(1, 1, 'The service was excellent, and the staff was very professional.', '2024-03-15', 0 ),
(1, 2, 'I had to wait longer than expected, but the repair quality was good.', '2024-03-20', 0 ),
(1, 3, 'The staff went above and beyond to assist me.', '2024-03-22', 0 ),
(14, 4, 'I received clear communication throughout the process.', '2024-03-10', 0 ),
(5, 5, 'The product repair was satisfactory, but follow-up could improve.', '2024-03-08', 0 ),
(16, null, 'The repair team was friendly and answered all my questions.', '2024-03-12', 0 ),
(7, 1, 'The replacement part was not in stock, causing a delay.', '2024-03-18', 0 ),
(4, 2, 'I am impressed with how quickly the service was completed.', '2024-03-05', 0 ),
(19, null, 'The service center was clean and well-organized.', '2024-03-14', 0 ),
(2, 4, 'The technician was skilled and resolved the issue efficiently.', '2024-03-28', 0 );

