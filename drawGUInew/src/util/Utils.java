package util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class Utils {

	
	public static void main(String[] args) throws IOException {
	//	Utils.addLog("fafasf");
		//Utils.addLog("1231442343");
	}
	

	

	
	
	
	public static String readFileToString(String filePath) throws IOException {
		File inputFile = new File(filePath);
		String holdText = "";
		try {
			String encoding = "GBK";
			if (inputFile.isFile() && inputFile.exists()) { 
				InputStreamReader read = new InputStreamReader(
						new FileInputStream(inputFile), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					holdText+=lineTxt+"\r\n";
				}
				read.close();
			} else {
			}
		} catch (Exception e) {
			System.out.println("read error");
			e.printStackTrace();
		}

		return holdText;
	}
	
	
	

	
}
