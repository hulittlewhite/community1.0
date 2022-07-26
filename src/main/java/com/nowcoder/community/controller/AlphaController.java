package com.nowcoder.community.controller;

import com.nowcoder.community.service.AlphaService;
import com.nowcoder.community.util.CommunityUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

@Controller
@RequestMapping("/alpha")
public class AlphaController {

    @Autowired
    private AlphaService alphaService;

    @RequestMapping("/hello")
    @ResponseBody
    public String sayHello() {
        return "Hello Spring Boot.";
    }

    @RequestMapping("/data")
    @ResponseBody
    public String getData() {
        return alphaService.find();
    }

    @RequestMapping("/http")
    public void http(HttpServletRequest request, HttpServletResponse response) {
        // 获取请求数据
        System.out.println(request.getMethod());
        System.out.println(request.getServletPath());
        Enumeration<String> enumeration = request.getHeaderNames();
        while (enumeration.hasMoreElements()) {
            String name = enumeration.nextElement();
            String value = request.getHeader(name);
            System.out.println(name + ": " + value);
        }

        System.out.println(request.getParameter("code"));

        // 返回响应数据
        response.setContentType("text/html;charset=utf-8");
        try {
            PrintWriter writer = response.getWriter();
            writer.write("<h1>nowcoder</h1>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // /students?current=1&limit=20
    @GetMapping("/students")
    @ResponseBody
    public String getStudents(
            @RequestParam(name = "current" ,required = false, defaultValue = "1") int current,
            @RequestParam(name = "limit" ,required = false, defaultValue = "10") int limit) {
        System.out.println(current);
        System.out.println(limit);
        return "some students";
    }

    // /student/123
    @GetMapping("/student/{id}")
    @ResponseBody
    public String getStudent(@PathVariable("id") int id) {
        System.out.println(id);
        return "one student";
    }

    // POST请求
    @PostMapping("/student")
    @ResponseBody
    public String saveStudent(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return "success";
    }

    // 响应HTML数据
    @GetMapping("/teacher")
    public ModelAndView getTeacher() {
        ModelAndView mav = new ModelAndView();
        mav.addObject("name", "黄碧如");
        mav.addObject("age", 20);
        mav.setViewName("/demo/view");
        return mav;
    }

    @GetMapping("/school")
    public String getSchool(Model model) {
        model.addAttribute("name", "xx大学");
        model.addAttribute("age", 100);
        return "/demo/view";
    }

    @GetMapping("/emp")
    @ResponseBody
    public Map<String, Object> getEmp() {
        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "黄碧如");
        emp.put("age", 20);
        emp.put("salary", 12000.00);
        return emp;
    }

    @GetMapping("/emps")
    @ResponseBody
    public List<Map<String, Object>> getEmps() {
        List<Map<String, Object>> list = new ArrayList<>();

        Map<String, Object> emp = new HashMap<>();
        emp.put("name", "黄碧如");
        emp.put("age", 20);
        emp.put("salary", 12000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "周莹");
        emp.put("age", 18);
        emp.put("salary", 15000.00);
        list.add(emp);

        emp = new HashMap<>();
        emp.put("name", "王晶晶");
        emp.put("age", 25);
        emp.put("salary", 8000.00);
        list.add(emp);

        return list;
    }

    // cookie 示例
    @GetMapping("/cookie/set")
    @ResponseBody
    public String setCookie(HttpServletResponse response) {

        // 创建cookie
        Cookie cookie = new Cookie("code", CommunityUtil.generateUUID());
        // 设置cookie生效的范围
        cookie.setPath("community/alpha");
        // 设置cookie的生存时间
        cookie.setMaxAge(10 * 60);
        // 发送cookie
        response.addCookie(cookie);

        return "set cookie";

    }

    @GetMapping("/cookie/get")
    @ResponseBody
    public String getCookie(@CookieValue("code") String code) {
        System.out.println(code);
        return "get cookie";
    }

    // session 示例
    @GetMapping("/session/set")
    @ResponseBody
    public String setSession(HttpSession session) {
        session.setAttribute("id", 1);
        session.setAttribute("name", "Test");
        return "set session";
    }

    @GetMapping("/session/get")
    @ResponseBody
    public String getSession(HttpSession session) {
        System.out.println(session.getAttribute("id"));
        System.out.println(session.getAttribute("name"));
        return "get session";
    }

    // ajax示例
    @PostMapping("/ajax")
    @ResponseBody
    public String testAjax(String name, int age) {
        System.out.println(name);
        System.out.println(age);
        return CommunityUtil.getJSONString(0, "操作成功!");
    }
}
