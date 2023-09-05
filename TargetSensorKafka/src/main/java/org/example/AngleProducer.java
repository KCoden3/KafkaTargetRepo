package org.example;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;
import java.util.Random;

public class AngleProducer {

   public static void main(String[] args) {
       String bootstrapServers = "localhost:9092";
       String topic = "NewTopic";

       Properties properties = new Properties();
       properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
       properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");
       properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringSerializer");

       Producer<String, String> producer = new KafkaProducer<>(properties);

       String angleDegrees = calculateAngle();
       String message = angleDegrees;
       ProducerRecord<String, String> record = new ProducerRecord<>(topic, null, message);

       producer.send(record);

       producer.close();
   }

   private static String calculateAngle() {

       Random rnd = new Random();

        int min = -1000;
        int max = 1000;

        int s1x = rnd.nextInt(max - min) + min;
        int s1y = rnd.nextInt(max - min) + min;
        int s2x = rnd.nextInt(max - min) + min;
        int s2y = rnd.nextInt(max - min) + min;
        int hx = rnd.nextInt(max - min) + min;
        int hy = rnd.nextInt(max - min) + min;

       // İki nokta arasındaki açıyı hesaplayın
       int deltaX = hx - s1x;
       int deltaY = hy - s1y;
       double angleRadians1 = Math.atan2(deltaY, deltaX);

       // Radyan cinsinden açıyı dereceye çevirin
       double angleDegrees1 = Math.toDegrees(angleRadians1);

       if(angleDegrees1 <= 0)
       {
           angleDegrees1 += 360;
       }

       int deltaA = hx - s2x;
       int deltaB = hy - s2y;
       double angleRadians2 = Math.atan2(deltaB, deltaA);

       double angleDegrees2 = Math.toDegrees(angleRadians2);

       if(angleDegrees2 <= 0)
           angleDegrees2 += 360;


       String Sensor1Koordinat = "S1 koordinat: (" + s1x + ", " + s1y + ")";
       String Sensor2Koordinat = "S2 koordinat: (" + s2x + ", " + s2y + ")";
       String HedefKoordinat = "H koordinat: (" + hx + ", " + hy + ")";
       String S1Kerteriz = "S1 Hedef Kerteriz: (aci)" + angleDegrees1;
       String S2Kerteriz = "S2 Hedef Kerteriz: (aci)" + angleDegrees2;

       String messages = Sensor1Koordinat + "," +  Sensor2Koordinat + "," + HedefKoordinat + "," + S1Kerteriz + "," + S2Kerteriz;

       return messages;
   }
}

