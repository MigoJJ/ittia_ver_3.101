		package je.panse.doro.listner.buttons;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import je.panse.doro.GDSEMR_frame;
import je.panse.doro.chartplate.EMR_Write_To_Chartplate;
import je.panse.doro.entry.EntryDir;
import je.panse.doro.entry.IttiaEntry;
import je.panse.doro.fourgate.hypertension.EMR_FU_hypertensionEdit;

public class EMR_B_1entry extends GDSEMR_frame {
    public EMR_B_1entry() throws Exception {
        super();
        // TODO Auto-generated constructor stub
    }

    public static void EMR_B_selection(int noButton, String panelLocation) throws Exception{
        // TODO Auto-generated method stub

        	switch (noButton) {
				case 1:
						if (panelLocation.equals("north")) {
						    // When the clear button is clicked, clear all the input text areas
						    for (int i = 0; i < textAreas.length; i++) {
						    	textAreas[i].setText("");
								String inputData = titles[i] + "\t";
								textAreas[i].setText(inputData);
						    }
						tempOutputArea.setText("");
						System.out.println("clear~~~ this button");
						}
				    break;
				    
				case 2:
						EMR_Write_To_Chartplate.copyToClipboard(tempOutputArea);
						EMR_Write_To_Chartplate.callsaveTextToFile(tempOutputArea);
				    break;
				
				case 3:
					String filepath = EntryDir.homeDir + "/tripikata/rescue/backup";
				    try (BufferedReader reader = new BufferedReader(new FileReader(filepath))) {
				        String line;
				        StringBuilder fileContents = new StringBuilder();
				        while ((line = reader.readLine()) != null) {
				            fileContents.append(line);
				            fileContents.append(System.lineSeparator());
				        }
				        ProcessBuilder pb = new ProcessBuilder("gedit", filepath);
				        pb.start();
				    } catch (IOException ex) {
				        ex.printStackTrace();
				    }  	
				    break;
				case 4:
					IttiaEntry.main(null);
					System.exit(0);
				    break;
				
				default:
				    System.out.println("I do not recognize this button");
				}
				
			switch (noButton) {
				case 1:
						if (panelLocation.equals("south")) {				    
							System.out.println("southsouthsouth 1 1 1 ~~!!");
							EMR_FU_hypertensionEdit.main(null);

						    // 
							}
				    break;
				    
				case 2:
						
				    break;
				
				case 3:
					  	
				    break;
				case 4:
				    break;
				
				default:
				    System.out.println("I do not recognize this button");
			}
	}
}
