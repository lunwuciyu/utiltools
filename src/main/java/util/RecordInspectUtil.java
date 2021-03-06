package util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;

public class RecordInspectUtil {
	public static Logger log = Logger.getLogger(RecordInspectUtil.class);
	
	/**
	 * 构建RecordQueryService录像查询请求xml报文
	 * 
	 * @param svrIp
	 * @param svrPort
	 * @param bCascade 0-本级录像查询，1-级联录像查询
	 * @param recordStyle
	 * @param netZoneId
	 * @param beginTimeInSecond 开始时间(s)
	 * @param endTimeInSecond 结束时间(s)
	 * @param cameraId 
	 * @param recType 录像类型(计划、移动、手动、报警)
	 * @return
	 */
	public static String buildRecordQueryRequestXml(String svrIp, int svrPort,
			int bCascade, int recordStyle, int netZoneId,
			long beginTimeInSecond, long endTimeInSecond, String cameraIndexCode,
			int recType) {
		StringBuilder recordQueryRequestXml = new StringBuilder();
		recordQueryRequestXml
				.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		recordQueryRequestXml.append("<Message>");
		recordQueryRequestXml.append("<ServerIP>").append(svrIp)
				.append("</ServerIP>");
		recordQueryRequestXml.append("<ServerPort>").append(svrPort)
				.append("</ServerPort>");
		recordQueryRequestXml.append("<BCascade>").append(bCascade)
				.append("</BCascade>");
		recordQueryRequestXml.append("<QueryCondition>");
		recordQueryRequestXml.append("<DeviceType>").append(recordStyle)
				.append("</DeviceType>");
		recordQueryRequestXml.append("<ZoneID>").append(netZoneId)
				.append("</ZoneID>");
		recordQueryRequestXml.append("<QueryType>").append("0")
				.append("</QueryType>");
		recordQueryRequestXml.append("<BeginTime>").append(beginTimeInSecond)
				.append("</BeginTime>");
		recordQueryRequestXml.append("<EndTime>").append(endTimeInSecond)
				.append("</EndTime>");
		recordQueryRequestXml.append("<Conditions>");
		recordQueryRequestXml.append("<Condition>");
		recordQueryRequestXml.append("<CameraID>").append(cameraIndexCode)
				.append("</CameraID>");
		recordQueryRequestXml.append("</Condition>");
		recordQueryRequestXml.append("<Condition>");
		recordQueryRequestXml.append("<RecType>").append(recType)
				.append("</RecType>");
		recordQueryRequestXml.append("</Condition>");
		recordQueryRequestXml.append("</Conditions>");
		recordQueryRequestXml.append("</QueryCondition>");
		recordQueryRequestXml.append("</Message>");

		return recordQueryRequestXml.toString();
	}
	
	public static String converDateStrToDatetimeStr(String dateStr){
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = df.parse(dateStr);
			df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			return df.format(date);
		} catch (ParseException e) {
			log.error(e.getMessage(),e);
		}
		return dateStr;
	}
	
	public static Calendar converDateStrToCalendar(String dateStr){
		Calendar calendar = Calendar.getInstance();
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			Date date = df.parse(dateStr);
			calendar.setTime(date);
		} catch (ParseException e) {
			log.error(e.getMessage(),e);
		}
		return calendar;
	}
	
	/**
	 * 将calendar时间部分设置为0
	 * @param calendar
	 * @return
	 */
	public static Calendar setCalendarTimeZero(Calendar calendar){
		calendar.set(calendar.get(Calendar.YEAR),
				calendar.get(Calendar.MONTH),
				calendar.get(Calendar.DATE) , 0, 0, 0);// 时分秒置为0
		calendar.set(Calendar.MILLISECOND, 0);// oracle数据库时间精确到毫秒
		return calendar;
	}
	
	public static String convertCalendarToDatetimeStr(Calendar calendar, String pattern) {
		DateFormat df = new SimpleDateFormat(pattern);
		return df.format(calendar.getTime());
	}
	
	public static Date convertDatetimeStrToDate(String datetimeStr){
		Date date = null;
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
			date = df.parse(datetimeStr);
		} catch (ParseException e) {
			log.error(e.getMessage(),e);
		}
		return date;
	}
	
	public static String convertCalendarToDateStr(Calendar calendar){
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		return df.format(calendar.getTime());
	}
	
	public static Calendar convertDateToCalendar(Date date){
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar;
	}
	
	public static Calendar convertSecondsToCalendar(long seconds){
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(seconds*1000);
		return calendar;
	}
	
	/**
	 * 判断是否为今天
	 * @param calendar
	 * @return
	 */
	public static boolean isTodayCalendar(Calendar calendar){
		Calendar currentCal = Calendar.getInstance();
		return isSameDay(calendar,currentCal);
	}
	
	/**
	 * 判断是否为同一天
	 * @param calA
	 * @param calB
	 * @return
	 */
	public static boolean isSameDay(Calendar calA,Calendar calB){
		if(calA.get(Calendar.YEAR) == calB.get(Calendar.YEAR) && calA.get(Calendar.MONTH) == calB.get(Calendar.MONTH) && calA.get(Calendar.DATE) == calB.get(Calendar.DATE)){
			return true;
		}else{
			return false;
		}
	}
	
}
