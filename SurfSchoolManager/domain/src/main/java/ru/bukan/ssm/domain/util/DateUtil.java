package ru.bukan.ssm.domain.util;

import java.util.Calendar;
import java.util.Date;

/**
 * Утилиты по работе с датой
 * @author by Ilin_ai on 25.07.2017.
 */
public class DateUtil {

    /**
     * Возврат даты без времени
     */
    public static Date trunc(Date d){
        Calendar calc = Calendar.getInstance();
        calc.setTime(d);
        calc.set(Calendar.HOUR_OF_DAY, 0);
        calc.set(Calendar.MINUTE, 0);
        calc.set(Calendar.SECOND, 0);
        calc.set(Calendar.MILLISECOND, 0);
        return calc.getTime();
    }

    /**
     * Увеличение даты на опредлённую величину
     * @param d входная дата
     * @param field на какую величину необходимо увеличить (Берутся констаты из класса Calendar)
     * @param amount на какую сумму нужно увеличить дату
     */
    public static Date addTime(Date d, int field, int amount){
        Calendar calc = Calendar.getInstance();
        calc.setTime(d);
        calc.set(field, amount);
        return calc.getTime();
    }
}
