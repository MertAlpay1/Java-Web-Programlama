
package com.mycompany.mavenwebproje.DoktorFonksiyonlar;

import com.mycompany.mavenwebproje.DoktorGirisServlet;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class DoktorTibbiRaporGuncelleServlet extends HttpServlet {

    static String selectedRaporID;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String raporID = request.getParameter("tibbi_id");
        String hastaID;
        String doktorID=DoktorGirisServlet.getLoggedInDocID();
        Connection conn = null;
        PreparedStatement pstmt = null;
        
       try {
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");
    
    String sqlhasta = "SELECT hasta_id FROM tibbirapor WHERE rapor_id=?";
    pstmt = conn.prepareStatement(sqlhasta);
    pstmt.setString(1, raporID);
    ResultSet rs = pstmt.executeQuery();

    if (rs.next()) {
        hastaID = rs.getString("hasta_id");

        String sqlcheck = "SELECT * FROM randevu WHERE hasta_id=? AND doktor_id=?";
        pstmt = conn.prepareStatement(sqlcheck);
        pstmt.setString(1, hastaID);
        pstmt.setString(2, doktorID);
        
        rs = pstmt.executeQuery();

        if (rs.next()) {
            String sqlSelect = "SELECT * FROM tibbirapor WHERE rapor_id = ?";
            pstmt = conn.prepareStatement(sqlSelect);
            pstmt.setString(1, raporID);
            
            rs= pstmt.executeQuery();
             
            if (rs.next()) {
                selectedRaporID = raporID;
                response.sendRedirect("DoktorRaporGuncelle.html");
                
            } else {
                out.println("<h2>Böyle bir rapor bulunmamaktadır!</h2>");
                out.println("<a href=\"doktorPanel.html\">Geri Dön</a>");
            }
        } else {
            out.println("<h2>Rapor size ait değildir!</h2>");
            out.println("<a href=\"doktorPanel.html\">Geri Dön</a>");
        }
    } else {
        out.println("<h2>Belirtilen rapor ID'sine sahip bir rapor bulunamadı!</h2>");
        out.println("<a href=\"doktorPanel.html\">Geri Dön</a>");
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
