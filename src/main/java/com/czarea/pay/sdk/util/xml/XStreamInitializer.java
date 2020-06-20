package com.czarea.pay.sdk.util.xml;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.core.util.QuickWriter;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
import com.thoughtworks.xstream.io.xml.XppDriver;
import com.thoughtworks.xstream.security.NullPermission;
import com.thoughtworks.xstream.security.PrimitiveTypePermission;
import java.io.Writer;

/**
 * 生成XStream工具
 *
 * @author zhouzx
 */
public class XStreamInitializer {

    public static XStream getInstance() {

        XStream xStream = new XStream(new XppDriver() {
            @Override
            public HierarchicalStreamWriter createWriter(Writer out) {
                return new PrettyPrintWriter(out, getNameCoder()) {
                    protected String PREFIX_CDATA = "<![CDATA[";
                    protected String SUFFIX_CDATA = "]]>";
                    protected String PREFIX_MEDIA_ID = "<MediaId>";
                    protected String SUFFIX_MEDIA_ID = "</MediaId>";

                    @Override
                    protected void writeText(QuickWriter writer, String text) {
                        if (text.startsWith(this.PREFIX_CDATA)
                            && text.endsWith(this.SUFFIX_CDATA)) {
                            writer.write(text);
                        } else if (text.startsWith(this.PREFIX_MEDIA_ID)
                            && text.endsWith(this.SUFFIX_MEDIA_ID)) {
                            writer.write(text);
                        } else {
                            super.writeText(writer, text);
                        }

                    }

                    @Override
                    public String encodeNode(String name) {
                        return name;// 防止将_转换成__
                    }
                };
            }
        });
        xStream.ignoreUnknownElements();
        xStream.setMode(XStream.NO_REFERENCES);
        xStream.addPermission(NullPermission.NULL);
        xStream.addPermission(PrimitiveTypePermission.PRIMITIVES);
        return xStream;
    }

}
