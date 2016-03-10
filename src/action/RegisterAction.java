package action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.RegisterDao;
import service.RegisterService;

/**
 * Servlet implementation class RegisterAction
 */
@WebServlet("/RegisterAction")
public class RegisterAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private RegisterService service;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterAction() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
//		response.setContentType("text/html,charset=utf-8");
		PrintWriter out = response.getWriter();
	    
		String username = request.getParameter("username");
		String pswd = request.getParameter("password");
		
	    List<Object> params = new ArrayList<Object>();	
	    params.add(username);
	    params.add(pswd);
	    
		boolean flag = service.isUserExist(params);
		
		if(flag){//如果存在用户，则跳转到文件上传下载页面
			   request.getRequestDispatcher("fileupload.jsp").forward(request, response);			
		}else{//如果用户名或者密码错误，则提示
			response.sendRedirect("login.jsp?error=yes");
		}
	}
	
	public void init() throws ServletException{
		service = new RegisterDao();
	}

}
