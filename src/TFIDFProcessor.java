import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.spark.ml.feature.*;
import org.apache.spark.ml.linalg.SparseVector;
import org.apache.spark.sql.*;
import org.apache.spark.sql.sources.In;
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

        // documents corpus. each row is a document.
        List<Row> data = Arrays.asList(
                RowFactory.create(0.0, texts.get(0)),
                RowFactory.create(1.0, texts.get(1)),
                RowFactory.create(1.0, texts.get(2)),
                RowFactory.create(1.0, texts.get(3))
        );

        /*List<Row> data = Arrays.asList(
                RowFactory.create(0.0, "это текст про медицину медицину медицину"),
                RowFactory.create(1.0, "у лукоморья дуб зеленый"),
                RowFactory.create(1.0, "поёт зима аукает зеленый")
        );*/

        StructType schema = new StructType(new StructField[]{
                new StructField("label", DataTypes.DoubleType, false, Metadata.empty()),
                new StructField("sentence", DataTypes.StringType, false, Metadata.empty())
        });

        // import data with the schema
        Dataset<Row> sentenceData = spark.createDataFrame(data, schema);


        // break sentence to words
        Tokenizer tokenizer = new Tokenizer().setInputCol("sentence").setOutputCol("words");
        Dataset<Row> wordsData = tokenizer.transform(sentenceData);


        // define Transformer, HashingTF
        int numFeatures = 1000000;
        /*HashingTF hashingTF = new HashingTF()
                .setInputCol("words")
                .setOutputCol("rawFeatures")
                .setNumFeatures(numFeatures);
                */

        CountVectorizerModel cvModel = new CountVectorizer()
                .setInputCol("words")
                .setOutputCol("rawFeatures")
                .setVocabSize(27000)
                .setMinTF(3)
                .fit(wordsData);

        // transform words to feature vector
        Dataset<Row> featurizedData = cvModel.transform(wordsData);
        featurizedData.show();

        System.out.println("TF vectorized data\n----------------------------------------");
        for(Row row:featurizedData.collectAsList()){
            System.out.println(row.get(3));
        }

        System.out.println(featurizedData.toJSON());

        // IDF is an Estimator which is fit on a dataset and produces an IDFModel
        IDF idf = new IDF().setInputCol("rawFeatures").setOutputCol("features");
        IDFModel idfModel = idf.fit(featurizedData);

        // The IDFModel takes feature vectors (generally created from HashingTF or CountVectorizer) and scales each column
        Dataset<Row> rescaledData = idfModel.transform(featurizedData);

        System.out.println("TF-IDF vectorized data\n----------------------------------------");
        for(Row row:rescaledData.collectAsList()){
            System.out.println(row.get(4));
        }

        Row row = rescaledData.collectAsList().get(0);
        String resultVector = row.get(4).toString();

        System.out.println(resultVector);

        int firstIndex = resultVector.indexOf("[") + 1;
        int secondIndex = resultVector.indexOf("],[");
        int thirdIndex = resultVector.length() - 2;

        String stringOfIds = resultVector.substring(firstIndex, secondIndex);
        String stringOfTfidfs = resultVector.substring(secondIndex + 3, thirdIndex);

        String[] arrayOfIds = stringOfIds.split(",");
        String[] arrayOfTfidfs = stringOfTfidfs.split(",");

        Map<Double,Integer> mapa = new TreeMap<>();

        for(int i = 0; i < arrayOfTfidfs.length; i++){
            Double tfidf = Double.valueOf(arrayOfTfidfs[i]);
            Integer id = Integer.valueOf(arrayOfIds[i]);
            mapa.put(tfidf, id);
        }
        System.out.println(mapa);

        String[] bag = cvModel.vocabulary();
        System.out.println("gagaga");
        System.out.println(bag[221]);

        /*for (String word: cvModel.vocabulary()){
            System.out.println(word);
        }


        try {
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        spark.close();

    }
}
