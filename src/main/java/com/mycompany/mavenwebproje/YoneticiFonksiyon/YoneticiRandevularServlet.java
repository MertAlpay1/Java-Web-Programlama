package com.mycompany.mavenwebproje.YoneticiFonksiyon;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class YoneticiRandevularServlet extends HttpServlet {

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

            String sql = "SELECT r.randevu_id, r.randevu_tarih, r.randevu_saat, " +
             "h.hasta_id, h.ad, h.soyad, h.cinsiyet, h.dogum_tarih, " +
             "a.adres, t.telefon_no, " +
             "d.doktor_id, d.ad AS doktor_ad, d.soyad AS doktor_soyad, " +
             "d.uzmanlık_alan AS uzmanlık, d.calıstıgı_hastane AS hastane " +
             "FROM randevu r " +
             "JOIN hasta h ON r.hasta_id = h.hasta_id " +
             "JOIN hasta_adres a ON h.hasta_id = a.hasta_id " +
             "JOIN hasta_telefon t ON h.hasta_id = t.hasta_id " +
             "JOIN doktor d ON r.doktor_id = d.doktor_id " +
             "LIMIT ?, ?";

            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, baslangicIndeksi);
            pstmt.setInt(2, sayfaBoyutu);

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
            out.println("<th>Hasta Id</th>");
            out.println("<th>Hasta Adı</th>");
            out.println("<th>Hasta Soyadı</th>");
            out.println("<th>Hasta Cinsiyet</th>");
            out.println("<th>Hasta Doğum Tarihi</th>");
            out.println("<th>Hasta Adresi</th>");
            out.println("<th>Hasta Telefon No</th>");
            out.println("<th>Doktor Id</th>");
            out.println("<th>Doktor Adı</th>");
            out.println("<th>Doktor Soyadı</th>");
            out.println("<th>Doktor Uzmanlık Alanı</th>");
            out.println("<th>Doktor Hastane</th>");
            out.println("</tr>");

            while (rs.next()) {
                tamSayfa++;
                out.println("<tr>");
                out.println("<td>" + rs.getString("randevu_id") + "</td>");
                out.println("<td>" + rs.getString("randevu_tarih") + "</td>");
                out.println("<td>" + rs.getString("randevu_saat") + "</td>");
                out.println("<td>" + rs.getString("hasta_id") + "</td>");
                out.println("<td>" + rs.getString("ad") + "</td>");
                out.println("<td>" + rs.getString("soyad") + "</td>");
                out.println("<td>" + rs.getString("cinsiyet") + "</td>");
                out.println("<td>" + rs.getString("dogum_tarih") + "</td>");
                out.println("<td>" + rs.getString("adres") + "</td>");
                out.println("<td>" + rs.getString("telefon_no") + "</td>");
                out.println("<td>" + rs.getString("doktor_id") + "</td>");
                out.println("<td>" + rs.getString("doktor_ad") + "</td>");
                out.println("<td>" + rs.getString("doktor_soyad") + "</td>");
                out.println("<td>" + rs.getString("uzmanlık") + "</td>");
                out.println("<td>" + rs.getString("hastane") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            if (sayfa > 1) {
                out.println("<a href=\"YoneticiRandevularServlet?sayfa=" + (sayfa - 1) + "\">Önceki Sayfa</a>");
            }
             if(tamSayfa==sayfaBoyutu){
            out.println("<a href=\"YoneticiRandevularServlet?sayfa=" + (sayfa + 1) + "\">Sonraki Sayfa</a>");
             }
            out.println("<a href=\"yoneticiPanel.html\">Geri Dön</a>");    

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
