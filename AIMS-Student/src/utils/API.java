package utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.logging.Logger;

import entity.payment.CreditCard;
import entity.payment.PaymentTransaction;

/**
 * Class cung cap cac phuong thuc giup gui request len server va nhan du lieu tra ve
 * @author UyenNTT_20184014
 * Date: 10/12/2021
 */
public class API {
	
	public static DateFormat DATE_FORMATER = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
	private static Logger LOGGER = Utils.getLogger(Utils.class.getName());

	/**
	 * Uyen_NTT
	 * Thiet lap connection toi server
	 * @param url: duong dan toi server can request
	 * @param method: giao thuc api
	 * @param token: doan ma bam can cung cap de xac thuc nguoi dung
	 * @return connection
	 * @throws IOException
	 */
	private static HttpURLConnection setupConnection(String url, String method, String token) throws IOException {
		//phan1: setup
		LOGGER.info("Request URL: " + url + "\n");
		URL line_api_url = new URL(url);
		HttpURLConnection conn = (HttpURLConnection) line_api_url.openConnection();
		conn.setDoInput(true);
		conn.setDoOutput(true);
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Content-Type", "application/json");
		conn.setRequestProperty("Authorization", "Bearer " + token);
		return conn;
	}
	
	/**
	 * Uyen_NTT
	 * Phuong thuc doc du lieu tra ve tu server
	 * @param conn: connection to server
	 * @return response: phan hoi tra ve tu server
	 * @throws IOException
	 */
	private static String readResponse(HttpURLConnection conn) throws IOException {
		//phan2: doc du lieu tra ve tu server
				BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				String inputLine;
				if (conn.getResponseCode() / 100 ==2) {
					in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
				} else {
					in = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
				}
				StringBuilder respone = new StringBuilder(); // ising StringBuilder for the sake of memory and performance
				while ((inputLine = in.readLine()) != null)
					System.out.println(inputLine);
				respone.append(inputLine + "\n");
				in.close();
				LOGGER.info("Respone Info: " + respone.substring(0, respone.length() - 1).toString());
				return respone.substring(0, respone.length() - 1).toString();
	}
	
	/**
	 * Phuong thuc giup goi cac api dang GET - UyenNTT
	 * @param url: duong dan toi server can request
	 * @param token: doan ma bam can cung cap de xac thuc nguoi dung
	 * @return respone: phan hoi tu server (dang string) 
	 * @throws Exception
	 */
	public static String get(String url, String token) throws Exception {
		//phan1: Setup
		HttpURLConnection conn = setupConnection(url, "GET", token);
		
		//phan2: doc du lieu tra ve tu server
		String respone = readResponse(conn);
		
		return respone;
	}

	int var;

	/**
	 * Phuong thuc giup goi cacs api dang POST (thanh toan,...) - UyenNTT
	 * @param url: duong dan toi server can request
	 * @param data: du lieu dua len server de xu ly (dang JSON)
	 * @return respone: phan hoi tu server
	 * @throws IOException
	 */
	public static String post(String url, String data, String token) throws IOException {
		//Cho phep PATCH protocol
		allowMethods("PATCH");
		
		//phan1: setup
		HttpURLConnection conn = setupConnection(url, "GET", token);
		
		//phan2: gui du lieu
		Writer writer = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
		writer.write(data);
		writer.close();
		
		//phan3: doc du lieu gui ve tu server
		String respone = readResponse(conn);
		return respone;
	}
	
	/**
	 * Phuong thuc cho phep goi cacs loai giao thuc api khac nhau nhu PATCH, PUT,.. (chi hoat dong voi Java 11) - UyenNTT
	 * @deprecated methods chi hoat dong voi Java <=11
	 * @param methods: giao thuc can cho phep (PATCH, PUT,...)
	 */
	private static void allowMethods(String... methods) {
		try {
			Field methodsField = HttpURLConnection.class.getDeclaredField("methods");
			methodsField.setAccessible(true);

			Field modifiersField = Field.class.getDeclaredField("modifiers");
			modifiersField.setAccessible(true);
			modifiersField.setInt(methodsField, methodsField.getModifiers() & ~Modifier.FINAL);

			String[] oldMethods = (String[]) methodsField.get(null);
			Set<String> methodsSet = new LinkedHashSet<>(Arrays.asList(oldMethods));
			methodsSet.addAll(Arrays.asList(methods));
			String[] newMethods = methodsSet.toArray(new String[0]);

			methodsField.set(null/* static field */, newMethods);
		} catch (NoSuchFieldException | IllegalAccessException e) {
			throw new IllegalStateException(e);
		}
	}

}
