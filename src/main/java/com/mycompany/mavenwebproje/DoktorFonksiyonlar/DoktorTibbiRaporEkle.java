/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.mycompany.mavenwebproje.DoktorFonksiyonlar;

import com.mycompany.mavenwebproje.DoktorGirisServlet;
import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class DoktorTibbiRaporEkle extends HttpServlet {
    static String RaporHasta;
    
protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        String hasta_id = request.getParameter("RaporHasta_id");
        String doktorID=DoktorGirisServlet.getLoggedInDocID();
        
        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");
            
            String sql = "SELECT * FROM randevu WHERE hasta_id=? and doktor_id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, hasta_id);
            pstmt.setString(2, doktorID);
            
            rs = pstmt.executeQuery();

           if (rs.next()) {
            RaporHasta = hasta_id;
            response.sendRedirect("DoktorTibbiRaporekle.html");
            } else {
                out.println("Hasta bulunamadı veya hasta sizin hastanız değil<br>");
                out.println("<a href=\"doktorPanel.html\">Geri Dön</a>");

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
    public static String getHasta() {
        return RaporHasta;
    }
}


