package com.mycompany.mavenwebproje.Hastabilgifonksiyonları;

import com.mycompany.mavenwebproje.HastaGirisServlet;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class HastaBilgiServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");
            
            String hasta_id = HastaGirisServlet.getLoggedInUserID();
String sqlHasta = "SELECT * FROM hasta WHERE hasta_id = ?";
String sqlAdres = "SELECT * FROM hasta_adres WHERE hasta_id = ?";
String sqlTelefon = "SELECT * FROM hasta_telefon WHERE hasta_id = ?";

pstmt = conn.prepareStatement(sqlHasta);
pstmt.setString(1, hasta_id);
ResultSet rsHasta = pstmt.executeQuery();

if (rsHasta.next()) {
    out.println("<!DOCTYPE html>");
    out.println("<html>");
    out.println("<head>");
    out.println("<title>Hasta</title>");
    out.println("<meta charset=\"UTF-8\">");
    out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
    out.println("</head>");
    out.println("<body>");
    out.println("<h2>Hastanın Özellikleri</h2>");
    out.println("<p>Hasta ID: " + rsHasta.getString("hasta_id") + "</p>");
    out.println("<p>Ad: " + rsHasta.getString("ad") + "</p>");
    out.println("<p>Soyad: " + rsHasta.getString("soyad") + "</p>");
    out.println("<p>Cinsiyet: " + rsHasta.getString("cinsiyet") + "</p>");
    out.println("<p>Doğum Tarihi: " + rsHasta.getString("dogum_tarih") + "</p>");

    pstmt = conn.prepareStatement(sqlAdres);
    pstmt.setString(1, hasta_id);
    ResultSet rsAdres = pstmt.executeQuery();

    out.println("<h3>Adresleri</h3>");
    out.println("<ul>");
    while (rsAdres.next()) {
        out.println("<li>" + rsAdres.getString("adres") + "</li>");
    }
    out.println("</ul>");

    pstmt = conn.prepareStatement(sqlTelefon);
    pstmt.setString(1, hasta_id);
    ResultSet rsTelefon = pstmt.executeQuery();

    out.println("<h3>Telefon Numaraları</h3>");
    out.println("<ul>");
    while (rsTelefon.next()) {
        out.println("<li>" + rsTelefon.getString("telefon_no") + "</li>");
    }
    out.println("</ul>");

    out.println("<a href=\"Hasta.html\">Geri Dön</a>");
    out.println("</body>");
    out.println("</html>");
} else {
    out.println("Hasta bulunamadı.");
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
