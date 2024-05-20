package com.mycompany.mavenwebproje.YoneticiFonksiyon;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class YoneticiHastaDogumTarihiGuncelle extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");
            String yeniDogumTarihi = request.getParameter("dogumTarihi");
            String hastaID = YoneticiHastaSecServlet.getHasta();

            String sql = "UPDATE hasta SET dogum_tarih=? WHERE hasta_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, yeniDogumTarihi);
            pstmt.setString(2, hastaID);
            int rowCount = pstmt.executeUpdate();

            if (rowCount > 0) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>İşlem Başarılı</title>");
                out.println("<meta charset=\"UTF-8\">");
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>İşlem Başarılı</h2>");
                out.println("<p>Hasta doğum tarihi başarıyla güncellendi.</p>");
                out.println("<a href=\"YHGuncelleme.html\">Geri Dön</a>");
                out.println("</body>");
                out.println("</html>");
            } else {
                out.println("İşlem sırasında bir hata oluştu.");
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
