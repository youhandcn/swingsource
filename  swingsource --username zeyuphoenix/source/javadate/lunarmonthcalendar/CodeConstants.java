package lunarmonthcalendar;

import java.util.HashMap;
import java.util.Map;

public class CodeConstants {

	// 这个是用的一个数据表格来实现的,对于Java来说，一个整数是32位，这里取用它的
	// 前24位(其实有价值的只有20位,所以最前面的0都省略了)来表示某个年份的阴历的
	// 信息,结构如下：
	// ********************************************************************
	// * 3 2 1 0 *
	// ********************************************************************
	// 第三、二、一和零位范围为1-12代表当年所闰的月
	// ********************************************************************
	// * 15 * 14 * 13 * 12 * 11 * 10 * 9 * 8 * 7 * 6 * 5 * 4 *
	// ********************************************************************
	// 因为农历存在闰月,所以一年可能有13个月,第十五到第四位这12位代表正常农历月的
	// 大小月,月份对应位为1，农历月大(30 天),为0 表示小(29 天)
	// ********************************************************************
	// * 19 18 17 16 *
	// ********************************************************************
	// 第十六位代表当年闰月的大小月,对应位为1,农历月大(30 天),为0 表示小(29 天),
	// 闰月的有无则根据后面的位来标示,没有闰月则标0.
	//
	// 存储1900-2020年的闰年信息
	public final static long[] LUNAR_INFO = new long[] { 0x04bd8, 0x04ae0,
			0x0a570, 0x054d5, 0x0d260, 0x0d950, 0x16554, 0x056a0, 0x09ad0,
			0x055d2, 0x04ae0, 0x0a5b6, 0x0a4d0, 0x0d250, 0x1d255, 0x0b540,
			0x0d6a0, 0x0ada2, 0x095b0, 0x14977, 0x04970, 0x0a4b0, 0x0b4b5,
			0x06a50, 0x06d40, 0x1ab54, 0x02b60, 0x09570, 0x052f2, 0x04970,
			0x06566, 0x0d4a0, 0x0ea50, 0x06e95, 0x05ad0, 0x02b60, 0x186e3,
			0x092e0, 0x1c8d7, 0x0c950, 0x0d4a0, 0x1d8a6, 0x0b550, 0x056a0,
			0x1a5b4, 0x025d0, 0x092d0, 0x0d2b2, 0x0a950, 0x0b557, 0x06ca0,
			0x0b550, 0x15355, 0x04da0, 0x0a5d0, 0x14573, 0x052d0, 0x0a9a8,
			0x0e950, 0x06aa0, 0x0aea6, 0x0ab50, 0x04b60, 0x0aae4, 0x0a570,
			0x05260, 0x0f263, 0x0d950, 0x05b57, 0x056a0, 0x096d0, 0x04dd5,
			0x04ad0, 0x0a4d0, 0x0d4d4, 0x0d250, 0x0d558, 0x0b540, 0x0b5a0,
			0x195a6, 0x095b0, 0x049b0, 0x0a974, 0x0a4b0, 0x0b27a, 0x06a50,
			0x06d40, 0x0af46, 0x0ab60, 0x09570, 0x04af5, 0x04970, 0x064b0,
			0x074a3, 0x0ea50, 0x06b58, 0x055c0, 0x0ab60, 0x096d5, 0x092e0,
			0x0c960, 0x0d954, 0x0d4a0, 0x0da50, 0x07552, 0x056a0, 0x0abb7,
			0x025d0, 0x092d0, 0x0cab5, 0x0a950, 0x0b4a0, 0x0baa4, 0x0ad50,
			0x055d9, 0x04ba0, 0x0a5b0, 0x15176, 0x052b0, 0x0a930, 0x07954,
			0x06aa0, 0x0ad50, 0x05b52, 0x04b60, 0x0a6e6, 0x0a4e0, 0x0d260,
			0x0ea65, 0x0d530, 0x05aa0, 0x076a3, 0x096d0, 0x04bd7, 0x04ad0,
			0x0a4d0, 0x1d0b6, 0x0d250, 0x0d520, 0x0dd45, 0x0b5a0, 0x056d0,
			0x055b2, 0x049b0, 0x0a577, 0x0a4b0, 0x0aa50, 0x1b255, 0x06d20,
			0x0ada0 };

