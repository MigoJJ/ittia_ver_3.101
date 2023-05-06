package je.panse.doro.soap.plan;

public class ittiaGDSPlanPanel_2_String {
	    private String retA;
	    private String[] retB;

	    public ittiaGDSPlanPanel_2_String(String retA, String[] retB) {
	        this.retA = retA;
	        this.retB = retB;
	    }

	    public String getRetA() {
	        return retA;
	    }

	    public String[] getRetB() {
	        return retB;
	    }
	    
	    public static ittiaGDSPlanPanel_2_String myMethod(int i) {
	        String retA = "123";
	        String[] retB ={"",""};
	        switch (i) {
		        case 1:
		        	retA = "Synthyorid";
		            retB = new String[]{"Synthyorid [ 25 ] ug 1 tab p.o. q.d.",
		            		"Synthyorid [ 37.5 ] ug 1 tab p.o. q.d.",
		            		"Synthyorid [ 50 ] ug 1 tab p.o. q.d.",
		            		"Synthyorid [ 75 ] ug 1 tab p.o. q.d.",
		            		"Synthyorid [ 100 ] ug 1 tab p.o. q.d.",
		            		"Synthyorid [ 112 ] ug 1 tab p.o. q.d.",
		            		"Synthyorid [ 150 ] ug 1 tab p.o. q.d.",
		            };
		            break;
		        case 2:
		        	retA = "Synthyroxine";
		            retB = new String[]{"Synthyroxine [ 25 ] ug 1 tab p.o. q.d.",
		            		"Synthyroxine [ 37.5 ] ug 1 tab p.o. q.d.",
		            		"Synthyroxine [ 50 ] ug 1 tab p.o. q.d.",
		            		"Synthyroxine [ 75 ] ug 1 tab p.o. q.d.",
		            		"Synthyroxine [ 100 ] ug 1 tab p.o. q.d.",
		            		"Synthyroxine [ 150 ] ug 1 tab p.o. q.d.",
		            };		            
	            	break;
		        default:
		            System.out.println("Number is neither 1 nor 2");
		            break;
		    }
	        return new ittiaGDSPlanPanel_2_String(retA, retB);
	    }
	    
}
