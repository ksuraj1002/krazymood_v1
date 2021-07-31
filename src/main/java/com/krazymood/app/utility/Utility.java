package com.krazymood.app.utility;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

public class Utility {
	
	public static String setTimeZone() {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Kolkata"));
		SimpleDateFormat sdf =  new SimpleDateFormat("dd MMM yyyy hh:mm a");
		Date date = new Date();
		return sdf.format(date);
	}
	
	public static Pageable getPageListBySort(int pageNm, int recPerPg) {
			return PageRequest.of(pageNm, recPerPg, Sort.by("id").descending());
	}
	
	public static String getMacAdd() throws UnknownHostException, SocketException {
		 String ma=null; 

	      //get the internet address of the local host(local machine)
	      InetAddress address=InetAddress.getLocalHost();
	      System.out.println(address);
	      
	      //get the network interface that has the ip address bound to it(internet address)
	      NetworkInterface ni=NetworkInterface.getByInetAddress(address);
	      System.out.println(ni);
	      
	      //get mac address from the network interface in byte
	      byte[]mac=ni.getHardwareAddress();
			/*
			 * //System.out.println(mac); for(int j=0;j<mac.length;j++){
			 * System.out.println(mac[j]); }
			 */
	      
	      //display the mac address
	      StringBuilder sb=new StringBuilder();
	      //browse the mac address to convert in into string
	      
	      for(int i=0;i<mac.length;i++){
	          
	          sb.append(String.format("%02X%s", mac[i],(i<mac.length-1)?"-":""));
	      }
	      ma=sb.toString();
	      return ma;
	}
	
	/*public static final void sendOtpbyMail(String username, String message, JavaMailSender javaMailSender) {
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();

        simpleMailMessage.setTo(username);
        simpleMailMessage.setSubject("Otp Request");
        simpleMailMessage.setText(message);

        javaMailSender.send(simpleMailMessage);
    }*/

    public static String getHeader(String str){
		return str.replace("-"," ");
	}

    public static boolean compareDatesforLatest(String date1,LocalDateTime dateTime) throws ParseException{
		if(dateTime.plusDays(7).isAfter(LocalDateTime.now())){
			return true;
		}
		return false;
	}

	public static String sitemapDateTime(LocalDateTime dateTime) {
		String dateStr = null;
		if (dateTime != null) {
			dateStr = convertDateTimeToStr(dateTime,"YYYY-MM-dd");
			dateStr=dateStr+"T";
			dateStr = dateStr+convertDateTimeToStr(dateTime,"hh:mm:ss");
			dateStr=dateStr+"+01:00";
		}
		return dateStr;
	}

	public static String convertDateTimeToStr(LocalDateTime dateTime, String dateFormat) {
		String dateStr = null;
		if (dateTime != null) {
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(dateFormat);
			dateStr = dateTime.format(dateTimeFormatter);
		}
		return dateStr;
	}
  
}
