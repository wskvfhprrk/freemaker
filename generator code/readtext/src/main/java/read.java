import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class read {
    public static void main(String[] args) {
        try {
            //文件路径，例子中的1.txt，题目是以#### 四号标题开始的
            String filePath = "C:\\Users\\Administrator\\IdeaProjects\\freemaker\\generator code\\readtext\\src\\main\\java\\1.txt";
//            String filePath = "C:\\Users\\Administrator\\Desktop\\1.txt";
            File file = new File(filePath);
            List<Object> textList = new ArrayList<>();
            if (file.isFile() && file.exists()) {
                InputStreamReader isr = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader br = new BufferedReader(isr);
                String lineTxt = null;
                while ((lineTxt = br.readLine()) != null) {
                    //在四号标题开始的——根据需要可以更改
                    String prefix="#### ";
                    if(lineTxt.startsWith(prefix)){
                        textList.add(lineTxt.replaceAll(prefix,""));
                    }
                }
                br.close();
                List<List<Object>> datalist=new ArrayList<>();
                for (int i = 0; i < textList.size(); i=i+3) {
                    if(textList.size()-i>=3) {
                        datalist.add(Arrays.asList(textList.get(i), textList.get(i + 1), textList.get(i+2)));
                    }else if(textList.size()-i==1){
                        datalist.add(Arrays.asList(textList.get(i)));
                    }else if(textList.size()-i==2){
                        datalist.add(Arrays.asList(textList.get(i), textList.get(i + 1)));
                    }else if(textList.size()-i==3){
                        datalist.add(Arrays.asList(textList.get(i), textList.get(i + 1), textList.get(i+2)));
                    }
                }
                csv.createCSVFile(datalist, "C:\\Users\\Administrator\\Desktop\\", "题目");
            } else {
                System.out.println("文件不存在!");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
