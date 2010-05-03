package proxy;

public class HelloImp implements IHello {

	@Override
	public void hello(String name) {

		System.out.println("Hello, " + name);
	}

}
