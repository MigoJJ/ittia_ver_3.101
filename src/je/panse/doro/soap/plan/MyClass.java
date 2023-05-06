package je.panse.doro.soap.plan;

public class MyClass {
	    private String retA;
	    private String[] retB;

	    public MyClass(String retA, String[] retB) {
	        this.retA = retA;
	        this.retB = retB;
	    }

	    public String getRetA() {
	        return retA;
	    }

	    public String[] getRetB() {
	        return retB;
	    }
	    
	    public static MyClass myMethod(int i) {
	        String retA = "some string value";
	        String[] retB = {"value 123456", "value 245678", "value 378962"};
	        return new MyClass(retA, retB);
	    }

	    public static MyClass myMethod2(int i) {
	        String retA = "myMethod(int 2)";
	        String[] retB = {"this.retB 123456", "this.retB 245678", "this.retB 378962"};
	        return new MyClass(retA, retB);
	    }
}
