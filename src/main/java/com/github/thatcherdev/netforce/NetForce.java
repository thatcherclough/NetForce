package com.github.thatcherdev.netforce;

import org.fusesource.jansi.AnsiConsole;

public class NetForce {

	private static String mode;
	private static String host;
	private static int port = 22;
	private static String user;
	private static String file;
	private static int timeout = 300;
	 private static String help = "NetForce: A network scanning and SSH brute forcing tool (1.0)\n\nUsage:\n\tjava -jar netforce.jar [-h] [-v] [-s -t IPRANGE -p PORT -w TIMEOUT]\n"
			+ "\t\t\t       [-b -t HOST -p PORT -u USER -f FILE -w TIMEOUT]\nArguments:\n\t-h, --help\t\tDisplay this message.\n\t-v, --version\t\tDisplay current version.\n"
			+ "\t-s, --scan\t\tScan network for open ports.\n\t-b, --bruteforce\tBrute force SSH server.\n\t-t, --target\t\tSpecify IP range when scanning, or host when brute forcing.\n"
			+ "\t-p, --port\t\tSpecify port to use. (Set to 22 if not specified)\n\t-u, --user\t\tSpecify user to brute force.\n\t-f, --file\t\tSpecify wordlist file to user when brute forcing.\n"
			+ "\t-w, --wait\t\tSpecify connection timeout in milliseconds. (Set to 300 if not specified)";

	/**
	 * Start scan or SSH brute force attack based on command line arguments.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		try {
			AnsiConsole.systemInstall();
			if (args.length == 0)
				throw new Exception();
			for (int k = 0; k < args.length; k++) {
				if (args[k].equals("-h") || args[k].equals("--help")) {
					throw new Exception();
				} else if (args[k].equals("-v") || args[k].equals("--version")) {
					System.out.println(help.substring(0, help.indexOf("\n")));
					System.exit(0);
				} else if (args[k].equals("-s") || args[k].equals("--scan"))
					mode = "scan";
				else if (args[k].equals("-b") || args[k].equals("--bruteforce"))
					mode = "bf";
				else if (args[k].equals("-t") || args[k].equals("--target"))
					host = args[++k];
				else if (args[k].equals("-p") || args[k].equals("--port"))
					port = Integer.parseInt(args[++k]);
				else if (args[k].equals("-u") || args[k].equals("--user"))
					user = args[++k];
				else if (args[k].equals("-f") || args[k].equals("--file"))
					file = args[++k];
				else if (args[k].equals("-w") || args[k].equals("--wait"))
					timeout = Integer.parseInt(args[++k]);
			}
			if (mode == null || (mode.equals("scan") && host == null)
					|| (mode.equals("bf") && (host == null || user == null || file == null)))
				throw new Exception();
			if (mode.equals("scan"))
				NetScan.scan(host, port, timeout);
			else if (mode.equals("bf"))
				Bruteforce.start(host, port, user, file, timeout);
		} catch (Exception e) {
			System.out.println(help);
			System.exit(0);
		}
	}
}
