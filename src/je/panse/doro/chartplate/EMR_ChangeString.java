package je.panse.doro.chartplate;

public class EMR_ChangeString {

	public static String EMR_ChangeString(String lines) {
		    if (lines.contains(":(")) {
		        lines = EMR_ChangeStringCC.EMR_ChangeString_abr(lines); // Call the method from the EMR_ChangeString class to change the string
		    }
		    else if (lines.contains(":>")) {
		        lines = EMR_ChangeStringCC.EMR_ChangeString_Px(lines); // Call the method from the EMR_ChangeString class to change the string
		    }
		
		
		String outputText = lines;
		outputText = outputText.replace(":d ", "diabetes mellitus");
		outputText = outputText.replace(":dr ", "DM with retinopathy");
		outputText = outputText.replace(":dn ", "DM with Nephropathy");
		outputText = outputText.replace(":dnp ", "DM with Peripheral Neuropathy");
		outputText = outputText.replace(":dna ", "DM with Autonomic Neuropathy");
		outputText = outputText.replace(":dp ", "Prediabetes");
		outputText = outputText.replace(":dg ", "Gestational Diabetes Mellitus");
		outputText = outputText.replace(":c ", "Hypercholesterolemia F/U");
		outputText = outputText.replace(":fd ", "Diabetes mellitus  F/U");
		outputText = outputText.replace(":ft ", "Hypertension F/U");
		outputText = outputText.replace(":fc ", "Hypercholesterolemia F/U");
		outputText = outputText.replace(":fctg ", "HyperTriGlyceridemia F/U");
		outputText = outputText.replace(":fte ", "Hyperthyroidism F/U");
		outputText = outputText.replace(":fto ", "Hypothyroidism F/U");
		outputText = outputText.replace(":fnti ", "Non-Thyroidal Illness F/U");
		outputText = outputText.replace(":ftep ", "Hyperthyroidism with Pregnancy [   ]  weeks F/U");
		outputText = outputText.replace(":ftop ", "Hypothyroidism with Pregnancy [   ]  weeks F/U");
		outputText = outputText.replace(":do ", "DM without complications");
		outputText = outputText.replace(":drn ", "DM with Retinopathy \n\t: Non-proliferative diabetic retinopathy");
		outputText = outputText.replace(":drp ", "DM with Retinopathy \n\t: Proliferative diabetic retinopathy");
		outputText = outputText.replace(":drm ", "DM with Retinopathy \n\t: Macular edema");
		outputText = outputText.replace(":dnm ", "DM with Nephropathy with microalbuminuria");
		outputText = outputText.replace(":dnc ", "DM with Nephropathywith CRF");
		outputText = outputText.replace(":da ", "DM with Autonomic Neuropathy");
		outputText = outputText.replace(":pd ", "Prediabetes");
		outputText = outputText.replace(":pg ", "Gestational Diabetes Mellitus");
		outputText = outputText.replace(":t ", "Hypertension");
		outputText = outputText.replace(":ctg ", "HyperTriGlyceridemia");
		outputText = outputText.replace(":te ", "Hyperthyroidism : Graves' disease");
		outputText = outputText.replace(":to ", "Hypothyroidism : Hashimoto's thyroiditis");
		outputText = outputText.replace(":ts ", "Subacute Thyroiditis");
		outputText = outputText.replace(":tt ", "c/w Chronic Thyroiditis on USG");

		outputText = outputText.replace(":tn ", "Thyroid nodule")
                .replace(":tc ", "Thyroid cyst")
                .replace(":tsg ", "Simple Goiter")
                .replace(":at ", "Atypical Chest pain")
                .replace(":ap ", "Angina Pectoris")
                .replace(":aps ", "Angina Pectoris with stent insertion")
                .replace(":omi ", "Old Myocardial Infarction")
                .replace(":ami ", "Acute Myocardial Infarction")
                .replace(":amis ", "Acute Myocardial Infarction with stent insertion")
                .replace(":as ", "Artherosclerosis Carotid artery")
                .replace(":asa ", "Artherosclerosis Carotid artery and Aorta")
                .replace(":af ", "Atrial Fibrillation")
                .replace(":afr ", "Atrial Fibrillation with RVR")
                .replace(":afl ", "Atrial Flutter")
                .replace(":pvc ", "PVC  Premature Ventricular Contractions")
                .replace(":apc ", "APC  atrial premature complexes")
                .replace(":gre ", "Reflux esophagitis")
                .replace(":gcag ", "Chronic Atrophic Gastritis")
                .replace(":gcsg ", "Chronic Superficial Gastritis")
                .replace(":geg ", "r/o Erosive Gastritis")
                .replace(":gibs ", "r/o Irritable Bowel Syndrome")
                .replace(":ggil ", "Gilbert's syndrome")
                .replace(":gcon ", "Severe Constipation")
                .replace(":ctg ", "HyperTriGlyceridemia")
                .replace(":te ", "Hyperthyroidism : Graves' disease")
                .replace(":to ", "Hypothyroidism : Hashimoto's thyroiditis")
                .replace(":ts ", "Subacute Thyroiditis");
		outputText = outputText.replace(":nti ", "Non-Thyroidal Illness")
                .replace(":tep ", "Hyperthyroidism with Pregnancy [ ] weeks")
                .replace(":top ", "Hypothyroidism with Pregnancy [ ] weeks")
                .replace(":tco ", "Papillary Thyroid Cancer OP(+)\n\tHypothyroidism")
                .replace(":tcor ", "Papillary Thyroid Cancer OP(+) RAI Tx(+)\n\tHypothyroidism")
                .replace(":sos ", "Severe Osteoporosis")
                .replace(":os ", "Osteoporosis")
                .replace(":ospe ", "Osteopenia")
                .replace(":cp ", "Colonic Polyp")
                .replace(":cpm ", "Colonic Polyps multiple")
                .replace(":cps ", "Colonic Polyp single")
                .replace(":cd ", "Colonic diverticulum")
                .replace(":gp ", "GB polyp")
                .replace(":gs ", "GB stone")
                .replace(":ggp ", "Gastric Polyp")
                .replace(":oc ", "s/p Cholecystectomy d/t GB stone")
                .replace(":oa ", "s/p Appendectomy")
                .replace(":occ ", "s/p Colon cancer op(+)")
                .replace(":ogc ", "s/p Gastric cancer cancer op(+)")
                .replace(":oh ", "s/p TAH : Total Abdominal Hysterectomy")
                .replace(":oho ", "s/p TAH with BSO")
                .replace(":bph ", "BPH")
                .replace(":op ", "Prostate cancer operation(+)")
                .replace(":ob ", "s/p Breast Cancer Operation")
                .replace(":ot ", "Papillary Thyroid Cancer OP(+)\n\twith Hypothyroidism")
                .replace(":oca ", "Cataract OP(+)")
                .replace(":hav ", "s/p Hepatitis A infection")
                .replace(":hbv ", "HBsAg(+) Carrier")
                .replace(":hcv ", "Hepatitis C virus (HCV) chronic infection")
                .replace(":hcvp ", "HCV-Ab(Positive) --> PCR(Negative) confirmed")
                .replace(":hh ", "Hepatic Hemangioma")
                .replace(":hc ", "Hepatic Cyst")
                .replace(":hn ", "Hepatic Nodule")
                .replace(":hhn ", "Hepatic higher echoic nodule")
                .replace(":hf ", "Fatty Liver")
                .replace(":hfmi ", "Mild Fatty Liver")
                .replace(":hfmo ", "Moderate Fatty Liver")
                .replace(":hfse ", "Severe Fatty Liver")
                .replace(":rc ", "Renal Cyst")
                .replace(":rs ", "Renal Stone")
                .replace(":rse ", "Renal Stone s/p ESWL")
                .replace(":rn ", "Renal Nodule")
                .replace(":rih ", "isolated hematuria")
                .replace(":rgh ", "gross hematuria")
                .replace(":rip ", "isolated proteinuria")
                .replace(":bc ", "Breast Cyst")
                .replace(":bn ", "Breast Nodule")
                .replace(":bnb ", "Breast Nodule with biopsy");

                outputText = outputText.replace(":bco ", "s/p Breast Cancer Operation")
                .replace(":bcoc ", "s/p Breast Cancer Operation+ ChemoTx(+)")
                .replace(":bcor ", "s/p Breast Cancer Operation + RT(+)")
                .replace(":bcocr ", "s/p Breast Cancer Operation \n\t:  ChemoTx(+) + RT(+)")
                
                .replace(":ins ", "Insomnia")
                .replace(":epi ", "Epigastric pain")
                .replace(":dys ", "Dysuria and frequency")
                .replace(":ind ", "Epigastric pain and Indigestion")
                .replace(":dir ", "Diarrhea")
                .replace(":con ", "Constipation")
                
                .replace(":cov ", "COVID-19 PCR (+)")
                .replace(":covc ", "s/p COVID-19 PCR (+) without complications [   ]")
                .replace(":covs ", "s/p COVID-19 PCR (+) with complications [   ]")
                
                .replace(":ver ", "Vertigo")
                .replace(":hea ", "Headache")
                .replace(":wei ", "Weight loss [   ] kg")
                .replace(":weig ", "Weight gain [   ] kg")
                .replace(":eas ", "Easy fatigue")
                .replace(":obe ", "Obesity")
                .replace(":obec ", "Central Obesity")
                .replace(":gla ", "Glaucoma(+)")
                .replace(":cat ", "Cataract(+)")
                .replace(":cato ", "Cataract operation (+) [   ]")
                .replace(":ida ", "Iron Deficiency Anemia")
                .replace(":leu ", "Leukocytopenia")
                .replace(":thr ", "Thrombocytopenia")
                .replace(":got ", "GOT/GPT/GGT elevation")
                
                .replace(":afp ", "AFP elevation")
                .replace(":ca1 ", "CA19-9 elevation")
                .replace(":her ", "Herpes Zoster")
                .replace(":uti ", "Urinary Tract Infection")
                .replace(":uri ", "Upper Respiratory Infection")
                .replace(":gou ", "Gout")
                .replace(":dis ", "HIVD : herniated intervertebral disc")
                .replace(":dep ", "Depression")
                .replace(":anx ", "Anxiety disorder")
                .replace(":cog ", "Cognitive Disorder")
                .replace(":pa ", "s/p Bronchial Asthma")
                .replace(":pc ", "Chronic Cough")
                .replace(":pp ", "Pneumonia")
                .replace(":pn ", "s/p Pulmonary Nodule")
                .replace(":pt ", "s/p Pulmonary Tuberculosis")
                .replace(":ntm ", "NTM : Nontuberculous Mycobacterial Pulmonary Disease")
                .replace(":gr ", "GDS RC")
                .replace(":grr ", "GDSRC Result Consultation")
                .replace(":gg ", "공단검진")
                .replace(":ggr ", "공단검진 결과상담")
                .replace(":rr ", "Other clinic RC and Lab result consultation");
		
		return outputText;
	}
////------------------------------------------------
}
