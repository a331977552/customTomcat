package org.learn;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class FileSystemClassLoader extends ClassLoader {
	private String rootDir;

	public FileSystemClassLoader(String rootDir) {
		this.rootDir = rootDir;
	}

	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] data = getClassData(name);
		if (data == null) {
			throw new ClassNotFoundException();
		}
		Class<?> defineClass = defineClass(name, data, 0, data.length);
		return defineClass;
	}

	private byte[] getClassData(String name) {
		try {
			String fullPath = getFullPath(name);
			FileInputStream fileInputStream = new FileInputStream(fullPath);
			ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
			byte[] arr = new byte[2048];
			int len = 0;
			while ((len = fileInputStream.read(arr)) != -1) {
				arrayOutputStream.write(arr, 0, len);
			}
			fileInputStream.close();

			return arrayOutputStream.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	private String getFullPath(String name) {

		return  rootDir+File.separatorChar + name.replace('.', File.separatorChar) + ".class";
	}
}
