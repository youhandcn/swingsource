package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class HelloProxyImp implements InvocationHandler {

	private Object delegate = null;

	public Object bind(Object delegate) {

		this.delegate = delegate;

		return Proxy.newProxyInstance(delegate.getClass().getClassLoader(),
				delegate.getClass().getInterfaces(), this);
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {

		Object result = null;

		System.out.println("before call : " + method);

		result = method.invoke(delegate, args);

		System.out.println("after call : " + method);

		return result;
	}

}
