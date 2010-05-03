package cn.idchecker.test;
import cn.idchecker.check.Checker;
/**
 * idchecker测试
 * @author rongxinhua
 *
 */
public class TestChecker {

	public static void main(String[] args) {
		Checker checker = new Checker("131182198311076018");
		System.out.println("出生年月日  : " + checker.getBirth());
		System.out.println("性别  ：" + checker.getSex());
		System.out.println("居民地址  : " + checker.getAddr());
		System.out.println("身份证号码是否合法 : " + checker.check() + "  " + checker.getErrorMsg());
		checker.checkAll();	
		for(String msg : checker.getErrorMsgs()) {
			System.out.print(msg + " | ");
		}
	}

}
