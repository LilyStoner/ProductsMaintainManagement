﻿USE MaintainManagement

INSERT INTO [Role] (RoleName)
VALUES 
('Admin'),
('Technician'),
('Inventory Manager'),
('Repair Contractor'),
('Customer Service Agent'),
('Inactive');

INSERT INTO Staff (UsernameS, PasswordS, RoleID, [Name], Gender, DateOfBirth, Email, Phone, [Address], Image)
VALUES
('admin01', 'T2D6i9+ldWGqevu6W6FAP1iXBbI=', 1, 'Admin User 1', 'Male', '1985-03-15', 'admin1@example.com', '0123456789', '123 Admin Street', NULL),
('tech01', 'Cw2LaFmhUP2i/jGdPuB5aVCxAQg=', 2, 'Technician 1', 'Male', '1990-06-20', 'tech1@example.com', '0123456790', '456 Tech Street', NULL),
('inv_manager1', 'G1VKaeAjqcN762tlQAIFfla2Hnc=', 3, 'Inventory Manager 1', 'Female', '1988-11-10', 'inv1@example.com', '0123456791', '789 Inventory Ave', NULL),
('cust_service1', 'mnIoccUReEqm3CnIyVEsEqXI418=', 5, 'Customer Service 1', 'Female', '1992-08-25', 'cs1@example.com', '0123456792', '321 Service Blvd', NULL),
('repair_contractor1', 'cDW7EzjR1mP8REo1UF3mWlnuZdo=', 4, 'Repair Contractor 1', 'Male', '1987-05-18', 'repair1@example.com', '0123456793', '654 Repair St', NULL),
('admin02', 'O5eiLfa5pJNrHk7ck1tNBw6pUYE=', 1, 'Admin User 2', 'Female', '1989-12-02', 'admin2@example.com', '0123456794', '234 Admin Street', NULL),
('tech02', 't6oNiVU8eL8t1C+e11mByn7n7oA=', 2, 'Technician 2', 'Male', '1991-07-14', 'tech2@example.com', '0123456795', '567 Tech St', NULL),
('inv_manager2', '2L5q1/vlAepSi4mrWEoz+oJnTEY=', 3, 'Inventory Manager 2', 'Female', '1993-04-22', 'inv2@example.com', '0123456796', '890 Inventory Ave', NULL),
('cust_service2', 'hNpyQH84BIbgDRM+z0nJgUqCeV8=', 5, 'Customer Service 2', 'Other', '1995-10-30', 'cs2@example.com', '0123456797', '432 Service Blvd', NULL),
('repair_contractor2', 'NiH6AmjlqDCWROFjLhNDiYQ1F5U=', 4, 'Repair Contractor 2', 'Male', '1986-09-05', 'repair2@example.com', '0123456798', '765 Repair St', NULL),
('admin03', 'm1HKaKLljc/8wGtZ4IIGTTCCPdE=', 1, 'Admin User 3', 'Male', '1984-02-17', 'admin3@example.com', '0123456799', '345 Admin Ave', NULL),
('tech03', 'z7f1n0HLfbf71/EAPMwcnzUhKLg=', 2, 'Technician 3', 'Male', '1990-08-09', 'tech3@example.com', '0123456800', '678 Tech St', NULL),
('inv_manager3', 'hMOqi32PeNalXipffGflzq/L9EY=', 3, 'Inventory Manager 3', 'Female', '1987-03-25', 'inv3@example.com', '0123456801', '901 Inventory St', NULL),
('cust_service3', '6glxG6yT7gkwR4oRHlU7X7akgY4=', 5, 'Customer Service 3', 'Female', '1994-05-19', 'cs3@example.com', '0123456802', '543 Service Rd', NULL),
('repair_contractor3', '01g8MZ8t7IJL2iIGtzRMPTzJiTo=', 4, 'Repair Contractor 3', 'Male', '1985-12-29', 'repair3@example.com', '0123456803', '876 Repair St', NULL),
('admin04', 'VSoUE0cbBMY/79BqIk1v+kCdG1M=', 1, 'Admin User 4', 'Female', '1986-06-06', 'admin4@example.com', '0123456804', '456 Admin Blvd', NULL),
('tech04', 'DTxKhA7pCN1NwkGj1iPWadYUOz0=', 2, 'Technician 4', 'Male', '1992-01-11', 'tech4@example.com', '0123456805', '789 Tech Rd', NULL),
('inv_manager4', 'R+RhrqNOhedR/3foIWXfAA8DHnc=', 3, 'Inventory Manager 4', 'Other', '1988-04-04', 'inv4@example.com', '0123456806', '234 Inventory Blvd', NULL),
('cust_service4', 'nMGd0IHxlcneVgs1GoQ6SjRSB2A=', 5, 'Customer Service 4', 'Female', '1993-07-07', 'cs4@example.com', '0123456807', '678 Service Ave', NULL),
('repair_contractor4', '8qZHe/AetEyne9jfDSciHoot6Y4=', 4, 'Repair Contractor 4', 'Male', '1989-09-21', 'repair4@example.com', '0123456808', '123 Repair Rd', NULL);



