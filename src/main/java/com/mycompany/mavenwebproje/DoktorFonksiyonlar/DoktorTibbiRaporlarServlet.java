package com.mycompany.mavenwebproje.DoktorFonksiyonlar;

import com.mycompany.mavenwebproje.DoktorGirisServlet;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class DoktorTibbiRaporlarServlet extends HttpServlet {

 protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int tamSayfa=0;
        int sayfa = Integer.parseInt(request.getParameter("sayfa"));
        int sayfaBoyutu = 20;
        
        String doktorID=DoktorGirisServlet.getLoggedInDocID();
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");
            
            String sqlSelect = "SELECT * FROM tibbirapor t,randevu r WHERE t.hasta_id=r.hasta_id AND r.doktor_id=?"+
                    "LIMIT ? OFFSET ?";
            pstmt = conn.prepareStatement(sqlSelect);
            pstmt.setString(1, doktorID);
            pstmt.setInt(2, sayfaBoyutu); 
            pstmt.setInt(3, (sayfa - 1) * sayfaBoyutu);
            rs = pstmt.executeQuery();
            
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Tıbbi Raporlar</title>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<h2>Tıbbi Raporlar</h2>");
            out.println("<table border=\"1\">");
            out.println("<tr>");
            out.println("<th>Tıbbi Rapor ID</th>");
            out.println("<th>Rapor Tarihi</th>");
            out.println("<th>Rapor içeriği </th>");
            out.println("<th>Rapor Sonucu </th>");
            out.println("<th>Hasta Id</th>");
            while (rs.next()) {
                tamSayfa++;
                out.println("<tr>");
                out.println("<td>" + rs.getString("rapor_id") + "</td>");
                out.println("<td>" + rs.getString("tarih") + "</td>");
                out.println("<td>" + rs.getString("içerik") + "</td>");
                out.println("<td><a href=\"" + rs.getString("url") + "\" target=\"_blank\">" + rs.getString("url") + "</a></td>");
                out.println("<td>" + rs.getString("hasta_id") + "</td>");
                out.println("</tr>");
            }
            out.println("</table>");
             if (sayfa > 1) {
                out.println("<a href=\"DoktorTibbiRaporlarServlet?sayfa=" + (sayfa - 1) + "\">Önceki Sayfa</a>");
            }
             if(tamSayfa==sayfaBoyutu){
            out.println("<a href=\"DoktorTibbiRaporlarServlet?sayfa=" + (sayfa + 1) + "\">Sonraki Sayfa</a>");
             }
            out.println("<a href=\"doktorPanel.html\">Geri Dön</a>");
            
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
   