	/**
	 * 定义月份的中文显示
	 */
	public final static String[] CHINESE_MONTH_NUMBER = { "正月", "二月", "三月",
			"四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "腊月" };

	/**
	 * 定义日期的中文表示
	 */
	public final static String[] CHINESE_DAY_NUMBER = { "一", "二", "三", "四",
			"五", "六", "七", "八", "九", "十", "初", "十", "廿", "卅" };

	/**
	 * 定义中国的十二生肖
	 */
	public final static String[] CHINESE_ZODIAC = { "鼠", "牛", "虎", "兔", "龙",
			"蛇", "马", "羊", "猴", "鸡", "狗", "猪" };

	/**
	 * 定义中国的天干
	 */
	public final static String[] CHINESE_TIANGAN = { "甲", "乙", "丙", "丁", "戊",
			"己", "庚", "辛", "壬", "癸" };

	/**
	 * 定义中国的地支
	 */
	public final static String[] CHINESE_DIZHI = { "子", "丑", "寅", "卯", "辰",
			"巳", "午", "未", "申", "酉", "戌", "亥" };

	/**
	 * 定义中国的星期表示
	 */
	public final static String[] CHINESE_WEEK = { "星期一", "星期二", "星期三", "星期四",
			"星期五", "星期六", "星期日" };

	/**
	 * 定义一般的阳历节日
	 */
	public final static Map<String, String> NORMAL_FESTIVAL = new HashMap<String, String>();
	static {
		NORMAL_FESTIVAL.put("0101", "元旦");
		NORMAL_FESTIVAL.put("0214", "情人节");
		NORMAL_FESTIVAL.put("0308", "妇女节");
		NORMAL_FESTIVAL.put("0312", "植树节");
		NORMAL_FESTIVAL.put("0315", "消费者维权");
		NORMAL_FESTIVAL.put("0401", "愚人节");
		NORMAL_FESTIVAL.put("0501", "劳动节");
		NORMAL_FESTIVAL.put("0504", "青年节");
		NORMAL_FESTIVAL.put("0601", "儿童节");
		NORMAL_FESTIVAL.put("0701", "建党节");
		NORMAL_FESTIVAL.put("0801", "建军节");
		NORMAL_FESTIVAL.put("0910", "教师节");
		NORMAL_FESTIVAL.put("1001", "国庆节");
		NORMAL_FESTIVAL.put("1031", "万圣节");
		NORMAL_FESTIVAL.put("1112", "孙中山诞辰");
		NORMAL_FESTIVAL.put("1225", "圣诞节");
		NORMAL_FESTIVAL.put("1226", "毛泽东诞辰");
	}

	/**
	 * 定义中国的阴历节日
	 */
	public final static Map<String, String> CHINESE_FESTIVAL = new HashMap<String, String>();
	static {
		CHINESE_FESTIVAL.put("0101", "春 节");
		CHINESE_FESTIVAL.put("0115", "元宵节");
		CHINESE_FESTIVAL.put("0505", "端午节");
		CHINESE_FESTIVAL.put("0707", "七 夕");
		CHINESE_FESTIVAL.put("0815", "中秋节");
		CHINESE_FESTIVAL.put("0909", "重阳节");
		CHINESE_FESTIVAL.put("1208", "腊八节");
		// 代表十二月的最后一天
		CHINESE_FESTIVAL.put("1299", "除 夕");
	}

	/**
	 * 定义不是固定日期的节日
	 */
	public final static Map<String, String> UNDATE_FESTIVAL = new HashMap<String, String>();
	static {
		// 月(2位)+第几个(1位)+星期几(1位)
		UNDATE_FESTIVAL.put("0520", "母亲节");
		UNDATE_FESTIVAL.put("0630", "父亲节");
		UNDATE_FESTIVAL.put("1144", "感恩节");
	}
	/**
	 * 定义不是节日却要休息的日
	 */
	public final static Map<String, String> UNWORK_FESTIVAL = new HashMap<String, String>();
	static {
		// 元旦休息
		UNWORK_FESTIVAL.put("0102W", "W");
		UNWORK_FESTIVAL.put("0103W", "W");
		// 五一休息
		UNWORK_FESTIVAL.put("0502W", "W");
		UNWORK_FESTIVAL.put("0503W", "W");
		// 国庆休息
		UNWORK_FESTIVAL.put("1002W", "W");
		UNWORK_FESTIVAL.put("1003W", "W");
		UNWORK_FESTIVAL.put("1004W", "W");
		UNWORK_FESTIVAL.put("1005W", "W");
		UNWORK_FESTIVAL.put("1006W", "W");
		UNWORK_FESTIVAL.put("1007W", "W");
		//春节休息
		UNWORK_FESTIVAL.put("0102Y", "Y");
		UNWORK_FESTIVAL.put("0103Y", "Y");
		UNWORK_FESTIVAL.put("0104Y", "Y");
		UNWORK_FESTIVAL.put("0105Y", "Y");
		UNWORK_FESTIVAL.put("0106Y", "Y");
	}
	/**
	 * 定义特殊日子,比如生日
	 */
	public final static Map<String, String> DEFINE_FESTIVAL = new HashMap<String, String>();
	static {
		DEFINE_FESTIVAL.put("1210", "生日");
	}
}
