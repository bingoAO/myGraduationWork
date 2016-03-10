package action;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import beans.UploadFile;
import dao.FileUploadDao;

@WebServlet("/FileUploadAction")
public class FileUploadAction extends HttpServlet {

	/**
	 * default version id
	 */
	private static final long serialVersionUID = 1L;

	private List<Object> uploadFiles = new ArrayList<Object>();
	private FileUploadDao uDao = new FileUploadDao();
	private UploadFile entity = new UploadFile();

	public FileUploadAction() {
		super();
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		// 1、创建工厂对象
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 2、设置临时文件路径
		factory.setRepository(new File("E:\\healerRespository"));
		// 3、得到上传文件解析器
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 4、处理乱码问题
		upload.setHeaderEncoding("utf-8");
		// 5、设定文件上传大小限制
		// upload.setSizeMax(1024*1024);
		// 6、进行解析 得到存放FileItem的List对象
		List<FileItem> list = null;
		try {
			list = upload.parseRequest(request);
		} catch (FileUploadException e) {
		}
		if(list == null ){
			return;
		}
		for (FileItem item : list) {
			if (item.isFormField()) {
				String paramName = item.getFieldName();
				String paramValue = item.getString("utf-8");
				System.out.println("普通输入项" + paramName + " = " + paramValue);
				entity.setName(paramValue);
			} else {
				// 获取上传文件名
				String fileName = item.getName();
				// 如果文件名有目录，则去掉目录部分，只保留文件名
				fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
				System.out.println("name = " + fileName);

				if (!fileName.equals("")) {
					// 处理同名问题 名字前加UUID处理 调用refactorFile方法
					String uuidName = refactorFileName(fileName);
					System.out.println("uuidName===" + uuidName);// 测试代码
					entity.setFileName(uuidName);// 将信息存入到数据库中

					InputStream is = item.getInputStream();
					// 上传文件位置生成指定的二级路径 调用方法
//					String basepath = this.getServletContext().getRealPath(
//							"WEB-INF/upload");
					String basepath = "E:\\healerRespository";
					String finalPath = generateFilePath(basepath, uuidName);// 文件的完整路径+名字+后缀
					String finalName = finalPath.substring(finalPath
							.indexOf("_") + 1);// 得到文件的真实名字
					System.out.println("finalName===" + finalName);
					entity.setFilePath(finalPath);// 将信息存入到数据库中
					entity.setFile(item.get());// 将上传文件存入到数据库中

					File file = new File(finalPath);
					FileOutputStream fos = new FileOutputStream(file);

					byte[] buffer = new byte[1024];
					int len = 0;
					while ((len = is.read(buffer)) != -1) {
						fos.write(buffer, 0, len);
					}

					uploadFiles.add(finalPath);
					fos.flush();
					fos.close();
					is.close();
					item.delete();
					request.setAttribute("message", "文件上传成功");

				}
			}
		}
		uDao.insert(uploadFiles);
		request.getRequestDispatcher("/fileupload.jsp").forward(request, response);
	}

	private String generateFilePath(String basepath, String uuidName) {
		int hashcode = uuidName.hashCode();
		int dir1 = hashcode & 0xf;
		int dir2 = (hashcode >> 4) & 0xf;

		String path = basepath + "\\" + dir1 + "\\" + dir2 + "\\";

		File f = new File(path);
		if (!f.exists()) {
			f.mkdirs();
		}
		return path + uuidName;
	}

	private String refactorFileName(String fileName) {
		return UUID.randomUUID().toString() + "_" + fileName;
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doGet(request, response);
	}

}
