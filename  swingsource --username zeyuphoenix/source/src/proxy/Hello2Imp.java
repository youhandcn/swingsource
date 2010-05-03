package proxy;

public class Hello2Imp implements IHello {

	@Override
	public void hello(String name) {
		
		System.out.println("we say: " + name);
	}

}
