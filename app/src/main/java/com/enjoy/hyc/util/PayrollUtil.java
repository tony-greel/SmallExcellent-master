package com.enjoy.hyc.util;

import com.enjoy.base.LogUtils;
import com.enjoy.hyc.bean.Moonlighting;
import com.enjoy.hyc.bean.Payroll;

import java.text.Collator;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Locale;

/**
 * Created by hyc on 2017/4/27 11:18
 */

public class PayrollUtil {
    /**
     * 其他兼职的收入
     */
    public static int otherIncome=0;

    /**
     * 通过兼职数据集合计算出每个兼职工作对应的工资单信息，未开始的兼职工作不计算
     * @param moonLighting 兼职数据
     * @return
     */
    public static List<Payroll> countPayrollByMoonlighting(List<Moonlighting> moonLighting){
        List<Payroll> payrolls=new ArrayList<>();
        payrolls=new ArrayList<>();
        long currentTime=System.currentTimeMillis();
        String today = new SimpleDateFormat("yyyy/MM/dd").format(new Date(currentTime));

        for (Moonlighting moon : moonLighting) {
            if (!countDateAfterTime(moon.getJob().getDeadline(), today)) {
                LogUtils.log(moon.getJob().getDeadline());
                long temp = currentTime - (moon.getJob().getWorkDayTime() - 1) * 24L * 60L * 60L * 1000L;
                //对应的兼职的开始时间
                String strDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date(temp));
                if (!countDateAfterTime(moon.getJob().getDeadline(), strDate)) {
                    //该兼职已完成
                    for (int i = 1; i <= moon.getJob().getWorkDayTime(); i++) {
                        String time = "";
                        Payroll p = new Payroll();
                        p.setIncome(moon.getJob().getJobSalary() / moon.getJob().getWorkDayTime());
                        p.setAddress(moon.getJob().getContactAddress());
                        p.setType(moon.getJob().getJobType());
                        try {
                            time = new SimpleDateFormat("yyyy/MM/dd").format(new Date(stringToDate(moon
                                    .getJob().getDeadline()).getTime() + i * 24L * 60L * 60L * 1000L));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            LogUtils.log("时间格式错误");
                            continue;
                        }
                        p.setTime(time);
                        payrolls.add(p);
                    }
                }else {
                    //兼职正在进行中
                    Date date = null;
                    try {
                        date = stringToDate(new SimpleDateFormat("yyyy/MM/dd").format(new Date(currentTime)));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date fdate = null;
                    try {
                        fdate = stringToDate(moon.getJob().getDeadline());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    int haveDay = daysOfTwo(fdate, date) - 1;
                    for (int i = 0; i < haveDay + 1; i++) {
                        String time = "";
                        Payroll p = new Payroll();
                        p.setIncome(moon.getJob().getJobSalary() / moon.getJob().getWorkDayTime());
                        p.setAddress(moon.getJob().getContactAddress());
                        p.setType(moon.getJob().getJobType());
                        try {
                            time = new SimpleDateFormat("yyyy/MM/dd").format(new Date(stringToDate(moon
                                    .getJob().getDeadline()).getTime() + (i + 1) * 24L * 60L * 60L * 1000L));
                        } catch (ParseException e) {
                            e.printStackTrace();
                            LogUtils.log("时间格式错误");
                            continue;
                        }
                        p.setTime(time);
                        payrolls.add(p);
                    }
                }
            }
        }

        if (payrolls.size()>0){
            sort(payrolls);
        }
        return payrolls;
    }

    /**
     * 计算data日期是否在time之后
     * @param date
     * @param time
     * @return true  date日期在time之后  2017/4/5
     */
    private static boolean countDateAfterTime(String date, String time) {
        return Integer.parseInt(date.split("/")[0]) > Integer.parseInt(time.split("/")[0])
                || Integer.parseInt(date.split("/")[0]) == Integer.parseInt(time.split("/")[0])
                && Integer.parseInt(date.split("/")[1]) > Integer.parseInt(time.split("/")[1])
                || Integer.parseInt(date.split("/")[0]) == Integer.parseInt(time.split("/")[0])
                && Integer.parseInt(date.split("/")[1]) == Integer.parseInt(time.split("/")[1])
                && Integer.parseInt(date.split("/")[2]) >= Integer.parseInt(time.split("/")[2]);
    }

    /**
     * 将String类型的日期转化为Date型数据
     * @param time
     * @return
     * @throws ParseException  格式错误对象
     */
    public static Date stringToDate(String time) throws ParseException {
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy/MM/dd");
        return formatter.parse(time);
    }

    /**
     * 计算两个Date日期对象之间相差的天数
     * @param fDate
     * @param oDate
     * @return
     */
    public static int daysOfTwo(Date fDate, Date oDate) {
        Calendar aCalendar = Calendar.getInstance();
        aCalendar.setTime(fDate);
        int day1 = aCalendar.get(Calendar.DAY_OF_YEAR);
        aCalendar.setTime(oDate);
        int day2 = aCalendar.get(Calendar.DAY_OF_YEAR);
        return day2 - day1;
    }

    /**
     * 将计算好的工资单对象信息集合按时间排序
     * @param payrolls
     */
    public static void sort(List<Payroll> payrolls){
        Collections.sort(payrolls, new Comparator<Payroll>() {
            @Override
            public int compare(Payroll o1, Payroll o2) {
                return countDateAfterTime(o1.getTime(),o2.getTime())?-1:1;

            }
        });

    }

    /**
     * 通过工资单中工作类型来计算收入
     * @param type
     * @param mayType
     * @param payrolls
     * @return
     */
    public static int countIncomeType(String type, String mayType, List<Payroll> payrolls) {
        int income = 0;
        for (Payroll payroll : payrolls) {

            if (payroll.getType().equals(type) || payroll.getType().equals(mayType)) {
                income += payroll.getIncome();
            }
        }
        otherIncome += income;
        return income;
    }

    /**
     * 计算当前用户的收入总计
     * @param payrolls
     * @return
     */
    public static int countTotal(List<Payroll> payrolls) {
        int income = 0;
        for (Payroll payroll : payrolls) {
            income += payroll.getIncome();
        }
        return income;
    }

    /**
     * 通过工资单信息集合计算今日收入
     * @param payrolls
     * @return
     */
    public static int countTodayIncome(List<Payroll> payrolls) {
        int income = 0;
        String strDate = new SimpleDateFormat("yyyy/MM/dd").format(new Date(System.currentTimeMillis()));
        for (Payroll payroll : payrolls) {
            if (payroll.getTime().equals(strDate)) {
                income += payroll.getIncome();
            }
        }

        return income;
    }

    /**
     * 计算工作类型中其他的收入
     * @param payrolls
     * @return
     */
    public static int countOther(List<Payroll> payrolls) {
        int income = countTotal(payrolls) - otherIncome;
        otherIncome = 0;
        return income;
    }


}
