package cn.coal.trading.Servlet;

import cn.coal.trading.bean.ResponseData;
import cn.coal.trading.mapper.CompanyMapper;
import com.baomidou.shaun.core.annotation.HasRole;
import com.baomidou.shaun.core.context.ProfileHolder;
import com.baomidou.shaun.core.profile.TokenProfile;
import org.springframework.web.bind.annotation.RestController;

import java.io.*;
import java.util.Locale;
import javax.annotation.Resource;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
//@HasRole("USER_REG_AUDITOR")
//@HasRole("SUPER_ADMIN")
@RestController
@WebServlet(urlPatterns = "/servlet/pic")//需传入id和name这两个参数，注：名字为数据库表格上的名字
public class ShowPic extends HttpServlet {//用于在浏览器中显示照片
   @Resource
    private CompanyMapper companyMapper;
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException
    {
        response.setContentType("image/jpeg");
        Long id = Long.parseLong(request.getParameter("id"));
        String file = (String) request.getParameter("file");
       String path=null;
        ResponseData responseData=new ResponseData();
       if (file.equals("legal_id_file"))
           path=companyMapper.legal_id_file(id);
       else if(file.equals("business_licence_file"))
           path=companyMapper.business_license_id(id);
       else if(file.equals("oib_code_file"))
           path=companyMapper.oib_code_file(id);
       else if(file.equals("oib_code_file"))
           path=companyMapper.business_license_id(id);
       else if(file.equals("manage_license_file"))
           path=companyMapper.manage_license_file(id);

       System.out.println(path);
//        String path=null;
        ServletOutputStream out;
        out = response.getOutputStream();
        FileInputStream fin = new FileInputStream(path);
        BufferedInputStream bin = new BufferedInputStream(fin);
        BufferedOutputStream bout = new BufferedOutputStream(out);
        int ch =0;
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