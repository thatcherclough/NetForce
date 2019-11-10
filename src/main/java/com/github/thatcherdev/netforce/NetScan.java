package com.github.thatcherdev.netforce;

import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.net.Inet4Address;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class NetScan {

	private static ArrayList<String> open = new ArrayList<String>();

	/**
	 * Scans all IP addressed included in {@link ipRange} for open port {@link port}
	 * with timeout {@link timeout} and displays IP addresses with port {@link port}
	 * open.
	 *
	 * @param ipRange range of IP addresses to scan
	 * @param port    port to check
	 * @param timeout timeout for connecting to IP address
	 */
	public static void scan(String ipRange, int port, int timeout) {
		try {
			System.out.println("Scanning " + ipRange + ":" + port);
			ExecutorService es = Executors.newCachedThreadPool();
			String[] parts = ipRange.split("\\.", -1);
			for (String part0 : getNums(parts[0]))
				for (String part1 : getNums(parts[1]))
					for (String part2 : getNums(parts[2]))
						for (String part3 : getNums(parts[3])) {
							String ip = part0 + "." + part1 + "." + part2 + "." + part3;
							es.execute(new Runnable() {
								@Override
								public void run() {
									if (portIsOpen(ip, port, timeout))
										open.add(ip);
								}
							});
						}
			es.shutdown();
			es.awaitTermination(1, TimeUnit.MINUTES);
			System.out.println();
			if (open.size() > 0) {
				System.out.println("Hosts with port " + port + " open:\n");
				for (String ip : open) {
					String name = Inet4Address.getByName(ip).getHostName();
					if (name.equals(ip))
						System.out.println(ip);
					else
						System.out.println(ip + "/" + name);
				}
			} else
				System.out.println("No hosts with port " + port + " open were found");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets all numbers in a given range.
	 *
	 * @param range range of numbers
	 * @return all numbers included in {@link range}
	 */
	private static ArrayList<String> getNums(String range) {
		ArrayList<String> nums = new ArrayList<String>();
		if (range.contains("-"))
			for (int k = Integer.parseInt(range.substring(0, range.indexOf("-"))); k <= Integer
					.parseInt(range.substring(range.indexOf("-") + 1)); k++)
				nums.add(Integer.toString(k));
		else
			nums.add(range);
		return nums;
	}

	/**
	 * Checks if host {@link host} has port {@link port} open with timeout
	 * {@link timeout}.
	 *
	 * @param ip      IP address to check for open port
	 * @param port    port to check if open
	 * @param timeout timeout for connecting to IP address
	 * @return if the port is open
	 */
	private static boolean portIsOpen(String ip, int port, int timeout) {
		try {
			Socket socket = new Socket();
			socket.connect(new InetSocketAddress(ip, port), timeout);
			socket.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}