package com.mycompany.mavenwebproje;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class DoktorKayit extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String ad = request.getParameter("ad");
        String soyad = request.getParameter("soyad");
        String sifre = request.getParameter("sifre");
        String uzmanlik = request.getParameter("uzmanlik");
        String hastane = request.getParameter("hastane");

        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");

            String sql = "INSERT INTO doktor (ad, soyad, sifre, uzmanlık_alan, calıstıgı_hastane) VALUES (?, ?, ?, ?, ?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, ad);
            pstmt.setString(2, soyad);
            pstmt.setString(3, sifre);
            pstmt.setString(4, uzmanlik);
            pstmt.setString(5, hastane);

            int rowCount = pstmt.executeUpdate();
            if (rowCount > 0) {
                out.println("<h1>Doktor başarıyla kaydedildi.</h1>");
            } else {
                out.println("<h1>Doktor kaydedilirken bir hata oluştu.</h1>");
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
