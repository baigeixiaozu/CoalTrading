package cn.coal.trading.Servlet;

import cn.coal.trading.mapper.CompanyMapper;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
@WebServlet(urlPatterns = "/servlet/pic")//需传入id和文件名字这两个参数，注：名字为数据库表格上的名字
public class ShowPic extends HttpServlet {//用于在浏览器中显示照片
    private CompanyMapper companyMapper;
    public void doGet(HttpServletRequest request,HttpServletResponse response)
            throws IOException
    {
        response.setContentType("image/jpeg");
        Long id = Long.parseLong(request.getParameter("id"));
        System.out.print("yes");
        String name = request.getParameter("name");
       String path=companyMapper.picPath(id,name);
        ServletOutputStream out;
        out = response.getOutputStream();
//        String path= request.getParameter("path");
        FileInputStream fin = new FileInputStream(path);

        BufferedInputStream bin = new BufferedInputStream(fin);
        BufferedOutputStream bout = new BufferedOutputStream(out);
        int ch =0; ;
        while((ch=bin.read())!=-1)
        {
            bout.write(ch);
        }

        bin.close();
        fin.close();
        bout.close();
        out.close();
    }
    public void dpPost(HttpServletRequest request,HttpServletResponse response)throws IOException{
        doGet(request,response);
    }
}