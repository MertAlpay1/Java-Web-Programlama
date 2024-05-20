package com.mycompany.mavenwebproje.YoneticiFonksiyon;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class YoneticiDoktorAdGuncelleServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");
            String yeniAd = request.getParameter("ad");
            String doktorID = YoneticiDoktorSecServlet.getDoktor();

            String sql = "UPDATE doktor SET ad=? WHERE doktor_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, yeniAd);
            pstmt.setString(2, doktorID);
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
                out.println("<p>Doktor adı başarıyla güncellendi.</p>");
                out.println("<a href=\"YDGuncelleme.html\">Yönetici Paneline Geri Dön</a>");
                out.println("</body>");
                out.println("</html>");
            } else {
                out.println("İşlem sırasında bir hata oluştu.");
                out.println("<a href=\"YDGuncelleme.html\">Yönetici Paneline Geri Dön</a>");
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
