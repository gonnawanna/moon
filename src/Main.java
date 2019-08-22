import org.apache.log4j.BasicConfigurator;

import java.util.ArrayList;
import java.util.List;

//-Xmx512m

public class Main {
    public static void main(String[] args) {

        //BasicConfigurator.configure();

        ParserPdf parser = new ParserPdf();

        String text1type1 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\док\\20060500s00003p168.pdf")
                + parser.getText("C:\\Users\\Ir\\Downloads\\data\\док\\Bennett_et_al-2013-Influenza_and_Other_Respiratory_Viruses.pdf")
                + parser.getText("C:\\Users\\Ir\\Downloads\\data\\док\\kjod-49-246.pdf")
                + parser.getText("C:\\Users\\Ir\\Downloads\\data\\док\\Mart--Carvajal_et_al-2015-Cochrane_Database_of_Systematic_Reviews.pdf")
                + parser.getText("C:\\Users\\Ir\\Downloads\\data\\док\\OAMJMS-7-1896.pdf")
                + parser.getText("C:\\Users\\Ir\\Downloads\\data\\док\\pnas-0503723102.pdf");
        String text1type2 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\худ\\Richard_Adams_-_Watership_Down.pdf");
        String text2type2 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\худ\\The_Adventures_of_Tom_Sawyer-Mark_Twain.pdf");
        String text3type2 = parser.getText("C:\\Users\\Ir\\Downloads\\data\\худ\\The_Perks_of_Being_a_Wallflower.pdf");

        List<String> texts = new ArrayList<>();
        texts.add(text1type1);
        texts.add(text1type2);
        texts.add(text2type2);
        texts.add(text3type2);

        TFIDFProcessor tfidf = new TFIDFProcessor();
        tfidf.calculateTFIDF(texts);

    }
}
