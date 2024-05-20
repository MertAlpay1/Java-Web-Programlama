package com.mycompany.mavenwebproje.Hastabilgifonksiyonları;

import com.mycompany.mavenwebproje.HastaGirisServlet;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class HastaTibbiRaporSilServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String raporID = request.getParameter("Trapor_id");
        String hastaID=HastaGirisServlet.getLoggedInUserID();
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");
            
            String sqlDelete = "DELETE FROM tibbirapor WHERE rapor_id = ? AND hasta_id=?";
            pstmt = conn.prepareStatement(sqlDelete);
            pstmt.setString(1, raporID);
            pstmt.setString(2, hastaID);
            
            int rowCount = pstmt.executeUpdate();
            
            if (rowCount > 0) {
                out.println("<h2>Rapor başarıyla silindi!</h2>");
                out.println("<a href=\"Hasta.html\">Geri Dön</a>");
            } else {
                out.println("<h2>Böyle bir Raporunuz bulunmamaktadır!</h2>");
                out.println("<a href=\"Hasta.html\">Geri Dön</a>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }
}
