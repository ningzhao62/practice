package com.nzhao.servlet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 测试ServletContext共享Servlet数据，需要两个Servlet，
 * 一个是Servlet1，一个是Servlet2
 * @author nzhao
 */
public class Servlet2 extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 解决乱码问题
        resp.setContentType("text/html;charset=UTF-8");
        resp.setCharacterEncoding("UTF-8");

        // 从ServletContext获得数据
        ServletContext servletContext = req.getServletContext();
        String name = (String) servletContext.getAttribute("name");

        // 处理name乱码问题
        //name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
        System.out.println("Servlet2 name = " + name);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        this.doGet(req, resp);
    }
}
