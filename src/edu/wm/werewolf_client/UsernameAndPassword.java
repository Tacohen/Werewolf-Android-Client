package edu.wm.werewolf_client;

public class UsernameAndPassword {
	
	private static String username;
	private static String password;
	
	public static String getUsername() {
		return username;
	}
	public static void setUsername(String username) {
		UsernameAndPassword.username = username;
	}
	public static String getPassword() {
		return password;
	}
	public static void setPassword(String password) {
		UsernameAndPassword.password = password;
	}

}
