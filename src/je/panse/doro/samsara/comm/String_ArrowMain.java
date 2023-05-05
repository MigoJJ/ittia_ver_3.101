package je.panse.doro.samsara.comm;

public class String_ArrowMain {
	public static void main(String[] args) {
	    String origin = "0.25";
	    double Lrange = 0.80;
	    double Hrange = 10.80;

	    String result = String_ArrowChange.compareOriginAndLrange(origin, Lrange);
	
	    System.out.println(result); // Output: "5.25 🡹"
	}

}