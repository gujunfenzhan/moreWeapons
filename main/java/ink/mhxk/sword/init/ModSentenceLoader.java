package ink.mhxk.sword.init;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Creative by GoldMain on 2019/11/17
 */
public class ModSentenceLoader {
    public static final File file = new File("config/sentence.txt");
    public static List<String> sentences = new ArrayList<>();
    public ModSentenceLoader() {
        try {
            if (!file.exists()) {
                file.createNewFile();
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        init();
    }
    public void init(){
        if(!file.exists())return;
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String str = null;
            while((str = br.readLine())!=null){
                sentences.add(str);
            }
            br.close();
        }catch (Exception e){
            e.printStackTrace();
        }


    }
}
