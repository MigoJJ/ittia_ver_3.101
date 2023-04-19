package je.panse.doro.chartplate;

public class EMR_ChangeString {
		
	public static String EMR_ChangeString(String line) {
	    String[] parts = line.split(" "); // Splitting the input string at each space character
	    for (int i = 0; i < parts.length; i++) {
	        if (parts[i].startsWith(":")) { // Checking if the part starts with ":"
	            parts[i] = EMR_ChangeString_abr(parts[i]); // Replacing the part with a new string
	            break; // Exiting the loop as soon as a matching part is found
	        }
	    }
	    String replacedText = String.join(" ", parts); // Joining the modified parts back into a single string
	    System.out.println("replacedText >>> " + replacedText); // Printing the modified string
	    return replacedText;
	}
	
	public static String EMR_ChangeString_abr(String replacedText) {
		replacedText = (replacedText + " ");

	    switch (replacedText) {
				case ":d " : replacedText =("diabetes mellitus");break;
				case ":dr " : replacedText =("DM with retinopathy");break;
				case ":dn " : replacedText =("DM with Nephropathy");break;
				case ":dnp " : replacedText =("DM with Peripheral Neuropathy");break;
				case ":dna " : replacedText =("DM with Autonomic Neuropathy");break;
				case ":dp " : replacedText =("Prediabetes");break;
				case ":dg " : replacedText =("Gestational Diabetes Mellitus");break;
				case ":c " : replacedText =("Hypercholesterolemia F/U");break;
				case ":fd " : replacedText =("Diabetes mellitus  F/U");break;
				case ":ft " : replacedText =("Hypertension F/U");break;    
				case ":fc " : replacedText =("Hypercholesterolemia F/U");break;
				case ":fctg " : replacedText =("HyperTriGlyceridemia F/U");break;
				case ":fte " : replacedText =("Hyperthyroidism F/U");break;
				case ":fto " : replacedText =("Hypothyroidism F/U");break;
				case ":fnti " : replacedText =("Non-Thyroidal Illness F/U");break;
				case ":ftep " : replacedText =("Hyperthyroidism with Pregnancy [   ]  weeks F/U");break;
				case ":ftop " : replacedText =("Hypothyroidism with Pregnancy [   ]  weeks F/U");break;
				case ":do " : replacedText =("DM without complications");break;
				case ":drn " : replacedText =("DM with Retinopathy \n\t: Non-proliferative diabetic retinopathy");break;
				case ":drp " : replacedText =("DM with Retinopathy \n\t: Proliferative diabetic retinopathy");break;
				case ":drm " : replacedText =("DM with Retinopathy \n\t: Macular edema");break;
				case ":dnm " : replacedText =("DM with Nephropathy with microalbuminuria");break;
				case ":dnc " : replacedText =("DM with Nephropathywith CRF");break;
				case ":da " : replacedText =("DM with Autonomic Neuropathy");break;
				case ":pd " : replacedText =("Prediabetes");break;
				case ":pg " : replacedText =("Gestational Diabetes Mellitus");break;
				case ":t " : replacedText =("Hypertension");break;    
				case ":ctg " : replacedText =("HyperTriGlyceridemia");break;
				case ":te " : replacedText =("Hyperthyroidism : Graves' disease");break;
				case ":to " : replacedText =("Hypothyroidism : Hashimoto's thyroiditis");break;
				case ":ts " : replacedText =("Subacute Thyroiditis");break;
				case ":tt " : replacedText =("c/w Chronic Thyroiditis on USG");break;
		               
				
				case ":tn " : replacedText =("Thyroid nodule");break;	
				case ":tc " : replacedText =("Thyroid cyst");break;	
				case ":tsg " : replacedText =("Simple Goiter");break;	
				
				
				case ":at " : replacedText =("Atypical Chest pain");break;
				case ":ap " : replacedText =("Angina Pectoris");break;
				case ":aps " : replacedText =("Angina Pectoris with stent insertion");break;
				case ":omi " : replacedText =("Old Myocardial Infacttion");break; 
				case ":ami " : replacedText =("Acute Myocardial Infaction");break;
				case ":amis " : replacedText =("Acute Myocardial Infaction with stent insertion");break;
				
				case ":as " : replacedText =("Artherosclerosis Carotid artery");break;
				case ":asa " : replacedText =("Artherosclerosis Carotid artery and Aorta");break;
					
				case ":af " : replacedText =("Atrial Fibrillation");break;
				case ":afr " : replacedText =("Atrial Fibrillation with RVR");break;
				case ":afl " : replacedText =("Atrial Flutter");break;
				case ":pvc " : replacedText =("PVC  Premature Ventricular Contractions");break;
				case ":apc " : replacedText =("APC  atrial premature complexes");break;
					
				case ":gre " : replacedText =("Reflux esophagitis");break;
				case ":gcag " : replacedText =("Chonic Atrophic Gastritis");break;
				case ":gcsg " : replacedText =("Chronic Superficial Gastritis");break;
				case ":geg " : replacedText =("r/o Erosive Gastritis");break;
				case ":gibs " : replacedText =("r/o Irritable Bowel Syndrome");break;
				case ":ggil " : replacedText =("Gilbert's syndrome");break;
				case ":gcon " : replacedText =("Severe Constipation");break;
				
				case ":nti " : replacedText =("Non-Thyroidal Illness");break;
				case ":tep " : replacedText =("Hyperthyroidism with Pregnancy [ ] weeks");break;
				case ":top " : replacedText =("Hypothyroidism with Pregnancy [ ] weeks");break;
				case ":tco " : replacedText =("Papillary Thyroid Cancer OP(+)\n\tHypothyroidism");break;
				case ":tcor " : replacedText =("Papillary Thyroid Cancer OP(+) RAI Tx(+)\n\tHypothyroidism");break;
				case ":sos " : replacedText =("Severe Osteoporosis");break;
				case ":os " : replacedText =("Osteoporosis");break;
				case ":ospe " : replacedText =("Osteopenia");break;
				case ":cp " : replacedText =("Colonic Polyp");break;
				case ":cpm " : replacedText =("Colonic Polyps multiple");break;
				case ":cps " : replacedText =("Colonic Polyp single");break;
				case ":cd " : replacedText =("Colonic diverticulum");break;
				case ":gp " : replacedText =("GB polyp");break;
				case ":gs " : replacedText =("GB stone");break;
				case ":ggp " : replacedText =("Gastric Polyp");break;
				case ":oc " : replacedText =("s/p Cholecystectomy d/t GB stone");break;
				case ":oa " : replacedText =("s/p Appendectomy");break;
				case ":occ " : replacedText =("s/p Colon cancer op(+)");break;
				case ":ogc " : replacedText =("s/p Gastric cancer cancer op(+)");break;
				case ":oh " : replacedText =("s/p TAH : Total Abdominal Hysterectomy");break;
				case ":oho " : replacedText =("s/p TAH with BSO");break;
				case ":bph " : replacedText =("BPH");break;
				case ":op " : replacedText =("Prostate cancer operation(+)");break;
				case ":ob " : replacedText =("s/p Breast Cancer Operation");break;
				case ":ot " : replacedText =("Papillary Thyroid Cancer OP(+)\n\twith Hypothyroidism");break;
				case ":oca " : replacedText =("Cataract OP(+)");break;
				case ":hav " : replacedText =("s/p Hepatitis A infection");break;
				case ":hbv " : replacedText =("HBsAg(+) Carrier");break;
				case ":hcv " : replacedText =("Hepatitis C virus (HCV) chronic infection");break;
				
				case ":hcvp " : replacedText =("HCV-Ab(Pisitive) --> PCR(Negative) confirmed ");break;
				
				case ":hh " : replacedText =("Hepatic Hemagioma");break;
				case ":hc " : replacedText =("Hepatic Cyst");break;
				case ":hn " : replacedText =("Hepatic Nodule");break;
				case ":hhn " : replacedText =("Hepatic higher echoic nodule");break;
				case ":hf " : replacedText =("Fatty Liver");break;
					case ":hfmi " : replacedText =("Mild Fatty Liver");break;
					case ":hfmo " : replacedText =("Moderate Fatty Liver");break;
					case ":hfse " : replacedText =("Severe Fatty Liver");break;
				
				case ":rc " : replacedText =("Renal Cyst");break;
				case ":rs " : replacedText =("Renal Stone");break;
				case ":rse " : replacedText =("Renal Stone s/p ESWL");break;
				case ":rn " : replacedText =("Renal Nodule");break;
				case ":rih " : replacedText =("isolated hematuria");break;
				case ":rgh " : replacedText =("gross hematuria");break;
				case ":rip " : replacedText =("isolated proteinuria");break;
				
				case ":bc " : replacedText =("Breast Cyst");break;
				case ":bn " : replacedText =("Breast Nodule");break;
				case ":bnb " : replacedText =("Breast Nodule with biopsy");break;
				case ":bco " : replacedText =("s/p Breast Cancer Operation");break;
				case ":bcoc " : replacedText =("s/p Breast Cancer Operation+ ChemoTx(+)");break;
				case ":bcor " : replacedText =("s/p Breast Cancer Operation + RT(+)");break;
				case ":bcocr " : replacedText =("s/p Breast Cancer Operation \n\t:  ChemoTx(+) + RT(+)");break;
					
				case ":ins " : replacedText =("Insomnia");break;
				case ":epi " : replacedText =("Epigastric pain");break;
				case ":dys " : replacedText =("Dysuria and frequency");break;
				case ":ind " : replacedText =("Epigastric pain and Indigestion");break;
				case ":dir " : replacedText =("Diarrhea");break;
				case ":con " : replacedText =("Constipation");break;
				case ":cov " : replacedText =("COVID-19 PCR (+)");break;
					case ":covc " : replacedText =("s/p COVID-19 PCR (+) without complications [   ]");break;
					case ":covs " : replacedText =("s/p COVID-19 PCR (+) with complications [   ]");break;
				
				case ":ver " : replacedText =("Vertigo");break;
				case ":hea " : replacedText =("Headache");break;
				case ":wei " : replacedText =("Weight loss [   ] kg");break;
				case ":weig " : replacedText =("Weight gain [   ] kg");break;
				case ":eas " : replacedText =("Easy fatigue");break;
				case ":obe " : replacedText =("Obesity");break;
				case ":obec " : replacedText =("Central Obesity");break;
				
				case ":gla " : replacedText =("Glaucoma(+)");break;
				case ":cat " : replacedText =("Cataract(+)");break;
				case ":cato " : replacedText =("Cataract operation (+) [   ]");break;
				
				case ":ida " : replacedText =("Iron Deficiency Anemia");break;
				case ":leu " : replacedText =("Leukocytopenia");break;
				case ":thr " : replacedText =("Thrombocytopenia");break;
				case ":got " : replacedText =("GOT/GPT/GGT elevation");break;
				case ":afp " : replacedText =("AFP elevation");break;
				case ":ca1 " : replacedText =("CA19-9 elevation");break;
				case ":her " : replacedText =("Herpes Zoster");break;
				
				case ":uti " : replacedText =("Urinary Tract Infection");break;
				case ":uri " : replacedText =("Upper Respiratory Infection");break;
				case ":gou " : replacedText =("Gout");break;
				case ":dis " : replacedText =("HIVD : herniated intervertebral disc");break;
				
				case ":dep " : replacedText =("Depression");break;
				case ":anx " : replacedText =("Anxiety disorder");break;
				case ":cog " : replacedText =("Cognitive Disorder");break;
				// ----------------------------------------------이찬주원장님
				case ":pa " : replacedText =("s/p Bronchial Asthma");break;
				case ":pc " : replacedText =("Chronic Cough");break;
				case ":pp " : replacedText =("Pneumonia");break;
				case ":pn " : replacedText =("s/p Pulmonary Nodule");break;
				case ":pt " : replacedText =("s/p Pulmonary Tuberculosis");break;
				case ":ntm " : replacedText =("NTM : Nontuberculous Mycobacterial Pulmonary Disease");break;
				
				case ":gr " : replacedText =("GDS RC");break;
				case ":grr " : replacedText =("GDSRC Result Consultation");break;
				case ":gg " : replacedText =("공단검진");break;
				case ":ggr " : replacedText =("공단검진 결과상담");break;
				case ":go " : replacedText =("Other clinic RC result");break;
				case ":gor " : replacedText =("Other clinic result consultation");break;

				default: System.out.println(" ReInsert disease code please ...  : ");break;
				}
		return replacedText;
	}
////------------------------------------------------
}
