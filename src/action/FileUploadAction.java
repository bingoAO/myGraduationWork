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
		// 1��������������
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// 2��������ʱ�ļ�·��
		factory.setRepository(new File("E:\\healerRespository"));
		// 3���õ��ϴ��ļ�������
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 4��������������
		upload.setHeaderEncoding("utf-8");
		// 5���趨�ļ��ϴ���С����
		// upload.setSizeMax(1024*1024);
		// 6�����н��� �õ����FileItem��List����
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
				System.out.println("��ͨ������" + paramName + " = " + paramValue);
				entity.setName(paramValue);
			} else {
				// ��ȡ�ϴ��ļ���
				String fileName = item.getName();
				// ����ļ�����Ŀ¼����ȥ��Ŀ¼���֣�ֻ�����ļ���
				fileName = fileName.substring(fileName.lastIndexOf("\\") + 1);
				System.out.println("name = " + fileName);

				if (!fileName.equals("")) {
					// ����ͬ������ ����ǰ��UUID���� ����refactorFile����
					String uuidName = refactorFileName(fileName);
					System.out.println("uuidName===" + uuidName);// ���Դ���
					entity.setFileName(uuidName);// ����Ϣ���뵽���ݿ���

					InputStream is = item.getInputStream();
					// �ϴ��ļ�λ������ָ���Ķ���·�� ���÷���
//					String basepath = this.getServletContext().getRealPath(
//							"WEB-INF/upload");
					String basepath = "E:\\healerRespository";
					String finalPath = generateFilePath(basepath, uuidName);// �ļ�������·��+����+��׺
					String finalName = finalPath.substring(finalPath
							.indexOf("_") + 1);// �õ��ļ�����ʵ����
					System.out.println("finalName===" + finalName);
					entity.setFilePath(finalPath);// ����Ϣ���뵽���ݿ���
					entity.setFile(item.get());// ���ϴ��ļ����뵽���ݿ���

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
					request.setAttribute("message", "�ļ��ϴ��ɹ�");

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
