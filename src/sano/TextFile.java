package sano;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.io.File;


/**
 *
 * @author HowariS
 */
public class TextFile {

    String name = "";
    InputStream is = null;
    BufferedReader buf = null;
    List<String> content = new ArrayList<>();
    FileWriter fw = null;
    PrintWriter os = null;
    StringBuilder sb = null;

    public enum Type {
        READ(1),
        WRITE(2),
        APPEND(2);

        private final int TypeCode;

        Type(int typeCode) {
            this.TypeCode = typeCode;
        }

        public int gettypeCode() {
            return this.TypeCode;
        }
    }
    public Type type;

    public TextFile() throws IOException {

    }

    public TextFile(String fileName, Type t) throws IOException {
        open(fileName, t);
    }

    public void open(String fileName, Type t) throws IOException {
        type = t;
        name = fileName;

        if (null != t) {
            switch (t) {
                case READ:
                    is = new FileInputStream(fileName);
                    buf = new BufferedReader(new InputStreamReader(is));
                    break;
                case WRITE:
                    fw = new FileWriter(fileName, false);
                    os = new PrintWriter(fw);
                    break;
                case APPEND:
                    fw = new FileWriter(fileName, true);
                    os = new PrintWriter(fw);
                    break;
                default:
                    break;
            }
        }

    }

    public String readLine() throws IOException {
        return buf.readLine();
    }

    public List<String> readAllLines() throws IOException {
        buf = new BufferedReader(new InputStreamReader(is));
        String line = buf.readLine();
        while (line != null) {
            content.add(line);
            line = buf.readLine();
        }
        return content;
    }

    public StringBuilder readAllText() throws IOException {
        sb = new StringBuilder();
        buf = new BufferedReader(new InputStreamReader(is));
        String line = buf.readLine();
        while (line != null) {
            sb.append(line);
            line = buf.readLine();
        }
        return sb;
    }

    public void writeLine(String line) throws IOException {
        os.println(line);
    }

    public void write(String line) throws IOException {
        os.print(line);
    }

    public String getPath() {
        File file = new File(name);
        String absolutePath = file.getAbsolutePath();
        return absolutePath;
    }

    public void close() throws IOException {
        if (null != type) {
            switch (type) {
                case READ:
                    buf.close();
                    is.close();
                    break;
                case WRITE:
                case APPEND:
                    os.close();
                    fw.close();
                    break;
                default:
                    break;
            }
        }
    }
}
