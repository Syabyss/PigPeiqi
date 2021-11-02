package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    //声明userservice
    private UserService userService = new UserServiceImpl();

    //登录服务
    public void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Map<String, String[]> map = request.getParameterMap();
        User user = new User();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        User u = userService.login(user);
        ResultInfo info = new ResultInfo();
        //判断用户是否为null
        if (u == null) {
            //用户名密码错误
            info.setFlag(false);
            info.setErrorMsg("用户名密码不匹配！");
        }

        //判断用户是否激活
        if (u != null && !"Y".equals(u.getStatus())) {
            //用户尚未激活
            info.setFlag(false);
            info.setErrorMsg("您的账号尚未激活，请登录邮箱进行激活！");
        }

        //判断登录成功
        if (u != null && "Y".equals(u.getStatus())) {
            //登录成功
            info.setFlag(true);
            request.getSession().setAttribute("user", u);
        }

        //响应数据
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), info);
    }

    //注册服务
    public void regist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证校验
        String check = request.getParameter("check");
        //从session中获取验证码
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");    //为了保证验证码只能用一次
        //比较
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)) {
            ResultInfo info = new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            //将info序列化为json
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(info);
            response.setContentType("application/json;charset=utf-8");
            response.getWriter().write(json);
            return;
        }

        //获取数据
        Map<String, String[]> parameterMap = request.getParameterMap();

        //封装对象
        User user = new User();
        try {
            BeanUtils.populate(user, parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用service完成注册

        boolean flag = userService.regist(user);
        ResultInfo info = new ResultInfo();

        //响应结果
        if (flag) {
            info.setFlag(true);
        } else {
            info.setFlag(false);
            info.setErrorMsg("注册失败！");
        }

        //将info序列化为json
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writeValueAsString(info);

        //写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }

    //查找服务
    protected void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object user = request.getSession().getAttribute("user");
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(), user);
    }

    //退出服务
    protected void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //销毁session
        request.getSession().invalidate();

        //跳转登录页面
        response.sendRedirect(request.getContextPath() + "/login.html");
    }

    //激活服务
    protected void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("code");
        if(code!=null){

            boolean flag=userService.active(code);
            String msg=null;
            if (flag){
                msg="<meta charset=\"utf-8\"><table width=\"100%\"><tr><td style=\"width: 100%;\"><center><table class=\"content-wrap\" style=\"margin: 0px auto; width: 600px;\"><tr><td style=\"margin: 0px auto; overflow: hidden; padding: 0px; border: 0px dotted rgb(238, 238, 238);\"><!----><div tindex=\"1\" style=\"margin: 0px auto; max-width: 600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-color: rgb(255, 255, 255); background-image: url(&quot;&quot;); background-repeat: no-repeat; background-size: 100px; background-position: 1% 50%;\"><tbody><tr><td style=\"direction: ltr; font-size: 0px; text-align: center; vertical-align: top; width: 600px;\"><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"vertical-align: top;\"><tbody><tr><td class=\"twoColumn column1\" style=\"width: 50%; max-width: 50%; min-height: 1px; font-size: 13px; text-align: left; direction: ltr; vertical-align: top; padding: 0px;\"><div columnnumber=\"2\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"width: 100%;\"><tbody><tr><td style=\"direction: ltr; font-size: 0px; text-align: center; vertical-align: top; border: 0px;\"><div class=\"mj-column-per-50\" style=\"width: 100%; max-width: 100%; font-size: 13px; text-align: left; direction: ltr; display: inline-block; vertical-align: top;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse; border-spacing: 0px; width: 100%; vertical-align: top;\"><tr><td align=\"center\" border=\"0\" style=\"font-size: 0px; word-break: break-word;\"><div class=\"full\" style=\"margin: 0px auto; max-width: 600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"fullTable\" style=\"width: 300px;\"><tbody><tr><td class=\"fullTd\" style=\"direction: ltr; width: 300px; font-size: 0px; padding-bottom: 0px; text-align: center; vertical-align: top;\"><div style=\"display: inline-block; vertical-align: top; width: 100%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"vertical-align: top;\"><tr><td style=\"font-size: 0px; word-break: break-word; width: 280px; text-align: left; padding: 12px 10px;\"><div><img height=\"auto\" alt=\"拖拽生成HTML邮件-拉易网-16\" width=\"auto\" src=\"https://www.drageasy.com/831e0bce023b59608a98b213dca3242a.jpg?imageslim\" style=\"box-sizing: border-box; border: 0px; display: inline-block; outline: none; text-decoration: none; height: auto; width: auto; max-width: 100%; padding: 0px;\"></div></td></tr></table></div></td></tr></tbody></table></div></td></tr></table></div><div class=\"mj-column-per-50\" style=\"width: 100%; max-width: 100%; font-size: 13px; text-align: left; direction: ltr; display: inline-block; vertical-align: top;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"border-collapse: collapse; border-spacing: 0px; width: 100%; vertical-align: top;\"><tr><td align=\"center\" border=\"0\" style=\"font-size: 0px; word-break: break-word;\"><div class=\"full\" style=\"margin: 0px auto; max-width: 600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"fullTable\" style=\"width: 300px;\"><tbody><tr><td class=\"fullTd\" style=\"direction: ltr; width: 300px; font-size: 0px; padding-bottom: 0px; text-align: center; vertical-align: top; background-image: url(&quot;&quot;); background-repeat: no-repeat; background-size: 0px; background-position: 0% 0%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"vertical-align: top;\"><tr><td align=\"left\" style=\"font-size: 0px; padding: 13px 10px;\"><div class=\"text\" style=\"font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; overflow-wrap: break-word; margin: 0px; text-align: left; line-height: 20px; color: rgb(46, 48, 51); font-size: 13px; font-weight: normal;\"><div><h1 style=\"line-height: 48px; font-size: 2em; font-weight: bold; margin: 0px;\"><span style=\"color: #f1c40f;\">放心</span>的服务</h1>\n" +
                        "<h1 style=\"line-height: 48px; font-size: 2em; font-weight: bold; margin: 0px;\">&nbsp; &nbsp; &nbsp; <span style=\"color: #f1c40f;\">放心</span>的价格</h1></div></div></td></tr></table></td></tr></tbody></table></div></td></tr></table></div></td></tr></tbody></table></div></td><td class=\"twoColumn column2\" style=\"width: 50%; max-width: 50%; min-height: 1px; font-size: 13px; text-align: left; direction: ltr; vertical-align: top; padding: 0px;\"><div class=\"full\" style=\"margin: 0px auto; max-width: 600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"fullTable\" style=\"width: 300px;\"><tbody><tr><td class=\"fullTd\" style=\"direction: ltr; width: 300px; font-size: 0px; padding-bottom: 0px; text-align: center; vertical-align: top;\"><div style=\"display: inline-block; vertical-align: top; width: 100%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"vertical-align: top;\"><tr><td style=\"font-size: 0px; word-break: break-word; width: 300px; text-align: center; padding: 0px;\"><div><img height=\"auto\" alt=\"拖拽生成HTML邮件-拉易网-11\" width=\"201\" src=\"https://www.drageasy.com/5c3572e8d3ead944a18b3bc946e88207.jpg?imageslim\" style=\"box-sizing: border-box; border: 0px; display: inline-block; outline: none; text-decoration: none; height: auto; max-width: 100%; padding: 0px;\"></div></td></tr></table></div></td></tr></tbody></table></div></td></tr></tbody></table></td></tr></tbody></table></div><div class=\"full\" tindex=\"2\" style=\"margin: 0px auto; max-width: 600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"fullTable\" style=\"width: 600px;\"><tbody><tr><td align=\"center\" vertical-align=\"middle\" style=\"font-size: 0px; word-break: break-word; width: 600px; padding: 40px 30px; background-image: url(&quot;&quot;); background-size: 100px; background-position: 1% 50%; background-repeat: no-repeat;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: separate; line-height: 1;\"><tr><td align=\"center\" valign=\"middle\" style=\"line-height: 1; background-color: rgb(218, 119, 230); padding: 10px 30px;\"><a target=\"_blank\" href=\"http://localhost/travel/login.html\" style=\"font-size: 0px; text-decoration: none;\"><p style=\"font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; margin: 0px; color: rgb(255, 255, 255); line-height: 1; font-size: 16px; font-weight: normal; text-decoration: none; text-transform: none;\"><span>点击完成注册</span></p></a></td></tr></table></td></tr></tbody></table></div><div class=\"full\" tindex=\"3\" style=\"margin: 0px auto; line-height: 0px; max-width: 600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"fullTable\" style=\"width: 600px;\"><tbody><tr><td align=\"center\" class=\"fullTd\" style=\"direction: ltr; font-size: 0px; padding: 32px 0px; text-align: center; vertical-align: top; word-break: break-word; width: 600px; background-image: url(&quot;&quot;); background-repeat: no-repeat; background-size: 100px; background-position: 10% 50%;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse; border-spacing: 0px;\"><tbody><tr><td style=\"width: 600px; border-top: 1px solid rgb(204, 204, 204);\"></td></tr></tbody></table></td></tr></tbody></table></div><div class=\"full\" tindex=\"4\" style=\"margin: 0px auto; line-height: 0px; max-width: 600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"fullTable\" style=\"width: 600px;\"><tbody><tr><td align=\"center\" class=\"fullTd\" style=\"direction: ltr; font-size: 0px; padding: 10px 0px; text-align: center; vertical-align: top; word-break: break-word; width: 600px; background-image: url(&quot;&quot;); background-repeat: no-repeat; background-size: 100px; background-position: 1% 50%;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"border-collapse: collapse; border-spacing: 0px;\"><tbody><tr><td style=\"width: 600px; border-top: 0px transparent; padding-bottom: 1px;\"></td></tr></tbody></table></td></tr></tbody></table></div><div tindex=\"5\" style=\"margin: 0px auto; max-width: 600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-image: url(&quot;&quot;); background-repeat: no-repeat; background-size: 100px; background-position: 1% 50%;\"><tbody><tr><td style=\"direction: ltr; font-size: 0px; text-align: center; vertical-align: top; width: 600px;\"><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"vertical-align: top;\"><tbody><tr><td class=\"fourColumn column1\" style=\"width: 25%; max-width: 25%; min-height: 1px; font-size: 13px; text-align: left; direction: ltr; vertical-align: top; padding: 0px;\"><div class=\"full\" style=\"margin: 0px auto; max-width: 600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"fullTable\" style=\"width: 150px;\"><tbody><tr><td class=\"fullTd\" style=\"direction: ltr; width: 150px; font-size: 0px; padding-bottom: 0px; text-align: center; vertical-align: top;\"><div style=\"display: inline-block; vertical-align: top; width: 100%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"vertical-align: top;\"><tr><td style=\"font-size: 0px; word-break: break-word; width: 150px; text-align: center; padding: 0px;\"><div><img height=\"auto\" alt=\"\" width=\"61\" src=\"https://www.drageasy.com/Uploads/2019/0302/b04f435ec488f180b7d9330819687470.png\" style=\"box-sizing: border-box; border: 0px; display: inline-block; outline: none; text-decoration: none; height: auto; max-width: 100%; padding: 0px;\"></div></td></tr></table></div></td></tr></tbody></table></div></td><td class=\"fourColumn column2\" style=\"width: 25%; max-width: 25%; min-height: 1px; font-size: 13px; text-align: left; direction: ltr; vertical-align: top; padding: 0px;\"><div class=\"full\" style=\"margin: 0px auto; max-width: 600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"fullTable\" style=\"width: 150px;\"><tbody><tr><td class=\"fullTd\" style=\"direction: ltr; width: 150px; font-size: 0px; padding-bottom: 0px; text-align: center; vertical-align: top;\"><div style=\"display: inline-block; vertical-align: top; width: 100%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"vertical-align: top;\"><tr><td style=\"font-size: 0px; word-break: break-word; width: 150px; text-align: center; padding: 0px;\"><div><img height=\"auto\" alt=\"拖拽生成HTML邮件-拉易网-7\" width=\"61\" src=\"https://www.drageasy.com/Uploads/2019/0302/dd5b3cab07b3f7960a4d22d079e3bf61.png\" style=\"box-sizing: border-box; border: 0px; display: inline-block; outline: none; text-decoration: none; height: auto; max-width: 100%; padding: 0px;\"></div></td></tr></table></div></td></tr></tbody></table></div></td><td class=\"fourColumn column3\" style=\"width: 25%; max-width: 25%; min-height: 1px; font-size: 13px; text-align: left; direction: ltr; vertical-align: top; padding: 0px;\"><div class=\"full\" style=\"margin: 0px auto; max-width: 600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"fullTable\" style=\"width: 150px;\"><tbody><tr><td class=\"fullTd\" style=\"direction: ltr; width: 150px; font-size: 0px; padding-bottom: 0px; text-align: center; vertical-align: top;\"><div style=\"display: inline-block; vertical-align: top; width: 100%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"vertical-align: top;\"><tr><td style=\"font-size: 0px; word-break: break-word; width: 150px; text-align: center; padding: 0px;\"><div><img height=\"auto\" alt=\"拖拽生成HTML邮件-拉易网-12\" width=\"61\" src=\"https://www.drageasy.com/Uploads/2019/0302/c797a715fc5fa4151fbfe109be1f8a81.png\" style=\"box-sizing: border-box; border: 0px; display: inline-block; outline: none; text-decoration: none; height: auto; max-width: 100%; padding: 0px;\"></div></td></tr></table></div></td></tr></tbody></table></div></td><td class=\"fourColumn column4\" style=\"width: 25%; max-width: 25%; min-height: 1px; font-size: 13px; text-align: left; direction: ltr; vertical-align: top; padding: 0px;\"><div class=\"full\" style=\"margin: 0px auto; max-width: 600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"fullTable\" style=\"width: 150px;\"><tbody><tr><td class=\"fullTd\" style=\"direction: ltr; width: 150px; font-size: 0px; padding-bottom: 0px; text-align: center; vertical-align: top;\"><div style=\"display: inline-block; vertical-align: top; width: 100%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"vertical-align: top;\"><tr><td style=\"font-size: 0px; word-break: break-word; width: 150px; text-align: center; padding: 0px;\"><div><img height=\"auto\" alt=\"拖拽生成HTML邮件-拉易网-1\" width=\"61\" src=\"https://www.drageasy.com/Uploads/2019/0302/2886ed3d925b5b77dc797e2258877685.png\" style=\"box-sizing: border-box; border: 0px; display: inline-block; outline: none; text-decoration: none; height: auto; max-width: 100%; padding: 0px;\"></div></td></tr></table></div></td></tr></tbody></table></div></td></tr></tbody></table></td></tr></tbody></table></div><div tindex=\"6\" style=\"margin: 0px auto; max-width: 600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"background-image: url(&quot;&quot;); background-repeat: no-repeat; background-size: 100px; background-position: 1% 50%;\"><tbody><tr><td style=\"direction: ltr; font-size: 0px; text-align: center; vertical-align: top; width: 600px;\"><table width=\"100%\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" style=\"vertical-align: top;\"><tbody><tr><td class=\"fourColumn column1\" style=\"width: 25%; max-width: 25%; min-height: 1px; font-size: 13px; text-align: left; direction: ltr; vertical-align: top; padding: 0px;\"><div class=\"full\" style=\"margin: 0px auto; max-width: 600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"fullTable\" style=\"width: 150px;\"><tbody><tr><td class=\"fullTd\" style=\"direction: ltr; width: 150px; font-size: 0px; padding-bottom: 0px; text-align: center; vertical-align: top; background-image: url(&quot;&quot;); background-repeat: no-repeat; background-size: 0px; background-position: 0% 0%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"vertical-align: top;\"><tr><td align=\"left\" style=\"font-size: 0px; padding: 10px;\"><div class=\"text\" style=\"font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; overflow-wrap: break-word; margin: 0px; text-align: center; line-height: 20px; color: rgb(102, 102, 102); font-size: 12px; font-weight: normal;\"><div><p style=\"text-size-adjust: none; word-break: break-word; line-height: 20px; font-size: 12px; margin: 0px;\"><span style=\"color: rgb(51, 51, 51);\">专业的工作</span></p><p style=\"text-size-adjust: none; word-break: break-word; line-height: 20px; font-size: 12px; margin: 0px;\"><span style=\"color: rgb(51, 51, 51);\">沟通工具</span></p></div></div></td></tr></table></td></tr></tbody></table></div></td><td class=\"fourColumn column2\" style=\"width: 25%; max-width: 25%; min-height: 1px; font-size: 13px; text-align: left; direction: ltr; vertical-align: top; padding: 0px;\"><div class=\"full\" style=\"margin: 0px auto; max-width: 600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"fullTable\" style=\"width: 150px;\"><tbody><tr><td class=\"fullTd\" style=\"direction: ltr; width: 150px; font-size: 0px; padding-bottom: 0px; text-align: center; vertical-align: top; background-image: url(&quot;&quot;); background-repeat: no-repeat; background-size: 0px; background-position: 0% 0%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"vertical-align: top;\"><tr><td align=\"left\" style=\"font-size: 0px; padding: 10px;\"><div class=\"text\" style=\"font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; overflow-wrap: break-word; margin: 0px; text-align: center; line-height: 20px; color: rgb(102, 102, 102); font-size: 12px; font-weight: normal;\"><div><p style=\"text-size-adjust: none; word-break: break-word; line-height: 20px; font-size: 12px; margin: 0px;\"><span style=\"color: rgb(51, 51, 51);\">连接微信</span></p><p style=\"text-size-adjust: none; word-break: break-word; line-height: 20px; font-size: 12px; margin: 0px;\"><span style=\"color: rgb(51, 51, 51);\">消息互通</span></p></div></div></td></tr></table></td></tr></tbody></table></div></td><td class=\"fourColumn column3\" style=\"width: 25%; max-width: 25%; min-height: 1px; font-size: 13px; text-align: left; direction: ltr; vertical-align: top; padding: 0px;\"><div class=\"full\" style=\"margin: 0px auto; max-width: 600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"fullTable\" style=\"width: 150px;\"><tbody><tr><td class=\"fullTd\" style=\"direction: ltr; width: 150px; font-size: 0px; padding-bottom: 0px; text-align: center; vertical-align: top; background-image: url(&quot;&quot;); background-repeat: no-repeat; background-size: 0px; background-position: 0% 0%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"vertical-align: top;\"><tr><td align=\"left\" style=\"font-size: 0px; padding: 10px;\"><div class=\"text\" style=\"font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; overflow-wrap: break-word; margin: 0px; text-align: center; line-height: 20px; color: rgb(102, 102, 102); font-size: 12px; font-weight: normal;\"><div><p style=\"text-size-adjust: none; word-break: break-word; line-height: 20px; font-size: 12px; margin: 0px;\"><span style=\"color: rgb(51, 51, 51);\">轻松打卡</span></p><p style=\"text-size-adjust: none; word-break: break-word; line-height: 20px; font-size: 12px; margin: 0px;\"><span style=\"color: rgb(51, 51, 51);\">智能核算</span></p></div></div></td></tr></table></td></tr></tbody></table></div></td><td class=\"fourColumn column4\" style=\"width: 25%; max-width: 25%; min-height: 1px; font-size: 13px; text-align: left; direction: ltr; vertical-align: top; padding: 0px;\"><div class=\"full\" style=\"margin: 0px auto; max-width: 600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"fullTable\" style=\"width: 150px;\"><tbody><tr><td class=\"fullTd\" style=\"direction: ltr; width: 150px; font-size: 0px; padding-bottom: 0px; text-align: center; vertical-align: top; background-image: url(&quot;&quot;); background-repeat: no-repeat; background-size: 0px; background-position: 0% 0%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"vertical-align: top;\"><tr><td align=\"left\" style=\"font-size: 0px; padding: 10px;\"><div class=\"text\" style=\"font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; overflow-wrap: break-word; margin: 0px; text-align: center; line-height: 20px; color: rgb(102, 102, 102); font-size: 12px; font-weight: normal;\"><div><p style=\"text-size-adjust: none; word-break: break-word; line-height: 20px; font-size: 12px; margin: 0px;\"><span style=\"color: rgb(51, 51, 51);\">丰富免费的</span></p><p style=\"text-size-adjust: none; word-break: break-word; line-height: 20px; font-size: 12px; margin: 0px;\"><span style=\"color: rgb(51, 51, 51);\">办公应用</span></p></div></div></td></tr></table></td></tr></tbody></table></div></td></tr></tbody></table></td></tr></tbody></table></div><div class=\"full\" tindex=\"7\" style=\"margin: 0px auto; max-width: 600px;\"><table align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" class=\"fullTable\" style=\"width: 600px;\"><tbody><tr><td class=\"fullTd\" style=\"direction: ltr; width: 600px; font-size: 0px; padding-bottom: 0px; text-align: center; vertical-align: top; background-image: url(&quot;&quot;); background-repeat: no-repeat; background-size: 100px; background-position: 1% 50%;\"><table border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"vertical-align: top;\"><tr><td align=\"left\" style=\"font-size: 0px; padding: 10px;\"><div class=\"text\" style=\"font-family: 微软雅黑, &quot;Microsoft YaHei&quot;; overflow-wrap: break-word; margin: 0px; text-align: center; line-height: 20px; color: rgb(102, 102, 102); font-size: 12px; font-weight: normal;\"><div><p style=\"text-size-adjust: none; word-break: break-word; line-height: 20px; font-size: 12px; margin: 0px;\"><a href=\"http://www.drageasy.com\" target=\"_blank\" style=\"color: rgb(136, 136, 136); text-decoration: underline; font-weight: normal;\">了解更多&nbsp;&gt;&gt;</a></p></div></div></td></tr></table></td></tr></tbody></table></div></td></tr></table></center></td></tr></table><!----><center style=\"text-align:center;font-size: 12px;margin:5px;color:rgb(102, 102, 102);transform: scale(.9);-webkit-transform: scale(.9);\"></center>";
            }else {
                msg="激活失败，请尝试重新注册！";
            }
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(msg);
        }
    }

    public void findMyFavorite(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 获取session，取得用户对象
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {     // 用户未登录
            writeValue(null, response);
            return;
        }
        // 获取并处理浏览器请求属性
        String currentPageStr = request.getParameter("currentPage");        // 当前页数
        int currentPage;
        if(currentPageStr=="" ||currentPageStr.length()>0){
            currentPage=0;
        }else {
            currentPage= Integer.parseInt(currentPageStr);
        }
        int pageSize=5;

        // 2.获取service，根据uid查rid，再根据rid查详情数据
        PageBean<Route> routePageBean = userService.favorPageQuery(user.getUid(), currentPage, pageSize);
        // 3.将数据回写至浏览器
        writeValue(routePageBean, response);
    }






}