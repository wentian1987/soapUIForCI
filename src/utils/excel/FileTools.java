package utils.excel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultStyledDocument;
import javax.swing.text.rtf.RTFEditorKit;

public class FileTools {

	public static String readFile(File file) {
		StringBuilder resultStr = new StringBuilder();
		try {
			BufferedReader bReader = new BufferedReader(new FileReader(file));
			String line = bReader.readLine();
			while (line != null) {
				resultStr.append(line);
				line = bReader.readLine();
			}
			bReader.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return resultStr.toString();
	}

	public static String readRtf(File in) {
		RTFEditorKit rtf;
		String resultStr = null;
		DefaultStyledDocument dsd;
		rtf = new RTFEditorKit();
		dsd = new DefaultStyledDocument();
		try {
			rtf.read(new FileInputStream(in), dsd, 0);
			resultStr = new String(dsd.getText(0, dsd.getLength()).getBytes("GB2312"));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return resultStr;
	}

	public static void writeFile(File file, String str) {
		try {
			BufferedWriter bWriter = new BufferedWriter(new FileWriter(file));
			bWriter.write(str);
			bWriter.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}