package com.graphql.exemple.util;

import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public interface DateFormat {
	
	public final static String TIMEZONE = "GMT-3";
	
	public final static String DATE = "dd/MM/yyyy";

	public final static String DATETIME = "dd/MM/yyyy HH:mm:ss";
	
	public final static PeriodFormatter PERIODCOMPLETE = new PeriodFormatterBuilder()
			.printZeroAlways()
		    .appendYears().appendSuffix(" ano", " anos")
		    .appendMonths().appendSuffix(" mês", " meses")
            .appendDays().appendSuffix(" dia", " dias")
            .appendHours().appendSuffix(" hora", " horas")
            .appendMinutes().appendSuffix(" minuto", " minutos")
            .appendSeparator(" e ")
            .appendSeconds().appendSuffix(" segundo", " segundos")
            .toFormatter();
	
	public final static PeriodFormatter PERIOD_YMD_EXTENDED= new PeriodFormatterBuilder()
			.appendYears().appendSuffix(" ano", " anos")
			.appendMonths().appendSuffix(" mês", " meses")
			.appendSeparator(" e ")
	        .appendDays().appendSuffix(" dia", " dias")
            .toFormatter();
	
	public final static PeriodFormatter PERIOD_YMD = new PeriodFormatterBuilder()
			.appendYears().appendSuffix("a")
			.appendMonths().appendSuffix("m")
	        .appendDays().appendSuffix("d")
            .toFormatter();
}
