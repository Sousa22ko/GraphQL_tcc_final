package com.graphql.exemple.util;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;

public class DateHelper {

	public static final Date HOJE = Calendar.getInstance().getTime();
	public static final Date ONTEM = updateDaysOfDate(HOJE, -1);
	public static final Date AMANHA = updateDaysOfDate(HOJE, 1);

	/**
	 * 
	 * @param date - Data que deverá ser convertida para timestamp;
	 * @return Timestamp correspondente à data informada.
	 */
	public static Timestamp getTimestampFromDate(Date date) {
		return new Timestamp(date.getTime());
	}

	/**
	 * 
	 * @param timestamp - Timestamp que deverá ser convertida para data;
	 * @return Data correspondente à timestamp informada.
	 */
	public static Date getDateFromTimestamp(Timestamp timestamp) {
		return new Date(timestamp.getTime());
	}

	/**
	 * 
	 * @param date - Data que deverá ser convertida para string;
	 * @param dateFormat - Formato de representação da data que deve ser utilizado na string;
	 * @return String representando a data no formato informado.
	 */
	private static String getStringFromDateAndFormat(Date date, String dateFormat) {

		return new SimpleDateFormat(dateFormat).format(date);
	}

	/**
	 * 
	 * @param date - Data que deverá ser convertida para string;
	 * @return String representando a data no formato dd/MM/yyyy.
	 */
	public static String getDateAsDateString(Date date) {

		return getStringFromDateAndFormat(date, DateFormat.DATE);
	}

	/**
	 * 
	 * @param date - Data que deverá ser convertida para string;
	 * @return String representando a data no formato dd/MM/yyyy HH:mm:ss.
	 */
	public static String getDateAsDateTimeString(Date date) {

		return getStringFromDateAndFormat(date, DateFormat.DATETIME);
	}

	/**
	 * 
	 * @param date - Date que será convertida para LocalDateTime;
	 * @return - Data representada como LocalDateTime.
	 */
	public static LocalDateTime getLocalDateTimeFromDate(Date date) {

		return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
	}
	
	/**
	 * 
	 * @param date - Date que será convertida para Date;
	 * @param formato - formato da data
	 * @return - Date representada como Date.
	 */
	public static Date getDateFromFormattedString(String date, String formato) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(formato);
		return getDateFromLocalDateTime(LocalDate.parse(date, formatter).atStartOfDay());	
	}

	/**
	 * 
	 * @param ldt - LocalDateTime que será convertida para Date;
	 * @return - Data representada como Date.
	 */
	public static Date getDateFromLocalDateTime(LocalDateTime ldt) {

		return Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant());
	}

	/**
	 * 
	 * @param date - Data usada como base para o incremento/decremento dos dias informados;
	 * @param days - Quantidade de dias que deverão ser acrescidos/decrescidos da data informada;
	 * O parâmetro ´days` pode ser negativo, no caso de um decremento de dias;
	 * @return - Data modificada à partir dos dias informados.
	 */
	public static Date updateDaysOfDate(Date date, int days) {

		return getDateFromLocalDateTime(getLocalDateTimeFromDate(date).plusDays(days));
	}

	/**
	 * 
	 * @param date - Data usada como base para o incremento/decremento dos meses informados;
	 * @param months - Quantidade de meses que deverão ser acrescidos/decrescidos da data informada;
	 * O parâmetro ´months` pode ser negativo, no caso de um decremento de meses;
	 * @return - Data modificada à partir dos meses informados.
	 */
	public static Date updateMonthsOfDate(Date date, int months) {

		return getDateFromLocalDateTime(getLocalDateTimeFromDate(date).plusMonths(months));
	}

	/**
	 * 
	 * @param date - Data usada como base para o incremento/decremento dos anos informados;
	 * @param years - Quantidade de anos que deverão ser acrescidos/decrescidos da data informada;
	 * O parâmetro ´years` pode ser negativo, no caso de um decremento de anos;
	 * @return - Data modificada à partir dos anos informados.
	 */
	public static Date updateYearsOfDate(Date date, int years) {

		return getDateFromLocalDateTime(getLocalDateTimeFromDate(date).plusYears(years));
	}
	/**
	 * 
	 * @param date1
	 * @param date2
	 * @return o número de dias entre as duas datas
	 */
	public static long getDifferenceDays(Date date1, Date date2) {
	    long diff = date1.getTime() - date2.getTime();
	    long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
	    return Math.abs(days);
	}

	public static String getPeriod(Date date1, Date date2, PeriodFormatter periodFormatter){
		Period period = new Period(new DateTime(date1), new DateTime(date2));
		return period.toString(periodFormatter);
	}

	/**
	 * Retorna a quantidade de minutos de uma data hora 
	 * 
	 * @param hora Hora no formato hh:mm
	 * @return Quantidade de minutos
	 */
	public static long getHoursToMinutes(String hora) {
		String[] split = hora.split(":");  
		try {
			if (split.length == 0) {
				return 0;
			}
			LocalTime localTime = LocalTime.of(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
			
			return (localTime.toSecondOfDay()/60);	      
		} catch (Exception e) {  
			return 0;  
		}  		
	}

	/**
	 * 
	 * @return Data e hora atuais como Timestamp.
	 */
	public static Timestamp getTimestampAtual() {
		return new Timestamp(Calendar.getInstance().getTime().getTime());
	}

	public static long calcWeekDays(LocalDate start, LocalDate end) {
	    DayOfWeek startW = start.getDayOfWeek();
	    DayOfWeek endW = end.getDayOfWeek();

	    long days = ChronoUnit.DAYS.between(start, end);
	    long daysWithoutWeekends = days - 2 * ((days + startW.getValue())/7);

	    return daysWithoutWeekends + (startW == DayOfWeek.SUNDAY ? 1 : 0) + (endW == DayOfWeek.SUNDAY ? 1 : 0);
	}
	
	public static long calcWeekendDays(LocalDate start, LocalDate end) {
	    DayOfWeek startW = start.getDayOfWeek();
	    DayOfWeek endW = end.getDayOfWeek();
	    long days = 2 * ((ChronoUnit.DAYS.between(start, end) + startW.getValue() + 1)/7);
	    
	    if(startW == DayOfWeek.SUNDAY) days--;
	    if(endW == DayOfWeek.SATURDAY) days--;
	    
	    return days;
	}
}
