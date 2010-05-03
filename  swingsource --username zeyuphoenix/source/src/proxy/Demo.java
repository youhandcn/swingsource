package proxy;

/**
 *动态代理 运行时注入。
 */
public class Demo {

	public static void main(String[] args) {
		HelloProxyImp proxyImp = new HelloProxyImp();
		IHello helloProxy = (IHello) proxyImp.bind(new HelloImp());

		helloProxy.hello("zeyu");
	}
}