-- Chèn thêm dữ liệu vào Customer với DateOfBirth
INSERT INTO Customer (UsernameC, PasswordC, [Name], Gender, DateOfBirth, Email, Phone, [Address], Image)
VALUES
('cust01', 'T2D6i9+ldWGqevu6W6FAP1iXBbI=', 'Customer 1', 'Male', '1990-01-15', 'customer1@example.com', '0123456001', '123 Customer St',' img/avatar/avatar'),
('cust02', 'Cw2LaFmhUP2i/jGdPuB5aVCxAQg=', 'Customer 2', 'Female', '1992-03-22', 'customer2@example.com', '0123456002', '234 Customer Ave', ' img/avatar/avatar'),
('cust03', 'G1VKaeAjqcN762tlQAIFfla2Hnc=', 'Customer 3', 'Male', '1988-07-10', 'customer3@example.com', '0123456003', '345 Customer Blvd', ' img/avatar/avatar'),
('cust04', 'mnIoccUReEqm3CnIyVEsEqXI418=', 'Customer 4', 'Female', '1995-05-30', 'customer4@example.com', '0123456004', '456 Customer Rd', ' img/avatar/avatar'),
('cust05', 'cDW7EzjR1mP8REo1UF3mWlnuZdo=', 'Customer 5', 'Male', '1987-09-18', 'customer5@example.com', '0123456005', '567 Customer St', ' img/avatar/avatar'),
('cust06', 'O5eiLfa5pJNrHk7ck1tNBw6pUYE=', 'Customer 6', 'Female', '1993-12-25', 'customer6@example.com', '0123456006', '678 Customer Blvd', ' img/avatar/avatar'),
('cust07', 't6oNiVU8eL8t1C+e11mByn7n7oA=', 'Customer 7', 'Male', '1989-06-14', 'customer7@example.com', '0123456007', '789 Customer Ave', ' img/avatar/avatar'),
('cust08', '2L5q1/vlAepSi4mrWEoz+oJnTEY=', 'Customer 8', 'Female', '1997-04-05', 'customer8@example.com', '0123456008', '890 Customer Rd', ' img/avatar/avatar'),
('cust09', 'hNpyQH84BIbgDRM+z0nJgUqCeV8=', 'Customer 9', 'Male', '1986-08-27', 'customer9@example.com', '0123456009', '123 Customer Blvd', ' img/avatar/avatar'),
('cust10', 'NiH6AmjlqDCWROFjLhNDiYQ1F5U=', 'Customer 10', 'Female', '1991-02-16', 'customer10@example.com', '0123456010', '234 Customer St', ' img/avatar/avatar'),
('cust11', 'm1HKaKLljc/8wGtZ4IIGTTCCPdE=', 'Customer 11', 'Male', '1984-11-21', 'customer11@example.com', '0123456011', '345 Customer Ave', ' img/avatar/avatar'),
('cust12', 'z7f1n0HLfbf71/EAPMwcnzUhKLg=', 'Customer 12', 'Female', '1996-07-08', 'customer12@example.com', '0123456012', '456 Customer Rd',' img/avatar/avatar'),
('cust13', 'hMOqi32PeNalXipffGflzq/L9EY=', 'Customer 13', 'Male', '1985-10-19', 'customer13@example.com', '0123456013', '567 Customer Blvd', ' img/avatar/avatar'),
('cust14', '6glxG6yT7gkwR4oRHlU7X7akgY4=', 'Customer 14', 'Female', '1994-09-12', 'customer14@example.com', '0123456014', '678 Customer Ave', ' img/avatar/avatar'),
('cust15', '01g8MZ8t7IJL2iIGtzRMPTzJiTo=', 'Customer 15', 'Male', '1983-03-05', 'customer15@example.com', '0123456015', '789 Customer Rd', ' img/avatar/avatar'),
('cust16', 'VSoUE0cbBMY/79BqIk1v+kCdG1M=', 'Customer 16', 'Female', '1998-06-30', 'customer16@example.com', '0123456016', '890 Customer St',' img/avatar/avatar'),
('cust17', 'DTxKhA7pCN1NwkGj1iPWadYUOz0=', 'Customer 17', 'Male', '1982-12-11', 'customer17@example.com', '0123456017', '123 Customer Ave', ' img/avatar/avatar'),
('cust18', 'R+RhrqNOhedR/3foIWXfAA8DHnc=', 'Customer 18', 'Female', '1990-05-09', 'customer18@example.com', '0123456018', '234 Customer Blvd', ' img/avatar/avatar'),
('cust19', 'nMGd0IHxlcneVgs1GoQ6SjRSB2A=', 'Customer 19', 'Male', '1981-07-22', 'customer19@example.com', '0123456019', '345 Customer Rd', ' img/avatar/avatar'),
('cust20', '8qZHe/AetEyne9jfDSciHoot6Y4=', 'Customer 20', 'Female', '1999-08-14', 'customer20@example.com', '0123456020', '456 Customer St', ' img/avatar/avatar');

