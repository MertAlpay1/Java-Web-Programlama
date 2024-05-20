
package com.mycompany.mavenwebproje.YoneticiFonksiyon;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class YoneticiHastaSecServlet extends HttpServlet {
    String url = "jdbc:mysql://localhost:3306/proje";
    static String SecilenHasta = "";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "");
            
            String hasta_id = request.getParameter("h_id");
            
            String sql = "SELECT * FROM hasta WHERE hasta_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, hasta_id);
            
            rs = pstmt.executeQuery();

           if (rs.next()) {
            SecilenHasta = hasta_id;
            response.sendRedirect("YHGuncelleme.html");
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
    public static String getHasta() {
        return SecilenHasta;
    }

}
