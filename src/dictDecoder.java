import javax.xml.bind.DatatypeConverter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Map;

public class dictDecoder {
    public static String encodeDict(Map<String, Double[]> decoded){
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(decoded);
            oos.flush();
            oos.close();
            bos.close();
            byte[] byteData = bos.toByteArray();
            String serial= DatatypeConverter.printBase64Binary(byteData);
            return serial;
        }catch (java.io.IOException ex) {
            return null;
        }
    }
    public static Map<String, Double[]> decodeDict(String dict){
        try {
            byte[] byteData_reverse=DatatypeConverter.parseBase64Binary(dict);
            ByteArrayInputStream bais = new ByteArrayInputStream(byteData_reverse);
           return (Map<String,Double[]>) new ObjectInputStream(bais).readObject();
        }catch (Exception ex){
            return null;
        }
    }
}
