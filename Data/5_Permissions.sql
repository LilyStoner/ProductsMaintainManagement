﻿USE MaintainManagement

DELETE FROM Permissions;
DBCC CHECKIDENT ('Permissions', RESEED, 0);
DELETE FROM Role_Permissions


INSERT INTO [dbo].[Permissions] ([PermissionName], [Link]) VALUES
('VIEW_COMPONENTS_WAREHOUSE', '/ComponentWarehouse'),
('DELETE_COMPONENT', '/ComponentWarehouse/Delete'),
('VIEW_WARRANTY_CARD_LIST', '/Warehouse/WarrantyCard'),
('ADD_NEW_COMPONENT', '/ComponentWarehouse/Add'),
('SEARCH_ADVANCE_COMPONENT', '/ComponentWarehouse/Search'),
('VIEW_COMPONENT_DETAIL', '/ComponentWarehouse/Detail'),
('EDIT_COMPONENT', '/ComponentWarehouse/Edit'),
('ADD_COMPONENT_INTO_PRODUCT', '/ComponentWarehouse/AddProductToComponent'),
('DELETE_COMPONENT_IN_PRODUCT', '/ComponentWarehouse/Detail?action=remove'),
('EXPORT_COMPONENTS', '/ExportComponents'),
('IMPORT_COMPONENTS', '/ImportComponents'),
('VIEW_WARRANTY_CARD', '/WarrantyCard'),
('ADD_WARRANTY_CARD', '/WarrantyCard/Add'),
('DELETE_WARRANTY_CARD', '/WarrantyCard/Delete'),
('VIEW_WARRANTY_CARD_DETAIL', '/WarrantyCard/Detail'),
('PROCESSING_WARRANTY_CARD_INCOM', '/WarrantyCard/Detail?action=process'),
('UPLOAD_IMAGES_WARRANTY_CARD', '/WarrantyCard/Detail?action=uploadImages'),
('DELETE_IMAGES_WARRANTY_CARD', '/WarrantyCard/Detail?action=deleteMedia'),
('UPDATE_REPAIR_LIST_WARRANTY_CARD', '/WarrantyCard/Detail?action=update'),
('CREATE_CONTRACTOR_CARD', '/WarrantyCard/OutsourceRequest?action=requestOutsource'),
('ADD_COMPONENT_INTO_WARRANTY_CARD', '/WarrantyCard/AddComponent'),
('CREATE_INVOICE_FOR_CUSTOMER', '/Invoice/Create'),
('VIEW_LIST_INVOICE_OF_CARD', '/Invoice/List'),
('CONFIRM_PAY_BY_CASH', '/Invoice/PayCash'),
('MANAGE_BRAND', '/Brand'),
('MANAGE_COMPONENT_TYPE', '/ComponentType'),
('CREATE_CUSTOMER', '/customer?action=add'),
('IMPORT_EXCEL_CUSTOMER', ''),
('UPDATE_CUSTOMER', '/customer?action=update'),
('VIEW_LIST_CUSTOMER', '/customer'),
('VIEW_CUSTOMER_DETAIL', '/customer?action=detail'),
('VIEW_ROLES', '/roles'),
('VIEW_ROLES_PERMISSION', '/permissions'),
('UPDATE_PERMISSION', '/updatepermission'),
('VIEW_PRODUCT', '/viewProduct'),
('VIEW_STAFF', '/StaffController'),
('ADD_PRODUCT', '/viewProduct?action=add'),
('REPORT_STAFF_CONTROLLER', '/reportStaffController'),
('SEE_MORE_CONTROLLER', '/seeMoreController'),
('REPORT_COMPONENT_CONTROLLER', '/ReportComponentController'),
('REPORT_WARRANTY_CARD_JSP', '/reportWarrantyCard.jsp'),
('ADD_STAFF_JSP', '/add-staff.jsp'),
('CHANGE_PASSWORD_FORM_JSP', '/ChangePasswordForm.jsp'),
('CHANGE_PASSWORD', '/changepassword'),
('VIEW_PROFILE', '/profile'),
('UPDATE_PROFILE', '/profile'),
('VIEW_FEEDBACK', '/feedback?action=viewFeedback'),
('DELETE_FEEDBACK', '/feedback?action=deleteFeedback'),
('UPDATE_FEEDBACK', '/feedback?action=updateFeedback'),
('VIEW_FEEDBACK_LOG', '/feedbacklog?action=viewListFeedbackLog'),
('UNDO_FEEDBACK_LOG', '/feedbacklog?action=undoFeedback'),
('UPDATE_MAX_SIZE', '/updateMaxSize'),
('ADMIN_DASHBOARD_JSP', '/adminDashboard.jsp'),
('COMPONENT_REQUEST_RESPONSIBLE', '/componentRequestResponsible?action=viewComponentRequestResponsible'),
('CUSTOMIZE_HOMEPAGE', '/customizeHomepage'),
('UPDATE_COVER', '/updateCover'),
('FOOTER_CONTROLLER', '/FooterController'),
('CONTACT_CONTROLLER', '/ContactController'),
('MARKETING_SERVICE_SECTION_CONTROLLER', '/MarketingServiceSectionController'),
('EDIT_MARKETING_SERVICE_ITEM', '/MarketingServiceItemController?action=edit'),
('ADD_MARKETING_SERVICE_ITEM', '/MarketingServiceItemController?action=new'),
('DELETE_MARKETING_SERVICE_ITEM', '/MarketingServiceItemController?action=delete'),
('VIEW_EXTENDED_WARRANTY', '/extendedWarranty'),
('VIEW_EXTENDED_WARRANTY_DETAILS', '/extendedWarranty?action=view'),
('ADD_EXTENDED_WARRANTY', '/extendedWarranty?action=new'),
('EDIT_EXTENDED_WARRANTY', '/extendedWarranty?action=edit'),
('DELETE_EXTENDED_WARRANTY', '/extendedWarranty?action=delete'),
('GENERAL_DASHBOARD', '/dashBoard'),
('LIST_UNKNOWN', '/listUnknown'),
('ADD_UNKNOWN', '/addUnknown'),
('VIEW_COMPONENT_REQUEST', '/componentRequest?action=viewComponentRequestDashboard'),
('CREATE_SUPPLEMENT_REQUEST', '/supplementRequest?action=createSupplementRequest'),
('VIEW_SUPPLEMENT_REQUEST', '/supplementRequest?action=listSupplementRequest'),
('CREATE_COMPONENT_REQUEST', '/componentRequest?action=createComponentRequest'),
('ADD_COMPONENT_IN_REQUEST', '/componentRequest?action=addComponent'),
('REMOVE_COMPONENT_FROM_REQUEST', '/componentRequest?action=removeComponent'),
('LIST_COMPONENT_REQUEST_STAFF', '/componentRequest?action=listComponentRequestInStaffRole'),
('CANCEL_COMPONENT_REQUEST', '/componentRequest?action=cancelComponentRequest'),
('GET_COMPONENT_REQUEST_DETAILS', '/componentRequest?action=getRequestDetails'),
('VIEW_LIST_COMPONENT_REQUEST', '/componentRequest?action=viewListComponentRequest'),
('UPDATE_COMPONENT_REQUEST_STATUS', '/componentRequest?action=updateStatusComponentRequest'),
('UPDATE_SUPPLEMENT_REQUEST', '/supplementRequest?action=updateSupplementRequest'),
('DETAIL_COMPONENT_REQUEST', '/componentRequest?action=detailComponentRequest'),
('WARRANTY_CARD_REPAIR_CONTRACTOR', '/warrantyCardRepairContractor'),
('WARRANTY_CARD_DETAIL_CONTRACTOR', '/warrantyCardDetailContractor'),
('LIST_INVOICE_REPAIR', '/listInvoiceRepair'),
('CREATE_REPAIR_INVOICE', '/repairCreateInvoice'),
('VIEW_INVOICE_DETAIL', '/invoiceDetail'),
('CUSTOMER_CONTACT', '/customerContact?action=view'),
('VIEW_PACKAGE_WARRANTY', '/packageWarranty?action=view'),
('EDIT_PACKAGE_WARRANTY', '/packageWarranty?action=edit'),
('EXTEND_WARRANTY_DEFAULT', '/extendWarranty?action=extendDefault'),
('EXTEND_WARRANTY_EXTENDED', '/extendWarranty?action=extendExtended'),
('INVOICE_CONTROLLER', '/InvoiceController'),
('WARRANTY_CARD_PROCESS_CONTROLLER', '/WarrantyCardProcessController'),
('REPORT_COMPONENT_JSP', '/ReportComponent.jsp'),
('REDIRECT (Cusomer)', '/Redirect'),
('HOME (Cusomer)', '/Home'),
('BANK_PAY', '/vnpayajax'),
('Pay_RETURN', '/Payment/Return'),
('ADD_WARRANTY_CARD_2', '/WarrantyCard/Add?action=create'),
('ADD_WARRANTY_CARD_3', '/WarrantyCard/Add?action=receive'),
('DELETE_COMPONENT_IN_REPAIR_LIST', '/WarrantyCard/Detail?action=delete'),
('CREATE_CONTRACTOR_CARD_2', '/WarrantyCard/OutsourceRequest'),
('WARRANTY_CARD_OUTSOURCE_PROCESS', '/WarrantyCard/OutsourceRequest?action=processOutsource'),
('VIEW_COMPONENT_REQUEST_DASHBOARD', '/componentRequest?action=viewComponentRequestDashboard'),
('SEARCH_WARRANTY_CARD_FOR_EXPORT', '/searchwc'),
('EXPORT_WARRANTY_CARD', '/exportpdf'),
('SEND_WARRANTY_CARD', '/sendWC'),
('DELETE_BRAND', '/Brand?action=delete'),
('ADD_BRAND', '/Brand?action=add'),
('COMPONENT_TYPE_ADD', '/ComponentType?action=add'),
('COMPONENT_TYPE_DELETE', '/ComponentType?action=delete'),
('VIEW_PRODUCT_TYPE', '/ProductType'),
('ADD_PRODUCT_TYPE', '/ProductType?action=add'),
('DELETE_PRODUCT_TYPE', '/ProductType?action=delete'),
('IMPORT_COMPONENTS_2', '/ComponentWarehouse?action=clearErrorComponents'),
('VIEW_DETAIL_PRODUCT', '/viewProduct?action=update'),
('PAY_FOR_OUTSOURCE', '/Invoice/PayOutsource'),
('EXPORT_INVOICE_PDF_REPAIR', '/exportInvoicePDF'),
('VIEW_DETAIL_WARRANTYCARD_CONTRACTOR', '/warrantyCardDetailContractor'),
('PERMISSION', '/permissions'),
('SUPPLEMENT_REQUEST_JSP', '/supplementRequest.jsp'),
('DELETE_PRODUCT', '/viewProduct?action=delete'),
('CHAT_HISTORY', '/chatHistory')

