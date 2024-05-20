package com.mycompany.mavenwebproje.YoneticiFonksiyon;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class DoktorSilServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        int doktor_id = Integer.parseInt(request.getParameter("doktor_id"));
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");

            String Sql_check = "SELECT * FROM doktor WHERE doktor_id=?";
            pstmt = conn.prepareStatement(Sql_check);
            pstmt.setInt(1, doktor_id);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) { 
                
                String Sql_randevu = "SELECT * FROM randevu WHERE doktor_id=?";
                pstmt = conn.prepareStatement(Sql_randevu);
                pstmt.setInt(1, doktor_id);
                ResultSet rsRandevu = pstmt.executeQuery();
                if(!rsRandevu.next()){
                
                String sql = "DELETE FROM doktor WHERE doktor_id=?";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, doktor_id);
                int rowCount = pstmt.executeUpdate();
                
                if (rowCount > 0) {
                    out.println("<h1>Doktor başarıyla silindi.</h1>");
                } else {
                    out.println("<h1>Doktor silinirken bir hata oluştu.</h1>");
                }
              }
                else {
                     out.println("<h1>Doktorun aktif randevusu bulunduğundan doktor silinemedi.</h1>");
                }
            } else { 
                out.println("<h1>Böyle bir doktor bulunamadı.</h1>");
            }
        } catch (SQLException | ClassNotFoundException e) {
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
