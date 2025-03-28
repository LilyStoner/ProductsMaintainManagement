package Controller.Invoice;

import DAO.InvoiceDAO;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Map;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class ExportInvoicePDF extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    private final InvoiceDAO invoiceDAO = new InvoiceDAO();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String invoiceIdParam = request.getParameter("invoiceId");
        if (invoiceIdParam == null || invoiceIdParam.trim().isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invoice ID is required.");
            return;
        }
        int invoiceId;
        try {
            invoiceId = Integer.parseInt(invoiceIdParam);
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid Invoice ID.");
            return;
        }
        
        // Lấy thông tin chi tiết của invoice từ DAO
        Map<String, Object> invoiceDetail = invoiceDAO.getInvoiceDetails(invoiceId);
        if (invoiceDetail == null || invoiceDetail.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Invoice not found.");
            return;
        }
        
        // Thiết lập header cho file PDF
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=\"Invoice_" + invoiceId + ".pdf\"");
        
        Document document = new Document();
        try {
            PdfWriter.getInstance(document, response.getOutputStream());
            document.open();
            
            // Tạo font
            Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16);
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12);
            Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 11);
            
            // Tiêu đề PDF
            Paragraph title = new Paragraph("Invoice Detail", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);
            document.add(new Paragraph(" ")); // khoảng trắng
            
            // Tạo bảng hiển thị thông tin invoice với 2 cột
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(100);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            
            // Thêm các dòng thông tin vào bảng
            addRow(table, "Invoice Number", getSafe(invoiceDetail.get("InvoiceNumber")), headerFont, normalFont);
            addRow(table, "Amount", getSafe(invoiceDetail.get("Amount")), headerFont, normalFont);
            addRow(table, "Issued Date", formatDate(invoiceDetail.get("IssuedDate")), headerFont, normalFont);
            addRow(table, "Due Date", formatDate(invoiceDetail.get("DueDate")), headerFont, normalFont);
            addRow(table, "Status", getSafe(invoiceDetail.get("Status")), headerFont, normalFont);
            addRow(table, "Created By", getSafe(invoiceDetail.get("CreatedByName")), headerFont, normalFont);
            addRow(table, "Received By", getSafe(invoiceDetail.get("ReceivedByName")), headerFont, normalFont);
            addRow(table, "Warranty Card Code", getSafe(invoiceDetail.get("WarrantyCardCode")), headerFont, normalFont);
            addRow(table, "Issue Description", getSafe(invoiceDetail.get("IssueDescription")), headerFont, normalFont);
            
            document.add(table);
            
            // Footer (tuỳ chọn)
            Paragraph footer = new Paragraph("Generated by our system", normalFont);
            footer.setAlignment(Element.ALIGN_CENTER);
            document.add(footer);
            
        } catch (DocumentException e) {
            throw new IOException("Error while creating PDF: " + e.getMessage());
        } finally {
            document.close();
        }
    }
    
    // Hàm hỗ trợ thêm 1 dòng (row) vào bảng PDF
    private void addRow(PdfPTable table, String label, String value, Font headerFont, Font normalFont) {
        PdfPCell cell1 = new PdfPCell(new Phrase(label, headerFont));
        cell1.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell1.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell1);
        
        PdfPCell cell2 = new PdfPCell(new Phrase(value, normalFont));
        cell2.setHorizontalAlignment(Element.ALIGN_LEFT);
        cell2.setBorder(PdfPCell.NO_BORDER);
        table.addCell(cell2);
    }
    
    // Hàm trả về chuỗi an toàn, nếu đối tượng null thì trả về chuỗi rỗng
    private String getSafe(Object obj) {
        return (obj == null) ? "" : obj.toString();
    }
    
    // Hàm định dạng ngày với mẫu "yyyy-MM-dd"
    private String formatDate(Object dateObj) {
        if (dateObj == null) return "";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return sdf.format(dateObj);
        } catch (Exception e) {
            return dateObj.toString();
        }
    }
}
