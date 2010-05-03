package lunarmonthcalendar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class Lunar {
	private int year;
	private int month;
	private int day;
	private boolean leap;

	private static SimpleDateFormat chineseDateFormat = new SimpleDateFormat(
			"yyyy年MM月dd日");

	/**
	 * 传出y年m月d日对应的农历.
	 */
	public Lunar(Calendar calendar) {
		int monCyl;
		int leapMonth = 0;
		Date baseDate = null;
		try {
			baseDate = chineseDateFormat.parse("1900年1月31日");
		} catch (ParseException e) {
			e.printStackTrace();
		}

		// 求出和1900年1月31日相差的天数
		int offset = (int) ((calendar.getTime().getTime() - baseDate.getTime()) / 86400000L);
		monCyl = 14;

		// 用offset减去每农历年的天数
		// 计算当天是农历第几天
		// i最终结果是农历的年份
		// offset是当年的第几天
		int iYear, daysOfYear = 0;
		for (iYear = 1900; iYear < 2050 && offset > 0; iYear++) {
			daysOfYear = yearDaysCount(iYear);
			offset -= daysOfYear;
			monCyl += 12;
		}
		if (offset < 0) {
			offset += daysOfYear;
			iYear--;
			monCyl -= 12;
		}
		// 农历年份
		year = iYear;

		// 闰哪个月,1-12
		leapMonth = verificationLeapMonth(iYear);
		leap = false;

		// 用当年的天数offset,逐个减去每月（农历）的天数，求出当天是本月的第几天
		int iMonth, daysOfMonth = 0;
		for (iMonth = 1; iMonth < 13 && offset > 0; iMonth++) {
			// 闰月
			if (leapMonth > 0 && iMonth == (leapMonth + 1) && !leap) {
				--iMonth;
				leap = true;
				daysOfMonth = countLeapDays(year);
			} else
				daysOfMonth = monthDays(year, iMonth);

			offset -= daysOfMonth;
			// 解除闰月
			if (leap && iMonth == (leapMonth + 1))
				leap = false;
			if (!leap)
				monCyl++;
		}
		// offset为0时，并且刚才计算的月份是闰月，要校正
		if (offset == 0 && leapMonth > 0 && iMonth == leapMonth + 1) {
			if (leap) {
				leap = false;
			} else {
				leap = true;
				--iMonth;
				--monCyl;
			}
		}
		// offset小于0时，也要校正
		if (offset < 0) {
			offset += daysOfMonth;
			--iMonth;
			--monCyl;
		}
		month = iMonth;
		day = offset + 1;
	}

	// 取得农历 year年的总天数
	private final int yearDaysCount(int year) {
		int i = 0;
		// 先按小月得到最小的天数 12 * 29 = 348.
		int sum = 348;
		// 判断12个月那个是大月,大月则加一天.
		for (i = 0x8000; i > 0x8; i >>= 1) {
			if ((CodeConstants.LUNAR_INFO[year - 1900] & i) != 0) {
				sum += 1;
			}
		}
		// 加上闰月的天数,得到一年的总天数
		return (sum + countLeapDays(year));
	}

	// 取得农历 year年闰月的天数
	private final int countLeapDays(int year) {
		// 先验证是否存在闰月,不存在则返回0
		if (verificationLeapMonth(year) != 0) {
			// 判断闰月是大月还是小月
			if ((CodeConstants.LUNAR_INFO[year - 1900] & 0x10000) != 0) {
				return 30;
			} else {
				return 29;
			}
		}
		return 0;
	}

	// 验证农历 year年闰哪个月 1-12 , 没闰月传回 0
	private final int verificationLeapMonth(int year) {
		// 把闰年信息的后4位与0x1111进行与操作,有闰月则返回.
		return (int) (CodeConstants.LUNAR_INFO[year - 1900] & 0xf);
	}

	// 取得农历 year年month月的总天数
	private final int monthDays(int year, int month) {
		// 移动指定的位数后进行与操作返回该月是大月还是小月
		if ((CodeConstants.LUNAR_INFO[year - 1900] & (0x10000 >> month)) == 0) {
			return 29;
		} else {
			return 30;
		}
	}

	// 取得农历 year年的生肖
	public String yearZodiac(int year) {
		return CodeConstants.CHINESE_ZODIAC[(year - 4) % 12];
	}

	// 取得农历 year年的天干和地支 0为甲子
	public String cyclical(int year) {
		int num = year - 1900 + 36;
		return (CodeConstants.CHINESE_TIANGAN[num % 10] + CodeConstants.CHINESE_DIZHI[num % 12]);
	}

	// 根据给定的天得出中文显示.
	private final String getChinaDayString(int day) {
		int n = day % 10 == 0 ? 9 : day % 10 - 1;
		if (day > 30) {
			return "";
		}
		if (day == 10) {
			day = 0;
		}
		return CodeConstants.CHINESE_DAY_NUMBER[day / 10 + 10]
				+ CodeConstants.CHINESE_DAY_NUMBER[n];
	}

	@Override
	public String toString() {
		return (leap ? "闰" : "")
				+ CodeConstants.CHINESE_MONTH_NUMBER[month - 1]
				+ getChinaDayString(day);
	}

	// 返回阿拉伯数字形式的阴历日期
	public String getNumericMD() {
		String temp_mon = month < 10 ? "0" + month : "" + month;
		String temp_day = day < 10 ? "0" + day : "" + day;

		return temp_mon + temp_day;
	}

	// 返回阴历的年
	public String getYear() {
		return cyclical(year) + "年";
	}

	// 返回阴历的月份
	public String getMonth() {
		return CodeConstants.CHINESE_MONTH_NUMBER[month - 1];
	}

	// 返回阴历的天
	public String getDay() {
		return getChinaDayString(day);
	}

	public static void main(String[] args) throws ParseException {
		Calendar today = Calendar.getInstance(Locale.CHINA);
		// today.setTime(new Date());// 加载当前日期
		today.set(2009, 6, 5);
		Lunar lunar = new Lunar(today);
		System.out.println(lunar.getYear());// 计算输出阴历年份
		System.out.println(lunar.toString());// 计算输出阴历日期
		System.out.println(lunar.yearZodiac(today.get(Calendar.YEAR)));// 计算输出属相
		System.out.println(new SimpleDateFormat("yyyy年MM月dd日").format(today
				.getTime()));// 输出阳历日期
		System.out.println(CodeConstants.CHINESE_WEEK[today
				.get(Calendar.DAY_OF_WEEK) - 1]);// 计算输出星期几
	}
}
