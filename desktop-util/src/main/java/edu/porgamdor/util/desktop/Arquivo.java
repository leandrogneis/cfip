package edu.porgamdor.util.desktop;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;

public class Arquivo {
	/*private static String CFIP_PDF="cfip1.0.pdf";
	public static File resCfipPdf() {
		ClassLoader classLoader = Arquivo.class.getClassLoader();
		File file = new File(classLoader.getResource("file/" +CFIP_PDF).getFile());
		return file;
	}
	public static File cfipPdf() {
		return new File("/cfip/conf/" + CFIP_PDF);
	}
	public static void copiarCfipPdf() throws IOException {
		File source = resCfipPdf();
		File dest = cfipPdf();
		FileChannel sourceChannel = new FileInputStream(source).getChannel();
	    FileChannel destChannel =new FileOutputStream(dest).getChannel();
	    try {
	        destChannel.transferFrom(sourceChannel, 0, sourceChannel.size());
	       }finally{
	           sourceChannel.close();
	           destChannel.close();
	   }
	}
	public static void abrirPdf() throws Exception {
		copiarCfipPdf();
		String cmds[] = new String[] {"cmd", "/c", cfipPdf().getAbsolutePath()  };
	    Runtime.getRuntime().exec(cmds);
	}
	public static void main(String[] args) {
		try {
			abrirPdf();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
}
