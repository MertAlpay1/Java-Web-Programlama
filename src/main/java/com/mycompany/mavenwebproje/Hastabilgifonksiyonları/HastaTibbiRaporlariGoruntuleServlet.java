package com.mycompany.mavenwebproje.Hastabilgifonksiyonları;

import com.google.cloud.storage.*;
import java.io.*;
import java.sql.*;
import java.util.UUID;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import com.mycompany.mavenwebproje.HastaGirisServlet;
import jakarta.servlet.annotation.MultipartConfig;

public class HastaTibbiRaporlariGoruntuleServlet extends HttpServlet {

   
     protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String hastaID = HastaGirisServlet.getLoggedInUserID();
        int tamSayfa=0;
        int sayfa = Integer.parseInt(request.getParameter("sayfa"));
        int sayfaBoyutu = 20;
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");
            String sql = "SELECT * FROM tibbirapor WHERE hasta_id=? LIMIT ? OFFSET ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, hastaID);
            pstmt.setInt(2, sayfaBoyutu); 
            pstmt.setInt(3, (sayfa - 1) * sayfaBoyutu);
            rs = pstmt.executeQuery();
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Hasta Tibbi Raporları</title>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Hasta Tibbi Raporları</h2>");
            out.println("<table border=\"1\">");
            out.println("<tr>");
            out.println("<th>Tıbbi Rapor ID</th>");
            out.println("<th>Rapor Tarihi</th>");
            out.println("<th>Rapor içeriği </th>");
            out.println("<th>Rapor Sonucu </th>");
            while (rs.next()) {
                tamSayfa++;
                out.println("<tr>");
                out.println("<td>" + rs.getString("rapor_id") + "</td>");
                out.println("<td>" + rs.getString("tarih") + "</td>");
                out.println("<td>" + rs.getString("içerik") + "</td>");
                out.println("<td><a href=\"" + rs.getString("url") + "\" target=\"_blank\">" + rs.getString("url") + "</a></td>");

                out.println("</tr>");
            }
            out.println("</table>");
             if (sayfa > 1) {
                out.println("<a href=\"HastaTibbiRaporlariGoruntuleServlet?sayfa=" + (sayfa - 1) + "\">Önceki Sayfa</a>");
            }
             if(tamSayfa==sayfaBoyutu){
            out.println("<a href=\"HastaTibbiRaporlariGoruntuleServlet?sayfa=" + (sayfa + 1) + "\">Sonraki Sayfa</a>");
             }
            out.println("<a href=\"Hasta.html\">Geri Dön</a>");
            
            out.println("</body>");
            out.println("</html>");
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
