
package com.mycompany.mavenwebproje.DoktorFonksiyonlar;

import com.mycompany.mavenwebproje.DoktorGirisServlet;
import com.mycompany.mavenwebproje.HastaGirisServlet;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;


public class DoktorTibbiRaporSilServlet extends HttpServlet {
    
   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
       
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String raporID = request.getParameter("Trapor_id");
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
            String sqlDelete = "DELETE FROM tibbirapor WHERE rapor_id = ?";
            pstmt = conn.prepareStatement(sqlDelete);
            pstmt.setString(1, raporID);
            
            int rowCount = pstmt.executeUpdate();
            
            if (rowCount > 0) {
                
                String sqlbildirim = "INSERT INTO bildirim (hasta_id,mesaj) VALUES(?,?)";
                pstmt = conn.prepareStatement(sqlbildirim);
                pstmt.setString(1, hastaID);
                pstmt.setString(2, "Bir tıbbi rapor silindi");
                pstmt.executeUpdate();
                
                out.println("<h2>Rapor başarıyla silindi!</h2>");
                out.println("<a href=\"doktorPanel.html\">Geri Dön</a>");

            } else {
                out.println("<h2>Böyle bir rapor bulunmamaktadır!</h2>");
                out.println("<a href=\"doktorPanel.html\">Geri Dön</a>");
            }
        } else {
            out.println("<h2>Silmeye çalıştığınız rapor size ait değildir!</h2>");
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
     
}
