package com.mycompany.mavenwebproje.Hastabilgifonksiyonları;

import com.google.gson.Gson;
import com.mycompany.mavenwebproje.HastaGirisServlet;
import com.mycompany.mavenwebproje.TibbiRapor;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class HastaRaporIcerikDegisServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String yeniIcerik = request.getParameter("ıcerik");
        String raporID = HastaTibbiRaporGuncelleServlet.getSelectedRaporID();
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");
            
            String sqlSelect = "SELECT * FROM tibbirapor WHERE rapor_id = ?";
            pstmt = conn.prepareStatement(sqlSelect);
            pstmt.setString(1, raporID);
            rs = pstmt.executeQuery();
            
            if (rs.next()) {
                String tarih = rs.getString("tarih");
                String url = rs.getString("url");
                
                TibbiRapor tibbiRapor = new TibbiRapor(tarih, yeniIcerik, url, HastaGirisServlet.getLoggedInUserID());
                Gson gson = new Gson();
                String yeniJson = gson.toJson(tibbiRapor);
        
                String sqlUpdate = "UPDATE tibbirapor SET içerik = ?, json = ? WHERE rapor_id = ?";
                pstmt = conn.prepareStatement(sqlUpdate);
                pstmt.setString(1, yeniIcerik);
                pstmt.setString(2, yeniJson);
                pstmt.setString(3, raporID);
                
                int rowCount = pstmt.executeUpdate();
                
                if (rowCount > 0) {
                    out.println("<h2>Rapor içeriği başarıyla güncellendi!</h2>");
                    out.println("<a href=\"HastaRaporGuncelle.html\">Geri Dön</a>");
                } else {
                    out.println("<h2>Rapor içeriği güncellenirken bir hata oluştu!</h2>");
                    out.println("<a href=\"HastaRaporGuncelle.html\">Geri Dön</a>");
                }
            } else {
                out.println("<h2>Rapor bulunamadı!</h2>");
                out.println("<a href=\"Hasta.html\">Geri Dön</a>");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }
}
