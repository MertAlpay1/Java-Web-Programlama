package com.mycompany.mavenwebproje.Hastabilgifonksiyonları;

import com.mycompany.mavenwebproje.HastaGirisServlet;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class HastaTibbiRaporGuncelleServlet extends HttpServlet {

    static String selectedRaporID;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
       
        String raporID = request.getParameter("tibbi_id");
        String hastaID = HastaGirisServlet.getLoggedInUserID();
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");
            
            String sql = "SELECT * FROM tibbirapor WHERE rapor_id = ? AND hasta_id=?";
            pstmt = conn.prepareStatement(sql);

            pstmt.setString(1, raporID);
            pstmt.setString(2, hastaID);

            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                selectedRaporID = raporID;
                response.sendRedirect("HastaRaporGuncelle.html");
            } else {
                out.println("<h2>Raport ID geçersiz veya Rapor size ait değil!</h2>");
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

    public static String getSelectedRaporID() {
        return selectedRaporID;
    }
}
