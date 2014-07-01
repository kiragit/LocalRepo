package readText;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReadText {
	
	//
	static List<Meisai> meisaiList = new ArrayList<Meisai>();
	
	public static void main(String[] args){
        try {
        	//保存ファイル
        	String SyukeiFilePath = "/Users/kentarokira/Desktop/Kyuuyo/txt/syukei.txt";
        	File SyukeiFile = new File(SyukeiFilePath);
        	PrintWriter pw = new PrintWriter(new BufferedWriter(new FileWriter(SyukeiFile)));

        	//給与明細の保存フォルダパス
            String path = "/Users/kentarokira/Desktop/Kyuuyo/txt";
            File dir = new File(path);
            File[] files = dir.listFiles();

            for (File file : files) {
            	
            	Meisai meisai = new Meisai();
            	Map<String,String> tmpMeisai = new HashMap<String,String>();            	
	            FileReader in = new FileReader(file);
	            BufferedReader br = new BufferedReader(in);
	            
	            //給与明細の１行
	            String line;
	            //１行を空白で分割
	            String[] parts;
	            //「名称:値」のセット
	            String tmp="";
	            //MeisaiMapにセットするため
	            String[] map = new String[2];
	            
	            //ファイルの最終行まで繰り返し
	            while ((line = br.readLine()) != null) {
	            	//支給年月の算出
	            	if(line.matches(".*賃金明細書.*")){
        					line = line.replaceAll("[\\s]+", "");
	                		map = line.split("分");
	                		String yyyymmdd = map[0].subSequence(6, 13).toString() +"/28";
	                		yyyymmdd = yyyymmdd.replaceAll("月", "");
	                		if(yyyymmdd.length()==9){
	                			yyyymmdd = yyyymmdd.replaceAll("年", "/0");
	                		}else{
	                			yyyymmdd = yyyymmdd.replaceAll("年", "/");
	                		}
	                		tmpMeisai.put(map[0].subSequence(0, 5).toString(),yyyymmdd);
	                		tmp="";
	                //項目の抽出
	            	}else if(line.matches(".*:.*")){
	                		parts = line.split("[\\s]+");
	                		tmp="";
	                		for (int i=0;i<parts.length;i++) {  
	                			if(parts[i].matches(".*:")){
	                				tmp = tmp + parts[i]+parts[i+1];
	                				tmp = tmp.replaceAll("[\\s]+", "");
	                				tmp = tmp.replaceAll("¥", "");
	                				tmp = tmp.replaceAll(",", "");
	                				tmp = tmp.replaceAll(":", ",");
	                				map = tmp.split(",");
	                				tmpMeisai.put(map[0],map[1]);
	                				tmp="";
	                			}else if(parts[i].matches(".*:.*")){
	                				tmp = tmp + parts[i];
	                				tmp = tmp.replaceAll("[\\s]+", "");
	                				tmp = tmp.replaceAll("¥", "");
	                				tmp = tmp.replaceAll(",", "");
	                				tmp = tmp.replaceAll(":", ",");
	                				map = tmp.split(",");
	                				tmpMeisai.put(map[0],map[1]);
	                				tmp="";
	                			}else{
	                				tmp = parts[i];
	                			}
	                		}
	                	}            	
	            }
            br.close();
            in.close();
            
            //テキストから読み込んだ値を、明細にマッピング
            for(String tmpkey : tmpMeisai.keySet()){
            	for(String key : Meisai.meisaiList){
            		if(key.indexOf("持株会社奨励金")>=0 && tmpkey.indexOf("持株会社奨励金")>=0 ){
            			System.out.print("");
            		}
            		if(tmpkey.indexOf(key)>=0){
            			meisai.meisai.put(key, tmpMeisai.get(tmpkey));
            			break;
            		}
            	}
            }
            
            //明細をリストに追加
            //meisai.print();
            meisaiList.add(meisai);
            
            }
            //明細のタイトル行を追加
            for(String key : Meisai.meisaiList){
            	pw.print(key + ",");
            }
            pw.println();
            //明細を集計
            for(Meisai m :meisaiList){
	        	for(String key : Meisai.meisaiList){
	        		pw.print(m.meisai.get(key)+",");
	        	}
	        	pw.println();
            }
            pw.close();
            
        } catch (IOException e) {
            System.out.println(e);
        }
        
    }
	


}
