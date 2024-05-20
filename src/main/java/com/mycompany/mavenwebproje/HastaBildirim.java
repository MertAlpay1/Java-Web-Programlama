package com.mycompany.mavenwebproje;

import java.io.*;
import java.sql.*;
import jakarta.servlet.*;
import jakarta.servlet.http.*;

public class HastaBildirim extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;

        try {
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/proje", "root", "");

            String loggedInUserID = HastaGirisServlet.getLoggedInUserID();

            String sqlSelect = "SELECT * FROM bildirim WHERE hasta_id = ?";
            pstmt = conn.prepareStatement(sqlSelect);
            pstmt.setString(1, loggedInUserID);

            rs = pstmt.executeQuery();

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Hasta Bildirimler</title>");
            out.println("<meta charset=\"UTF-8\">");
            out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1> Bildirimler</h1>");

            while (rs.next()) {
                out.println("<p>" +"--> "+ rs.getString("mesaj") + "</p>");
            }

            String sqlDelete = "DELETE FROM bildirim WHERE hasta_id = ?";
            pstmt = conn.prepareStatement(sqlDelete);
            pstmt.setString(1, loggedInUserID);
            pstmt.executeUpdate();
            
            out.println("<form action=\"Hasta.html\" method=\"post\">");
            out.println("<input type=\"submit\" value=\"Tamam\">");
            out.println("</form>");

            out.println("</body>");
            out.println("</html>");
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
                out.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
