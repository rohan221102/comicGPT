package ucd.lrv;

import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class XMLGenerator {
        public void generateXML(NumberedList numberedList, String figure1, String figure2, String[] captions,
                        String[][] poses, String[] settings) {
                try {
                        BufferedWriter writer = new BufferedWriter(new FileWriter("output.xml"));
                        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
                        writer.write("<comic>\n");
                        writer.write("<premise>Prosef and Connor's Adventures!</premise>");
                        writer.write("<figures>\n");
                        writer.write("<figure>\n");
                        writer.write("<name>" + figure1 + "</name>\n");
                        writer.write("<appearance>male</appearance>\n");
                        writer.write("<skin>olive</skin>\n");
                        writer.write("<hair>black</hair>\n");
                        writer.write("<lips>default</lips>\n");
                        writer.write("</figure>\n");
                        writer.write("<figure>\n");
                        writer.write("<name>" + figure2 + "</name>\n");
                        writer.write("<appearance>female</appearance>\n");
                        writer.write("<skin>white</skin>\n");
                        writer.write("<hair>blonde</hair>\n");
                        writer.write("<lips>default</lips>\n");
                        writer.write("</figure>\n");
                        writer.write("</figures>\n");

                        writer.write("<panels>\n");
                        for (int i = 1; i <= numberedList.getCount(); i++) {
                                writer.write("<panel>\n");
                                writer.write("<above>" + captions[i - 1] + "</above>\n");

                                writer.write("<left>\n");
                                writer.write("<figure>\n");
                                writer.write("<name>" + figure1 + "</name>\n");
                                writer.write("<pose>" + poses[i - 1][0] + "</pose>\n");
                                writer.write("</figure>\n");
                                writer.write("<balloon status = \"speech\">\n");
                                writer.write("<content>"
                                                + numberedList.getLine(i).split("DELIMITED_HERE")[0].substring(3)
                                                + "</content>");
                                writer.write("</balloon>\n");
                                writer.write("</left>\n");

                                writer.write("<right>\n");
                                writer.write("<figure>\n");
                                writer.write("<name>" + figure2 + "</name>\n");
                                writer.write("<pose>" + poses[i - 1][1] + "</pose>\n");
                                writer.write("</figure>\n");
                                writer.write("<balloon status = \"speech\">\n");
                                writer.write("<content>"
                                                + numberedList.getLine(i).split("DELIMITED_HERE")[1]
                                                + "</content>");
                                writer.write("</balloon>\n");
                                writer.write("</right>\n");
                                writer.write("<setting>" + settings[i - 1] + "</setting>\n");
                                writer.write("</panel>\n");
                        }
                        writer.write("</panels>\n");
                        writer.write("</comic>\n");
                        writer.close();
                } catch (IOException e) {
                        e.printStackTrace();
                }
        }
}
