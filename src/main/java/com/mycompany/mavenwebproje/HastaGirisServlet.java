package com.mycompany.mavenwebproje;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class HastaGirisServlet extends HttpServlet {
    
    static final String url = "jdbc:mysql://localhost:3306/proje";
    static final String username = "root";
    static final String password = "";
    static String loggedInUserID = "";
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, username, password);
            
            String hasta_id = request.getParameter("hasta_id");
            String sifre = request.getParameter("sifre");
            
            String sql = "SELECT * FROM hasta WHERE hasta_id=? AND sifre=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, hasta_id);
            pstmt.setString(2, sifre);
            rs = pstmt.executeQuery();

           if (rs.next()) {
            loggedInUserID = hasta_id;
            String sqlBildirim = "SELECT * FROM bildirim WHERE hasta_id=?";
            pstmt = conn.prepareStatement(sqlBildirim);
            pstmt.setString(1, hasta_id);
            rs = pstmt.executeQuery();
            if(rs.next()){
                response.sendRedirect("HastaBildirim");
            }
            else{
            response.sendRedirect("Hasta.html");
            }
            } else {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Hasta Giriş Sonucu</title>");
                out.println("<meta charset=\"UTF-8\">");
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Hasta Girişi Sonucu</h2>");
                out.println("Hatalı ID veya şifre!<br>");
                out.println("<a href=\"HastaGiris.html\">Geri Dön</a>");
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
    public static String getLoggedInUserID() {
        return loggedInUserID;
    }
}
