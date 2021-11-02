package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class BaseServlet extends HttpServlet {

    private static final long serialVersionUID = 5522372203700422672L;


    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //完成方法的分发

        //1.获取请求路径
        String uri = request.getRequestURI();
        //2.获取方法名称
        String methodName = uri.substring(uri.lastIndexOf('/') + 1);

        //3.获取方法对象Method

        try {
            //因为Servlet的方法被protected修饰，所以我们要暴力反射，忽略权限修饰符
            Method method = this.getClass().getDeclaredMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            //4.执行方法
            try {
                //暴力反射
                method.setAccessible(true);
                method.invoke(this, request, response);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    //序列化json并且写回客户端
    public  void writeValue(Object obj,HttpServletResponse response) throws IOException {
        //序列化json返回
        ObjectMapper mapper=new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
        mapper.writeValue(response.getOutputStream(),obj);
    }

    //将传入的对象序列化为json，返回
    public  String writeValueAsString(Object obj) throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();

        return mapper.writeValueAsString(obj);
    }


    //自动登录
    public void writeCookie(Cookie cookie, int maxAge, String path, HttpServletResponse response) {
        cookie.setMaxAge(maxAge);
        cookie.setPath(path);
        response.addCookie(cookie);
    }
}

