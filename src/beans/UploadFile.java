package beans;

public class UploadFile {

	private String name = "";
	private String fileName = "";
	private String filePath = "";
	private byte[] file;

	public void setName(String paramValue) {
		name = paramValue;
	}

	public void setFileName(String uuidName) {
		fileName = uuidName;
	}

	public void setFilePath(String finalPath) {
		filePath = finalPath;
	}

	public void setFile(byte[] bs) {
		file = bs;
	}
}
