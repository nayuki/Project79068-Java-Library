package p79068.io;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.Collections;
import java.util.SortedSet;
import java.util.TreeSet;

import p79068.lang.NullChecker;


public final class FileUtils {
	
	/**
	 * Returns a sorted set of all items in the specified directory.
	 * @return a sorted set of all items in {@code dir}
	 * @throws IllegalArgumentException if {@code dir} is not a directory
	 */
	public static SortedSet<File> listItems(File dir) {
		checkArgIsDirectory(dir);
		SortedSet<File> result = new TreeSet<File>();
		Collections.addAll(result, dir.listFiles());
		return result;
	}
	
	
	/**
	 * Returns a sorted set of all files in the specified directory.
	 * @return a sorted set of all files in {@code dir}
	 * @throws IllegalArgumentException if {@code dir} is not a directory
	 */
	public static SortedSet<File> listFiles(File dir) {
		checkArgIsDirectory(dir);
		SortedSet<File> result = new TreeSet<File>();
		for (File item : dir.listFiles()) {
			if (item.isFile())
				result.add(item);
		}
		return result;
	}
	
	
	/**
	 * Returns a sorted set of all directories in the specified directory.
	 * @return a sorted set of all directories in {@code dir}
	 * @throws IllegalArgumentException if {@code dir} is not a directory
	 */
	public static SortedSet<File> listDirs(File dir) {
		checkArgIsDirectory(dir);
		SortedSet<File> result = new TreeSet<File>();
		for (File item : dir.listFiles()) {
			if (item.isDirectory())
				result.add(item);
		}
		return result;
	}
	
	
	
	/**
	 * Returns the name of the specified file, without its extension.
	 * For example, "abc.jpg" yields "abc", "foo.tar.gz" yields "foo.tar", and "README" yields "README".
	 * @return the name of the file
	 */
	public static String getNameOnly(File file) {
		NullChecker.check(file);
		return getNameOnly(file.getName());
	}
	
	
	/**
	 * Returns the name of the specified file name string, without its extension.
	 * For example, "abc.jpg" yields "abc", "foo.tar.gz" yields "foo.tar", and "README" yields "README".
	 * For all strings, this expression is true: {@code s.equals(getNameOnly(s) + getExtension(s))}.
	 * @return the name of the file name string
	 */
	public static String getNameOnly(String name) {
		NullChecker.check(name);
		int index = name.lastIndexOf('.');
		if (index > -1)
			return name.substring(0, index);
		else
			return name;
	}
	
	
	/**
	 * Returns the extension of the specified file, including the dot. An empty string is also possible.
	 * For example, "abc.jpg" yields ".jpg", "foo.tar.gz" yields ".gz", and "README" yields "".
	 * @return the extension of the file
	 */
	public static String getExtension(File file) {
		NullChecker.check(file);
		return getExtension(file.getName());
	}
	
	
	/**
	 * Returns the extension of the specified file name string, including the dot. An empty string is also possible.
	 * For example, "abc.jpg" yields ".jpg", "foo.tar.gz" yields ".gz", and "README" yields "".
	 * For all strings, this expression is true: {@code s.equals(getNameOnly(s) + getExtension(s))}.
	 * @return the extension of the file name string
	 */
	public static String getExtension(String name) {
		NullChecker.check(name);
		int index = name.lastIndexOf('.');
		if (index != -1)
			return name.substring(index, name.length());
		else
			return "";
	}
	
	
	
	/**
	 * Renames the specified file to the new name. The new name contains the name portion only, and no paths.
	 * @throws IOException if the rename failed
	 */
	public static void rename(File file, String newname) throws IOException {
		NullChecker.check(file, newname);
		File newfile = new File(file.getParentFile(), newname);
		if (newfile.exists() && !file.getCanonicalFile().equals(newfile.getCanonicalFile()))
			throw new IOException(String.format("New file exists: %s", newfile));
		if (!file.renameTo(newfile))
			throw new IOException(String.format("Rename failed: %s --> %s", file, newname));
	}
	
	
	public static byte[] readAll(File file) throws IOException {
		if (file.length() > Integer.MAX_VALUE)
			throw new RuntimeException("File too large");
		int length = (int)file.length();
		byte[] b = new byte[length];
		InputStream in = new FileInputStream(file);
		try {
			int position = 0;
			while (position < length) {
				int read = in.read(b, position, length - position);
				if (read == -1)
					throw new EOFException("Unexpected end of file");
				position += read;
			}
		} finally {
			in.close();
		}
		return b;
	}
	
	
	public static String readAll(File file, String charset) throws IOException {
		InputStream in0 = new FileInputStream(file);
		InputStreamReader in = new InputStreamReader(in0);
		StringBuffer sb = new StringBuffer();
		char[] c = new char[65536];
		while (true) {
			int tp = in.read(c, 0, c.length);
			if (tp == -1)
				break;
			sb.append(c, 0, tp);
		}
		in.close();
		in0.close();
		return sb.toString();
	}
	
	
	public static void write(File file, byte[] b) throws IOException {
		OutputStream out = new FileOutputStream(file);
		try {
			out.write(b);
		} finally {
			out.close();
		}
	}
	
	
	
	private static void checkArgIsDirectory(File dir) {
		NullChecker.check(dir);
		if (!dir.isDirectory())
			throw new IllegalArgumentException("Not a directory");
	}
	
	
	
	/** 
	 * Not instantiable.
	 */
	private FileUtils() {}
	
}
