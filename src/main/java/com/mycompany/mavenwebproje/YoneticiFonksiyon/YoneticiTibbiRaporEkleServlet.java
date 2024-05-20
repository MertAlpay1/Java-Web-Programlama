
package com.mycompany.mavenwebproje.YoneticiFonksiyon;

import com.google.cloud.storage.*;
import com.google.gson.Gson;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import com.mycompany.mavenwebproje.TibbiRapor;
import jakarta.servlet.annotation.MultipartConfig;

@MultipartConfig
public class YoneticiTibbiRaporEkleServlet extends HttpServlet {

   protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        String hastaID=request.getParameter("HastaId");
        String raporTarihi = request.getParameter("raporTarihi");
        String raporIcerigi = request.getParameter("raporIcerigi");
        Part filePart = request.getPart("Foto"); 

        String fileName = filePart.getSubmittedFileName();

        Storage storage = StorageOptions.getDefaultInstance().getService();
        BlobInfo blobInfo = storage.create(BlobInfo.newBuilder("proje23", fileName).build(), filePart.getInputStream());

        String raporDosyaURL = "https://storage.cloud.google.com/proje23/"+fileName;
        
        TibbiRapor tibbiRapor = new TibbiRapor(raporTarihi, raporIcerigi, raporDosyaURL, hastaID);
        Gson gson = new Gson();
        String jsonRapor = gson.toJson(tibbiRapor);

        
        Connection conn = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");
            String sql = "INSERT INTO tibbirapor (tarih, içerik, url,hasta_id,json) VALUES (?, ?, ?, ? ,?)";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, raporTarihi);
            pstmt.setString(2, raporIcerigi);
            pstmt.setString(3, raporDosyaURL);
            pstmt.setString(4, hastaID);
            pstmt.setString(5, jsonRapor);
            pstmt.executeUpdate();
            
            String sqlbildirim="INSERT INTO bildirim (hasta_id,mesaj)VALUES(?,?)";
             pstmt = conn.prepareStatement(sqlbildirim);
            pstmt.setString(1, hastaID);
            pstmt.setString(2, "Yeni bir tıbbi rapor eklenmiştir");
             
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Tıbbi Rapor Ekleme</title>");
        out.println("<meta charset=\"UTF-8\">");
        out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
        out.println("</head>");
        out.println("<body>");
        out.println("<h2>Tıbbi Rapor Ekleme</h2>");
        out.println("<p>Rapor başarıyla kaydedildi.</p>");
        out.println("<a href=\"yoneticiPanel.html\">Geri Dön</a>");
        out.println("</body>");
        out.println("</html>");
            
        } catch (SQLException e) {
            e.printStackTrace();
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
