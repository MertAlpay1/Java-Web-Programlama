package com.mycompany.mavenwebproje.Hastabilgifonksiyonları;

import com.mycompany.mavenwebproje.HastaGirisServlet;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class HastaRandevuAlServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");

            String hastaID = HastaGirisServlet.getLoggedInUserID();
            String doktorID = request.getParameter("doktorID");
            String randevuTarih = request.getParameter("randevuTarih");
            String randevuSaat = request.getParameter("randevuSaat");

            String sql = "INSERT INTO randevu (randevu_tarih, randevu_saat, hasta_id, doktor_id) VALUES (?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, randevuTarih);
            pstmt.setString(2, randevuSaat);
            pstmt.setString(3, hastaID);
            pstmt.setString(4, doktorID);

            int rowCount = pstmt.executeUpdate();
            if (rowCount > 0) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Randevu Alındı</title>");
                out.println("<meta charset=\"UTF-8\">");
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Randevu Alındı</h2>");
                out.println("<p>Randevunuz başarıyla alındı.</p>");
                out.println("<a href=\"Hasta.html\">Geri Dön</a>");
                out.println("</body>");
                out.println("</html>");
            } else {
                out.println("Randevu alınırken bir hata oluştu.");
                out.println("<a href=\"Hasta.html\">Geri Dön</a>");
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
