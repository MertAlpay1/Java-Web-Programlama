package com.mycompany.mavenwebproje.DoktorFonksiyonlar;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class HastaKayit extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String ad = request.getParameter("ad");
        String soyad = request.getParameter("soyad");
        String sifre = request.getParameter("sifre");
        String cinsiyet = request.getParameter("cinsiyet");
        String dogumTarihi = request.getParameter("dogum_tarihi");
        String adres = request.getParameter("adres");
        String telefon = request.getParameter("telefon_no");
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");
            
            String sql = "INSERT INTO hasta (ad, soyad, sifre, cinsiyet, dogum_tarih) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, ad);
            pstmt.setString(2, soyad);
            pstmt.setString(3, sifre);
            pstmt.setString(4, cinsiyet);
            pstmt.setString(5, dogumTarihi);
            int rowCount = pstmt.executeUpdate();
            
            ResultSet generatedKeys = pstmt.getGeneratedKeys();
            int kullaniciId = -1;
            if (generatedKeys.next()) {
                kullaniciId = generatedKeys.getInt(1);
            }
            
            if (kullaniciId != -1) {
                sql = "INSERT INTO hasta_adres (hasta_id, adres) VALUES (?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, kullaniciId);
                pstmt.setString(2, adres);
                pstmt.executeUpdate();
                
                sql = "INSERT INTO hasta_telefon (hasta_id, telefon_no) VALUES (?, ?)";
                pstmt = conn.prepareStatement(sql);
                pstmt.setInt(1, kullaniciId);
                pstmt.setString(2, telefon);
                pstmt.executeUpdate();
            }
            
            if (rowCount > 0) {
                out.println("<h1>Hasta başarıyla kaydedildi.</h1>");
            } else {
                out.println("<h1>Hasta kaydedilirken bir hata oluştu.</h1>");
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
