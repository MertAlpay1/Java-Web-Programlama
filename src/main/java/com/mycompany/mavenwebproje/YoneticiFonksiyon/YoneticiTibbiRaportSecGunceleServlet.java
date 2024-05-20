
package com.mycompany.mavenwebproje.YoneticiFonksiyon;


import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;


public class YoneticiTibbiRaportSecGunceleServlet extends HttpServlet {
    
 static String selectedRaporID;
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        String raporID = request.getParameter("Tibbi_id");
        Connection conn = null;
        PreparedStatement pstmt = null;
        
       try {
    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");
    
    
            String sql = "SELECT * FROM tibbirapor WHERE rapor_id = ?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, raporID);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                selectedRaporID = raporID;
                response.sendRedirect("YoneticiRaporGuncelle.html");
                
            } else {
                out.println("<h2>Böyle bir rapor bulunmamaktadır!</h2>");
                out.println("<a href=\"yoneticiPanel.html\">Geri Dön</a>");
            }
       
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            out.close();
        }
    }
public static String getSelectedRaporID() {
        return selectedRaporID;
    }

}