INSERT INTO [dbo].[Role_Permissions] ([RoleID], [PermissionID] ) VALUES
(1,1),(2,1),(3,1),
(1,2),(3,2),
(1,3),(2,3),
(1,4),(3,4),
(1,5),(2,5),(3,5),
(1,6),(2,6),(3,6),
(1,7),(3,7),
(1,8),(3,8),
(1,9),(2,9),(3,9),
(1,10),(3,10),
(1,11),(3,11),
(1,12),(2,12),(5,12),
(2,13),
(1,14),(2,14),
(1,15),(2,15),(3,15),(4,15),(5,15),
(2,16),
(2,17),
(1,18),(2,18),
(2,19),
(2,20),
(2,21),
(2,22),
(1,23),(2,23),
(2,24),
(1,25),(3,25),
(1,26),(3,26),
(1,27),(2,27),
(1,28),
(1,29),(2,29),
(1,30),(2,30),(5,30),
(1,31),(2,31),(5,31),
(1,32),
(1,33),
(1,34),
(1,35),(2,35),(3,35),
(1,36),
(1,37),(2,37),(3,37),
(1,38),
(1,39),
(1,40),
(1,41),
(1,42),
(1,43),(2,43),(3,43),(4,43),(5,43),
(1,44),(2,44),(3,44),(4,44),(5,44),
(1,45),(2,45),(3,45),(4,45),(5,45),
(1,46),(2,46),(3,46),(4,46),(5,46),
(1,47),(5,47),
(1,48),(5,48),
(1,49),(5,49),
(1,50),
(1,51),
(1,52),
(1,53),
(1,54),
(1,55),
(1,56),
(1,57),
(1,58),
(1,59),
(1,60),
(1,61),
(1,62),
(1,63),
(1,64),
(1,65),
(1,66),
(1,67),
(1,68),(2,68),(3,68),(4,68),(5,68),
(1,69),(2,69),
(1,70),(2,70),
(1,71),(2,71),
(1,72),(2,72),
(1,73),(3,73),
(1,74),(2,74),
(1,75),(2,75),
(1,76),(2,76),
(1,77),(2,77),
(1,78),(2,78),
(1,79),(2,79),
(1,80),(3,80),
(1,81),(3,81),
(1,82),(3,82),
(1,83),(3,83),
(4,84),
(4,85),
(4,86),
(4,87),
(4,88),
(1,89),(5,89),
(1,90),(5,90),
(1,91),(5,91),
(1,92),(5,92),
(1,93),(5,93),
(1,94),(2,94),(3,94),(4,94),(5,94),
(1,95),
(1,96),
(1,97),(2,97),(3,97),(4,97),(5,97),
(1,98),(2,98),(3,98),(4,98),(5,98),
(2,99),
(2,100),
(2,101),
(2,102),
(2,103),
(2,104),
(2,105),
(2,106),
(1,107),(2,107),
(1,108),(2,108),
(1,109),(2,109),
(1,110),(3,110),
(1,111),(3,111),
(1,112),(3,112),
(1,113),(3,113),
(1,114),(3,114),
(1,115),(3,115),
(1,116),(3,116),
(1,117),(3,117),
(1,118),(2,118),(3,118),
(1,119),(2,119),
(4,120),
(4,121),
(1,122),
(1,123),(2,123),
(1,124),(2,124),(3,124),
(1,125)


