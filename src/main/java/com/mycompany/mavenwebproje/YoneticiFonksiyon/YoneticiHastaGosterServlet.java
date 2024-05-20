package com.mycompany.mavenwebproje.YoneticiFonksiyon;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class YoneticiHastaGosterServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        int tamSayfa=0;
        int sayfa = Integer.parseInt(request.getParameter("sayfa"));
        int sayfaBoyutu = 20; 

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");

            String sql = "SELECT  h.*, a.adres, t.telefon_no " +
                         "FROM hasta h " +
                         "INNER JOIN hasta_adres a ON h.hasta_id = a.hasta_id " +
                         "INNER JOIN hasta_telefon t ON h.hasta_id = t.hasta_id " +
                         "ORDER BY h.hasta_id ASC " + 
                         "LIMIT ? OFFSET ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, sayfaBoyutu); 
            pstmt.setInt(2, (sayfa - 1) * sayfaBoyutu);
            rs = pstmt.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Tüm Hastalar</title>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Tüm Hastalar</h2>");
            out.println("<table border=\"1\">");
            out.println("<tr>");
            out.println("<th>Hasta ID</th>");
            out.println("<th>Ad</th>");
            out.println("<th>Soyad</th>");
            out.println("<th>Cinsiyet</th>");
            out.println("<th>Doğum Tarihi</th>");
            out.println("<th>Adres</th>");
            out.println("<th>Telefon Numarası</th>");
            out.println("</tr>");

            while (rs.next()) {
                tamSayfa++;
                out.println("<tr>");
                out.println("<td>" + rs.getString("hasta_id") + "</td>");
                out.println("<td>" + rs.getString("ad") + "</td>");
                out.println("<td>" + rs.getString("soyad") + "</td>");
                out.println("<td>" + rs.getString("cinsiyet") + "</td>");
                out.println("<td>" + rs.getString("dogum_tarih") + "</td>");
                out.println("<td>" + rs.getString("adres") + "</td>");
                out.println("<td>" + rs.getString("telefon_no") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            if (sayfa > 1) {
                out.println("<a href=\"YoneticiHastaGosterServlet?sayfa=" + (sayfa - 1) + "\">Önceki Sayfa</a>");
            }
            if(tamSayfa==sayfaBoyutu){
            out.println("<a href=\"YoneticiHastaGosterServlet?sayfa=" + (sayfa + 1) + "\">Sonraki Sayfa</a>");
            }
            out.println("<a href=\"yoneticiPanel.html\">Geri Dön</a>");
            out.println("</body>");
            out.println("</html>");
            
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
