package com.mycompany.mavenwebproje.Hastabilgifonksiyonları;

import com.mycompany.mavenwebproje.HastaGirisServlet;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class RandevuGuncelleServlet extends HttpServlet {

    static String SelectedRandevu="";
    
   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        
        String randevuID = request.getParameter("r_id");
        String hastaID=HastaGirisServlet.getLoggedInUserID();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");

            String sql = "SELECT * FROM randevu  WHERE randevu_id = ? and hasta_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, randevuID);
            pstmt.setString(2, hastaID);

             rs = pstmt.executeQuery();

            if (rs.next()) {
                SelectedRandevu=randevuID;
                response.sendRedirect("RandevuGuncelle.html");
            } else {
                out.println("<p>Belirtilen ID'ye sahip randevu bulunamadı veya Randevu size ait değil.</p>");
                out.println("<a href=\"Hasta.html\">Geri Dön</a>");
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

    public static String getSelectedRandevu() {
        return SelectedRandevu;
    }

    
   

}
