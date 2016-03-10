package utils;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FileUploadUtils {
public static void show(HttpServletRequest request,HttpServletResponse response){
	String path = "E:\\healerRespository\\14\\7\\sheshihao.jpg";
	File file = new File(path);
	if(file.exists()&&!"".equals(path)){
		try {
			DataOutputStream dos = new DataOutputStream(response.getOutputStream());
			DataInputStream dis = new DataInputStream(new FileInputStream(file.getAbsoluteFile()));
			byte[] data = new byte[2048];
			while(dis.read(data)!=-1){
				dos.write(data);
				dos.flush();
			}
			dis.close();
			dos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
}
