package com.czarea.pay.sdk.test.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.junit.Test;

import com.czarea.pay.sdk.test.model.Case1;
import com.czarea.pay.sdk.test.model.Case2;
import com.czarea.pay.sdk.test.model.Demo;
import com.czarea.pay.sdk.test.model.Example;
import com.thoughtworks.xstream.XStream;

/**
 * XStream虽然是线程安全的，但是如果是不通结构共用会出现问题
 *
 * @author zhouzx
 */
public class XStreamTest {
    @SuppressWarnings("unused")
    private static volatile XStream stream = null;

    public static XStream getStream() {
        return new XStream();
        /*
         * synchronized (XStreamTest.class) { if (stream == null) { stream = new XStream(); } return
         * stream; }
         */
    }

    @Test
    public void testDemo1() throws Exception {
        Demo d1 = new Demo();
        d1.setElement("test1");
        Demo d2 = new Demo();
        d2.setElement("test2");
        List<Demo> demos = new LinkedList<Demo>();
        demos.add(d1);
        demos.add(d2);
        Case1 c1 = new Case1();
        c1.setDemos(demos);
        c1.setDate(new Date());
        FileOutputStream out1 = new FileOutputStream(new File("d:\\test1.xml"));
        try {
            XStreamTest.toXML(c1, Case1.class, out1);
        } finally {
            try {
                out1.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Test
    public void demo2() throws FileNotFoundException {
        Example e1 = new Example();
        e1.setElement("test1");
        Example e2 = new Example();
        e2.setElement("test2");
        List<Example> examples = new LinkedList<Example>();
        examples.add(e1);
        examples.add(e2);
        Case2 c2 = new Case2();
        c2.setExamples(examples);
        c2.setDate(new Date());
        FileOutputStream out2 = new FileOutputStream(new File("d:\\test2.xml"));
        try {
            XStreamTest.toXML(c2, Case2.class, out2);
        } finally {
            try {
                out2.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static <T> void toXML(Object obj, Class<T> clazz, OutputStream out) {
        XStream stream = XStreamTest.getStream();
        stream.processAnnotations(clazz);
        stream.toXML(obj, out);
        return;
    }

    @Test
    public void demo3() throws FileNotFoundException {
        File f1 = new File("d:\\test1.xml");
        FileInputStream fis1 = new FileInputStream(f1);
        XStreamTest.fromXML(fis1, Case1.class);
        File f2 = new File("d:\\test2.xml");
        FileInputStream fis2 = new FileInputStream(f2);
        XStreamTest.fromXML(fis2, Case2.class);
    }

    public static <T> T fromXML(InputStream in, Class<T> clazz) {
        XStream stream = XStreamTest.getStream();
        stream.processAnnotations(clazz);
        Object obj = stream.fromXML(in);
        try {
            return clazz.cast(obj);
        } catch (ClassCastException e) {
            return null;
        }
    }
}
