package com.mycompany.mavenwebproje.YoneticiFonksiyon;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class YoneticiRandevuSil extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String randevuID = request.getParameter("r_id");

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");

            String sql = "DELETE FROM randevu WHERE randevu_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, randevuID);

            int rowCount = pstmt.executeUpdate();

            if (rowCount > 0) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Randevu Sil</title>");
                out.println("<meta charset=\"UTF-8\">");
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Randevu Sil</h2>");
                out.println("<p>Randevu başarıyla silindi.</p>");
                out.println("<a href=\"yoneticiPanel.html\">Geri Dön</a>");
                out.println("</body>");
                out.println("</html>");
            } else { 
                out.println("<p>Belirtilen ID'ye sahip randevu bulunamadı veya silinemedi.</p>");
                out.println("<a href=\"yoneticiPanel.html\">Geri Dön</a>");
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
