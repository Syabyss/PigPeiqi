package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.ResultInfo;
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

@WebServlet("/registUserServlet")
public class RegistUserServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request,response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //验证校验
        String check=request.getParameter("check");
        //从session中获取验证码
        HttpSession session=request.getSession();
        String checkcode_server=(String) session.getAttribute("CHECKCODE_SERVER");
        session.removeAttribute("CHECKCODE_SERVER");    //为了保证验证码只能用一次
        //比较
        if (checkcode_server==null || !checkcode_server.equalsIgnoreCase(check)){
            ResultInfo info=new ResultInfo();
            info.setFlag(false);
            info.setErrorMsg("验证码错误！");
            //将info序列化为json
            ObjectMapper mapper=new ObjectMapper();
            String json=mapper.writeValueAsString(info);
            response.getWriter().write(json);
            return;
        }

        //获取数据
        Map<String,String[]> parameterMap = request.getParameterMap();

        //封装对象
        User user=new User();
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        //调用service完成注册
        UserService service=new UserServiceImpl();
        boolean flag=service.regist(user);
        ResultInfo info=new ResultInfo();

        //响应结果
        if (flag){
            info.setFlag(true);
        }else {
            info.setFlag(false);
            info.setErrorMsg("注册失败！");
        }

        //将info序列化为json
        ObjectMapper mapper=new ObjectMapper();
        String json=mapper.writeValueAsString(info);

        //写回客户端
        response.setContentType("application/json;charset=utf-8");
        response.getWriter().write(json);
    }
}
