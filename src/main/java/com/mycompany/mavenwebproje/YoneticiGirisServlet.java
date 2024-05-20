package com.mycompany.mavenwebproje;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class YoneticiGirisServlet extends HttpServlet {

    String url = "jdbc:mysql://localhost:3306/proje";
    static String loggedInAdminID = "";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, "root", "");
            
            String yonetici_id = request.getParameter("yonetici_id");
            String sifre = request.getParameter("sifre");
            
            String sql = "SELECT * FROM yönetici WHERE yönetici_id=? AND sifre=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, yonetici_id);
            pstmt.setString(2, sifre);
            rs = pstmt.executeQuery();

            if (rs.next()) {
                loggedInAdminID = yonetici_id;
                response.sendRedirect("yoneticiPanel.html");
            } else {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Yönetici Giriş Sonucu</title>");
                out.println("<meta charset=\"UTF-8\">");
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Yönetici Girişi Sonucu</h2>");
                out.println("Hatalı kullanıcı adı veya şifre!<br>");
                out.println("<a href=\"YoneticiGiris.html\">Geri Dön</a>");
                out.println("</body>");
                out.println("</html>");
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

    public static String getLoggedInAdminID() {
        return loggedInAdminID;
    }
}
