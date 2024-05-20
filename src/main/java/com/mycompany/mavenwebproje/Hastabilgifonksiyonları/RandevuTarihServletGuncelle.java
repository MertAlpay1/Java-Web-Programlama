package com.mycompany.mavenwebproje.Hastabilgifonksiyonları;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class RandevuTarihServletGuncelle extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String yeniTarih = request.getParameter("tarih");
        String randevuID = RandevuGuncelleServlet.getSelectedRandevu();

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");

            String sql = "UPDATE randevu SET randevu_tarih = ? WHERE randevu_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, yeniTarih);
            pstmt.setString(2, randevuID);

            int rowCount = pstmt.executeUpdate();

            if (rowCount > 0) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Randevu Tarihi Güncellendi</title>");
                out.println("<meta charset=\"UTF-8\">");
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Randevu Tarihi Güncellendi</h2>");
                out.println("<p>Randevu tarihi başarıyla güncellendi.</p>");
                out.println("<a href=\"RandevuGuncelle.html\">Geri Dön</a>");
                out.println("</body>");
                out.println("</html>");
            } else {
                out.println("<p>Belirtilen ID'ye sahip randevu bulunamadı veya güncellenemedi.</p>");
                out.println("<a href=\"RandevuGuncelle.html\">Geri Dön</a>");
            }
        } catch (SQLException e) {
            out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
