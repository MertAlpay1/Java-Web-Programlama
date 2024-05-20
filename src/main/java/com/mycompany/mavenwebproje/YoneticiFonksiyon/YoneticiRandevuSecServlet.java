
package com.mycompany.mavenwebproje.YoneticiFonksiyon;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class YoneticiRandevuSecServlet extends HttpServlet {
String url = "jdbc:mysql://localhost:3306/proje";
    static String SecilenRandevu = "";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "");
            
            String randevu_id = request.getParameter("randevu_id");
            
            String sql = "SELECT * FROM randevu WHERE randevu_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, randevu_id);
            
            rs = pstmt.executeQuery();

           if (rs.next()) {
            SecilenRandevu = randevu_id;
            response.sendRedirect("YRGuncelleme.html");
            } else {
                out.println("Hasta id geçersiz<br>");
                out.println("<a href=\"yoneticiPanel.html\">Geri Dön</a>");

            }
        } catch (SQLException | ClassNotFoundException e) {
            out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    public static String getRandevu() {
        return SecilenRandevu;
    }

}
