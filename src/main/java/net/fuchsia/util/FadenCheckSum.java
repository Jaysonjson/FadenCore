package net.fuchsia.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class FadenCheckSum {

	public static byte[] create(InputStream inputStream) throws NoSuchAlgorithmException, IOException {
		byte[] readBuffer = new byte[1024];
		MessageDigest digest = MessageDigest.getInstance("MD5");
		int read = 0;
		
		while(read != -1) {
			read = inputStream.read(readBuffer);
			if(read > 0) digest.update(readBuffer, 0, read);
		}
		return digest.digest();
	}

	public static byte[] create(String string) throws NoSuchAlgorithmException, IOException {
		MessageDigest digest = MessageDigest.getInstance("MD5");
 		digest.update(string.getBytes(), 0, string.getBytes().length);
		return digest.digest();
	}

	public static String checkSum(String string) {
		try {
			byte[] digest = create(string);
			StringBuilder checksum = new StringBuilder();
			for (byte b : digest) {
				checksum.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
			}
			return checksum.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String checkSum(InputStream inputStream) {
		try {
			byte[] digest = create(inputStream);
			StringBuilder checksum = new StringBuilder();
            for (byte b : digest) {
                checksum.append(Integer.toString((b & 0xff) + 0x100, 16).substring(1));
            }
			return checksum.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String checkSum(File file) {
		try {
			InputStream inputStream = new FileInputStream(file);
			String checkSum = checkSum(inputStream);
			inputStream.close();
			return checkSum;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
}
