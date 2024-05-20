package com.mycompany.mavenwebproje.Hastabilgifonksiyonları;

import com.mycompany.mavenwebproje.HastaGirisServlet;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class HastaRandevularServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int tamSayfa=0;
        int sayfa = Integer.parseInt(request.getParameter("sayfa"));
        int sayfaBoyutu = 20; 

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");

            int baslangicIndeksi = (sayfa - 1) * sayfaBoyutu;

            String sql = "SELECT r.randevu_id, r.randevu_tarih, r.randevu_saat,r.doktor_id,d.ad, d.soyad, d.uzmanlık_alan, d.calıstıgı_hastane " +
                         "FROM randevu r " +
                         "JOIN doktor d ON r.doktor_id = d.doktor_id " +
                         "WHERE r.hasta_id = ? " +
                         "LIMIT ?, ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, HastaGirisServlet.getLoggedInUserID());
            pstmt.setInt(2, baslangicIndeksi);
            pstmt.setInt(3, sayfaBoyutu);
            
            rs = pstmt.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Hasta Randevuları</title>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Hasta Randevuları</h2>");
            out.println("<table border=\"1\">");
            out.println("<tr>");
            out.println("<th>Randevu ID</th>");
            out.println("<th>Randevu Tarihi</th>");
            out.println("<th>Randevu Saati</th>");
            out.println("<th>Doktorun Id</th>");
            out.println("<th>Doktorun Adı</th>");
            out.println("<th>Doktorun Soyadı</th>");
            out.println("<th>Doktorun Uzmanlık Alanı</th>");
            out.println("<th>Hastane</th>");
            out.println("</tr>");

            while (rs.next()) {
                tamSayfa++;
                out.println("<tr>");
                out.println("<td>" + rs.getString("randevu_id") + "</td>");
                out.println("<td>" + rs.getString("randevu_tarih") + "</td>");
                out.println("<td>" + rs.getString("randevu_saat") + "</td>");
                out.println("<td>" + rs.getString("doktor_id") + "</td>");
                out.println("<td>" + rs.getString("ad") + "</td>");
                out.println("<td>" + rs.getString("soyad") + "</td>");
                out.println("<td>" + rs.getString("uzmanlık_alan") + "</td>");
                out.println("<td>" + rs.getString("calıstıgı_hastane") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            if (sayfa > 1) {
                out.println("<a href=\"HastaRandevularServlet?sayfa=" + (sayfa - 1) + "\">Önceki Sayfa</a>");
            }
            if(tamSayfa==sayfaBoyutu){
                out.println("<a href=\"HastaRandevularServlet?sayfa=" + (sayfa + 1) + "\">Sonraki Sayfa</a>");
            }
            out.println("<a href=\"Hasta.html\">Geri Dön</a>");    

            out.println("</body>");
            out.println("</html>");

        } catch (SQLException e) {
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
