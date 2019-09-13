import org.apache.log4j.BasicConfigurator;

import java.util.ArrayList;
import java.util.List;

//-Xmx512m

public class Main {
    public static void main(String[] args) {

        ParserPdf parser = new ParserPdf();
/*
        String text1type1 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\док\\20060500s00003p168.pdf")
                + parser.getText("C:\\Users\\Ir\\Downloads\\data\\док\\Bennett_et_al-2013-Influenza_and_Other_Respiratory_Viruses.pdf")
                + parser.getText("C:\\Users\\Ir\\Downloads\\data\\док\\kjod-49-246.pdf")
                + parser.getText("C:\\Users\\Ir\\Downloads\\data\\док\\Mart--Carvajal_et_al-2015-Cochrane_Database_of_Systematic_Reviews.pdf")
                + parser.getText("C:\\Users\\Ir\\Downloads\\data\\док\\OAMJMS-7-1896.pdf")
                + parser.getText("C:\\Users\\Ir\\Downloads\\data\\док\\pnas-0503723102.pdf");
        String text1type2 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\худ\\Richard_Adams_-_Watership_Down.pdf");
        String text2type2 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\худ\\The_Adventures_of_Tom_Sawyer-Mark_Twain.pdf");
        String text3type2 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\худ\\The_Perks_of_Being_a_Wallflower.pdf");
        String text1type3 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\недок\\1-s2.0-S073510971303088X-main.pdf");
        String text2type3 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\недок\\1-s2.0-S0166354216306465-main.pdf");
        String text3type3 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\недок\\1-s2.0-S1347861318301804-main.pdf");
        String text4type3 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\недок\\41598_2019_Article_47329.pdf");
        String text5type3 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\недок\\e8d4a50bbea0518509e97ac6a1aa6bb1789c.pdf");
        String text6type3 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\недок\\Lu2019_Article_CytotoxicCycloartaneTriterpeno.pdf");
        String text7type3 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\недок\\s10517-018-4104-z.pdf");

        String text1type1 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\техн\\Norman_Myetloff_Iskusstvo_programmirovaniya_na_R(z-lib.org).pdf")
                + parser.getText("C:\\Users\\Ir\\Downloads\\data\\техн\\Riza_S._i_dr._Spark_dlya_professionalov._Sovreme(z-lib.org).pdf")
                + parser.getText("C:\\Users\\Ir\\Downloads\\data\\техн\\dissert_tushkanova.pdf")
                + parser.getText("C:\\Users\\Ir\\Downloads\\data\\техн\\Hadoop_Podrobnoe_rukovodstvo.pdf")
                + parser.getText("C:\\Users\\Ir\\Downloads\\data\\техн\\Prikladnoy_analiz_textovykh_dannykh_na_Python_-_2019.pdf");
        String text1type2 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\публ\\Maier-SHenberger_V.,_Kuker_K.;Per._s_angl._Gaid(z-lib.org).pdf");
        String text2type2 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\публ\\Gurvic_Dzhudit,_Nyudzhent_Alan,_Halper_Fern,_Kauf(z-lib.org).pdf");
        String text3type2 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\публ\\Fryenks_B.;Per._s_angl._Baranov_A._Ukroshenie_bo(z-lib.org).pdf");
        String text4type2 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\публ\\Vse_lgut_Poiskoviki_Big_Data_i_Internet_znayut_o_vas_vsyo.pdf");
*/
        String text1type1 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\публ\\Fryenks_B.;Per._s_angl._Baranov_A._Ukroshenie_bo(z-lib.org).pdf")
                + parser.getText("C:\\Users\\Ir\\Downloads\\data\\публ\\Maier-SHenberger_V.,_Kuker_K.;Per._s_angl._Gaid(z-lib.org).pdf");
        String text1type2 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\техн\\Riza_S._i_dr._Spark_dlya_professionalov._Sovreme(z-lib.org).pdf");
        String text2type2 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\техн\\Prikladnoy_analiz_textovykh_dannykh_na_Python_-_2019.pdf");
        String text3type2 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\техн\\Norman_Myetloff_Iskusstvo_programmirovaniya_na_R(z-lib.org).pdf");
        String text4type2 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\техн\\Hadoop_Podrobnoe_rukovodstvo.pdf");

        List<String> texts = new ArrayList<>();
        texts.add(text1type1);
        texts.add(text1type2);
        texts.add(text2type2);
        texts.add(text3type2);
        texts.add(text4type2);
        /*texts.add(text1type3);
        texts.add(text2type3);
        texts.add(text3type3);
        texts.add(text4type3);
        texts.add(text5type3);
        texts.add(text6type3);
        texts.add(text7type3);
        */

        TFIDFProcessor tfidf = new TFIDFProcessor();
        tfidf.calculateTFIDF(texts);

    }
}
