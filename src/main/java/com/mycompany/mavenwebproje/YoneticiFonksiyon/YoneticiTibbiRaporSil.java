package com.mycompany.mavenwebproje.YoneticiFonksiyon;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class YoneticiTibbiRaporSil extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String RaporID = request.getParameter("YtibbiRapor_id");
        String hastaID = null;

        Connection conn = null;
        PreparedStatement pstmt = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");

            String sqlget = "SELECT * FROM tibbirapor WHERE rapor_id = ?";
            pstmt = conn.prepareStatement(sqlget);
            pstmt.setString(1, RaporID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                hastaID = rs.getString("hasta_id");
            }

            String sql = "DELETE FROM tibbirapor WHERE rapor_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, RaporID);

            int rowCount = pstmt.executeUpdate();

            if (rowCount > 0) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Rapor Sil</title>");
                out.println("<meta charset=\"UTF-8\">");
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Tıbbi Rapor Sil</h2>");
                out.println("<p>Tıbbi Rapor başarıyla silindi.</p>");
                out.println("<a href=\"yoneticiPanel.html\">Geri Dön</a>");
                out.println("</body>");
                out.println("</html>");

                String sqlbildirim = "INSERT INTO bildirim (hasta_id,mesaj) VALUES(?,?)";
                pstmt = conn.prepareStatement(sqlbildirim);
                pstmt.setString(1, hastaID);
                pstmt.setString(2, "Bir tıbbi rapor silindi");
                pstmt.executeUpdate();
            } else {
                out.println("<p>Belirtilen ID'ye sahip tıbbi rapor bulunamadı veya silinemedi.</p>");
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
