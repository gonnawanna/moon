import java.util.*;

import org.apache.spark.ml.feature.*;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;



public class TFIDFProcessor {
    public void calculateTFIDF(List<String> texts){

        SparkSession spark = SparkSession
                .builder()
                .appName("TFIDF")
                .master("local")
                .getOrCreate();

        // documents corpus
        List<Row> data = new ArrayList<>();
        data.add(RowFactory.create(0.0, texts.get(0)));
        for (int i = 1; i <texts.size(); i++){
            data.add(RowFactory.create(1.0, texts.get(i)));
        }

        StructType schema = new StructType(new StructField[]{
                new StructField("label", DataTypes.DoubleType, false, Metadata.empty()),
                new StructField("sentence", DataTypes.StringType, false, Metadata.empty())
        });

        // import data with the schema
        Dataset<Row> sentenceData = spark.createDataFrame(data, schema);

        RegexTokenizer regexTokenizer = new RegexTokenizer()
                .setInputCol("sentence")
                .setOutputCol("words")
                .setPattern("\\d|\\w+")
                .setGaps(false)
                .setToLowercase(true)
                .setMinTokenLength(3);

        // break sentence to words
        //Tokenizer tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("tokens");
        Dataset<Row> wordsData = regexTokenizer.transform(sentenceData);



        CountVectorizerModel cvModel = new CountVectorizer()
                .setInputCol("words")
                .setOutputCol("rawFeatures")
                .setVocabSize(27000)
                .setMinTF(3)
                .fit(wordsData);

        // transform words to feature vector
        Dataset<Row> featurizedData = cvModel.transform(wordsData);
        featurizedData.show();

        System.out.println("TF векторы\n---");
        for(Row row:featurizedData.collectAsList()){
            System.out.println(row.get(3));
        }

        // IDF is an Estimator which is fit on a dataset and produces an IDFModel
        IDF idf = new IDF().setInputCol("rawFeatures").setOutputCol("features");
        IDFModel idfModel = idf.fit(featurizedData);

        // The IDFModel takes feature vectors (generally created from HashingTF or CountVectorizer) and scales each column
        Dataset<Row> rescaledData = idfModel.transform(featurizedData);

        System.out.println("TF-IDF векторы\n---");
        for(Row row:rescaledData.collectAsList()){
            System.out.println(row.get(4));
        }

        System.out.println("TF-IDF вектор для первого класса\n---");
        Row row = rescaledData.collectAsList().get(0);
        String resultVector = row.get(4).toString();
        System.out.println(resultVector);

        int firstIndex = resultVector.indexOf("[") + 1;
        int secondIndex = resultVector.indexOf("],[");
        int thirdIndex = resultVector.length() - 2;

        String[] arrayOfIds = getValuesFromVector(resultVector, firstIndex, secondIndex);
        String[] arrayOfTfidfs = getValuesFromVector(resultVector, secondIndex + 3, thirdIndex);

        Map<Double, Integer> mapa = createDescendingTreeMap(arrayOfTfidfs, arrayOfIds);

        String[] bag = cvModel.vocabulary();
        printTop(mapa, bag);

        spark.close();

    }

    private void printTop(Map<Double, Integer> mapa, String[] vocabulary){
        System.out.println("Рейтинг слов, идентифицирующих данные первого класса\n---");
        int frequencyIndex = 1;
        for (Map.Entry<Double,Integer> entry : mapa.entrySet()) {
            int i = entry.getValue();
            System.out.println(frequencyIndex + " - " + vocabulary[i]);
            frequencyIndex ++;
        }
    }

    private Map<Double, Integer> createDescendingTreeMap(String[] arrayOfTfidfs, String[] arrayOfIds){

        Map<Double,Integer> mapa = new TreeMap<>(Collections.reverseOrder());

        for(int i = 0; i < arrayOfTfidfs.length; i++){
            Double tfidf = Double.valueOf(arrayOfTfidfs[i]);
            Integer id = Integer.valueOf(arrayOfIds[i]);
            mapa.put(tfidf, id);
        }
        System.out.println(mapa);
        return mapa;
    }

    private String[] getValuesFromVector(String vector, int from, int to){

        String stringOfValues = vector.substring(from, to);
        System.out.println(stringOfValues);
        return stringOfValues.split(",");

    }


}
