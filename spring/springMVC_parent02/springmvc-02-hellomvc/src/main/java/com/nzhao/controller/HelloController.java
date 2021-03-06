package com.nzhao.controller;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//注意，这里是我们理解原理的方式，我们先导入Controller接口
public class HelloController implements Controller {
    @Override
    public ModelAndView handleRequest(HttpServletRequest request, HttpServletResponse response) throws Exception {
        //ModelAndView 模型与视图
        ModelAndView mv = new ModelAndView();

        //封装对象，放在ModelAndView
        mv.addObject("msg","HelloSpringMVC ! ");
        //封装要跳转的视图，放在ModelAndView

        mv.setViewName("hello");  //:  /WEB-INF/jsp/hello.jsp
        return mv;
    }
}
