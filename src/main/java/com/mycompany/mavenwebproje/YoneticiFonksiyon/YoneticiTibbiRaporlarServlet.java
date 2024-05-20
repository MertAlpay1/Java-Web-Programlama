
package com.mycompany.mavenwebproje.YoneticiFonksiyon;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class YoneticiTibbiRaporlarServlet extends HttpServlet {

   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        int tamSayfa=0;
        int sayfa = Integer.parseInt(request.getParameter("sayfa"));
        int randevuSayfaBoyutu = 20; 

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");

            int baslangicIndeksi = (sayfa - 1) * randevuSayfaBoyutu;

            String sql = "SELECT * FROM tibbirapor LIMIT ?, ?";
            
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, baslangicIndeksi);
            pstmt.setInt(2, randevuSayfaBoyutu);
            
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
            out.println("<th>Tıbbi Rapor ID</th>");
            out.println("<th>Rapor Tarihi</th>");
            out.println("<th>Rapor içeriği </th>");
            out.println("<th>Rapor Sonucu </th>");
            out.println("<th>Hasta Id</th>");
            out.println("</tr>");

            while (rs.next()) {
                tamSayfa++;
                out.println("<tr>");
                out.println("<td>" + rs.getString("rapor_id") + "</td>");
                out.println("<td>" + rs.getString("tarih") + "</td>");
                out.println("<td>" + rs.getString("içerik") + "</td>");
                out.println("<td>" + rs.getString("url") + "</td>");
                out.println("<td>" + rs.getString("hasta_id") + "</td>");
                out.println("</tr>");
            }

            out.println("</table>");

            if (sayfa > 1) {
                out.println("<a href=\"YoneticiTibbiRaporlarServlet?sayfa=" + (sayfa - 1) + "\">Önceki Sayfa</a>");
            }
            if(tamSayfa==randevuSayfaBoyutu){
            out.println("<a href=\"YoneticiTibbiRaporlarServlet?sayfa=" + (sayfa + 1) + "\">Sonraki Sayfa</a>");
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
