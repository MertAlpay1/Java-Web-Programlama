
package com.mycompany.mavenwebproje.YoneticiFonksiyon;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class YoneticiDoktorSecServlet extends HttpServlet {
    String url = "jdbc:mysql://localhost:3306/proje";
    static String SecilenDoktor = "";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "");
            
            String doktor_id = request.getParameter("d_id");
            
            String sql = "SELECT * FROM doktor WHERE doktor_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, doktor_id);
            
            rs = pstmt.executeQuery();

           if (rs.next()) {
            SecilenDoktor = doktor_id;
            response.sendRedirect("YDGuncelleme.html");
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
    public static String getDoktor() {
        return SecilenDoktor;
    }

}
