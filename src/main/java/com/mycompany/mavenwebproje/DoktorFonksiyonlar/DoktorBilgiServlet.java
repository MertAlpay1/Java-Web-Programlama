package com.mycompany.mavenwebproje.DoktorFonksiyonlar;

import com.mycompany.mavenwebproje.DoktorGirisServlet;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class DoktorBilgiServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");
            String doktor_id = DoktorGirisServlet.getLoggedInDocID(); 
           
            String sqlDoktor = "SELECT * FROM doktor WHERE doktor_id = ?";
            pstmt = conn.prepareStatement(sqlDoktor);
            pstmt.setString(1, doktor_id);
            ResultSet rsDoktor = pstmt.executeQuery();

            if (rsDoktor.next()) {
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Doktor</title>");
                out.println("<meta charset=\"UTF-8\">");
                out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
                out.println("</head>");
                out.println("<body>");
                out.println("<h2>Doktorun Özellikleri</h2>");
                out.println("<p>Doktor ID: " + rsDoktor.getString("doktor_id") + "</p>");
                out.println("<p>Ad: " + rsDoktor.getString("ad") + "</p>");
                out.println("<p>Soyad: " + rsDoktor.getString("soyad") + "</p>");
                out.println("<p>Uzmanlık Alanı: " + rsDoktor.getString("uzmanlik_alan") + "</p>");
                out.println("<p>Çalıştığı Hastane:"+rsDoktor.getString("calıstıgı_hastane")+"</p>");
                
                out.println("<a href=\"doktorPanel.html\">Geri Dön</a>");
                out.println("</body>");
                out.println("</html>");
            } else {
                out.println("Doktor bulunamadı.");
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
