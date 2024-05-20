package com.mycompany.mavenwebproje.YoneticiFonksiyon;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class hastaSilServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int hasta_id = Integer.parseInt(request.getParameter("hasta_id"));
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");

            String checkSql = "SELECT * FROM hasta WHERE hasta_id=?";
            pstmt = conn.prepareStatement(checkSql);
            pstmt.setInt(1, hasta_id);
            rs = pstmt.executeQuery();
            
            if (rs.next()) { 
                String sql_adres = "DELETE FROM hasta_adres WHERE hasta_id=?" ;
                pstmt = conn.prepareStatement(sql_adres);
                pstmt.setInt(1,hasta_id); 
                pstmt.executeUpdate();

                String sql_telefon = "DELETE FROM hasta_telefon WHERE hasta_id=?" ;
                pstmt = conn.prepareStatement(sql_telefon);
                pstmt.setInt(1,hasta_id); 
                pstmt.executeUpdate();
                
                String sql_randevu = "DELETE FROM randevu WHERE hasta_id=?" ;
                pstmt = conn.prepareStatement(sql_randevu);
                pstmt.setInt(1,hasta_id); 
                pstmt.executeUpdate();
                
                String sql_rapor = "DELETE FROM tibbirapor WHERE hasta_id=?" ;
                pstmt = conn.prepareStatement(sql_rapor);
                pstmt.setInt(1,hasta_id); 
                pstmt.executeUpdate();
                
                String sql_bildirim = "DELETE FROM bildirim WHERE hasta_id=?" ;
                pstmt = conn.prepareStatement(sql_bildirim);
                pstmt.setInt(1,hasta_id); 
                pstmt.executeUpdate();

                String sql = "DELETE FROM hasta WHERE hasta_id=?" ;
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1,hasta_id); 
                pstmt.executeUpdate();

                out.println("<h1>Hasta başarıyla silindi.</h1>");
            } else {
                out.println("<h1>Böyle bir hasta bulunamadı.</h1>");
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
}
