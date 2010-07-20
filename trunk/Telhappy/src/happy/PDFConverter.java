package happy;

import java.io.File;
import java.io.IOException;

public class PDFConverter {
	
	private File sourceFile;

	public PDFConverter(File sourceFile) {
		this.sourceFile = sourceFile;
	}
	
	public File convert() {
		
		String pdfName = sourceFile.getAbsolutePath();
		String txtName = pdfName.replaceAll("pdf$", "txt");
		Process converter;
		try {
			converter = Runtime.getRuntime().exec(new String[]{"pdftotext", pdfName, txtName});
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		try {
			converter.waitFor();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		File txt = new File(txtName);
		return txt;
	}

}
