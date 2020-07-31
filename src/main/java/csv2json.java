import com.alibaba.fastjson.JSONArray;

/**
 * csv文件转json
 *
 * @author chb
 * @date 20200414
 * @update 修改可以读取分行的文件
 */
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class csv2json {

    public static String[] split(String splitStr) {
        String[] ret = {};
        //List list = new ArrayList();
        JSONArray list = new JSONArray();
        String tempStr=splitStr;

        while(tempStr.length() > 0) {
            try {
                if (tempStr.startsWith("\"")) {
                    tempStr = tempStr.substring(1);
                    if (tempStr.indexOf("\"") > -1) {
                        list.add(tempStr.substring(0, tempStr.indexOf("\"")));
                        tempStr = tempStr.substring(tempStr.indexOf("\"") + 1);
                        if (tempStr.startsWith(",\"") || tempStr.startsWith(","))
                            tempStr = tempStr.substring(1);
                    } else {
                        if (tempStr.indexOf(",") < 0) {
                            list.add(tempStr);
                            break;
                        } else {
                            list.add(tempStr.substring(0, tempStr.indexOf(",")));
                            tempStr = tempStr.substring(tempStr.indexOf(",") + 1);
                        }
                    }
                } else {
                    if (tempStr.indexOf(",") > -1) {
                        list.add(tempStr.substring(0, tempStr.indexOf(",")));
                        tempStr = tempStr.substring(tempStr.indexOf(",") + 1);
                    } else {
                        list.add(tempStr);
                        break;
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (splitStr.endsWith(",")){
            list.add("");
        }
        ret = (String[])list.toArray(ret);
        return ret;
    }

    public static void main(String[]args) throws Exception{
        String fileName="d:\\csv\\c_register.csv";
        File file= new File(fileName);
        FileInputStream in = new FileInputStream(file);
        //InputStreamReader reader= new InputStreamReader(in,"ISO-8859-1");
        InputStreamReader reader= new InputStreamReader(in,"GB2312");
        BufferedReader bread=new BufferedReader(reader);
        String line=null;
        String head="";
        String[] fields=null;

        String jsonFileName="d:\\csv\\c_register.json";
        File jsonFile = new File(jsonFileName);
        FileOutputStream out = new FileOutputStream(jsonFile);
        OutputStreamWriter writer = new OutputStreamWriter(out);//UTF-8
        int index = 0;
        String recordLine = "";
        while((line=bread.readLine())!=null){
            index++;
            //if (index == 7439) {
            if (line.indexOf("F213223D82864576AD5213A9A9250A02") > -1){
                System.out.println("32");
            }
            System.out.println(line);
            if ("".equals(head)) {
                head = line;
                //fields = head.replaceAll("\"","").split(",");
                fields = head.replaceAll("\"","").split(",");
                continue;
            }
            //String[] data = line.split(",");
            try {
                String[] data = split(line);
                //String[] data = line.split(",");
                if (data.length < fields.length) {
                    recordLine = line + bread.readLine();
                    data = split(recordLine);
                }
                String record = "{\"";
                for (int i = 0; i < fields.length; i++) {
                    if (i == fields.length - 1)
                        record += fields[i] + "\":\"" + data[i] + "\"";
                    else
                        record += fields[i] + "\":\"" + data[i] + "\",\"";
                }
                record += "}";
                writer.write(record);
                writer.write("\n");
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("line:"+ line);
            }
        }
        if (writer!=null) {
            writer.flush();
            writer.close();
        }
        if (out!=null) {
            out.flush();
            out.close();
        }
        //String line="";
        //byte[] data = new byte[in.available()];
        //long len = in.read(line);
        //in.read(data);
        reader.close();
        in.close();


        //String[] s=

        //byte[] b = new byte[]
        //out.write()

//        String student_csv=[];
//        student_json=[];
//        with open("student.csv",mode='r',encoding='ansi')as student_csv_file_name:
//        read_object=csv.reader(student_csv_file_name);  #用csv模块自带的函数来完成读写操作
//        with open("student_csv转json.json",mode='w',encoding='ansi')as student_json_file_name:
//        for i in read_object:
//        student_csv.append(i);
//        key=student_csv[0];
//        for i in range(1,len(student_csv)):
//        student_json_temp=[];
//        for j in zip(key,student_csv[i]):
//        k=":".join(j);
//        student_json_temp.append(k);
//        student_json.append(student_json_temp);
//        json.dump(student_json,student_json_file_name);
    }
}
