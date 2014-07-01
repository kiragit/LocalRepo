package houseKeepbyLINE;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class readTextLINE {
	
	private File filepath;
	private Kakeibo kakeibo;
	private List<String> splitter;
	private final String splitstr = "SPRITSTR";
	private String YYYYMMDD;
	private BufferedReader br;
	private final Pattern yyyymmdd = Pattern.compile("^[0-9][0-9][0-9][0-9].[0-9][0-9].[0-9][0-9].*");
	private final Pattern cost = Pattern.compile("^[0-9]*.");
	private Matcher match;
	private ArrayList<Kakeibo> kakeiboList = new ArrayList<Kakeibo>();
	
	public static void main(String[] args){
		File filepath = new File("/Users/kentarokira/Desktop/[LINE] 滝口 めぐみとのトーク.txt");
		readTextLINE rtl = new readTextLINE();
		rtl.setTextLINE(filepath, " ","　");
		rtl.readTextLine();
	}
	
	public void setTextLINE(File filepath,String... splitter){
		this.filepath = filepath;
		this.splitter = new ArrayList<String>();
		for(String split:splitter){
			this.splitter.add(split);
		}
	}
	
	public Kakeibo readTextLine(){
		System.out.println("start");
		System.out.println("read text:"+filepath.getPath());
		System.out.println("split line by:"+ splitter);
		String line;
		String[] parts;
		String[] koumoku_cost;
		String tmp_koumoku_cost;
		try{
			br = new BufferedReader( new FileReader(filepath));
				while( (line = br.readLine()) != null){
					match = yyyymmdd.matcher(line);
					if(match.matches()) YYYYMMDD = line;
					parts = line.split("	");
					if(parts.length >= 2){
						tmp_koumoku_cost = parts[2];
					}else{
						tmp_koumoku_cost = parts[0];
					}
					for(String split:splitter){
						tmp_koumoku_cost = tmp_koumoku_cost.replaceAll(split, splitstr);
					}
					koumoku_cost = tmp_koumoku_cost.split(splitstr);
					if(parts.length >= 2 && koumoku_cost.length >=2 && cost.matcher(koumoku_cost[1]).matches()){
						Kakeibo k = new Kakeibo();
						//System.out.print("【日時】:"+YYYYMMDD + " " + parts[0]);
						//System.out.print(",【名前】:"+parts[1]);
						//System.out.print(",【項目】:"+koumoku_cost[0]);
						//System.out.println(",【金額】:"+koumoku_cost[1]);
						k.setDay(YYYYMMDD + " " + parts[0]);
						k.setName(parts[1]);
						k.setKoumoku(koumoku_cost[0]);
						k.setCost(koumoku_cost[1]);
						kakeiboList.add(k);
					}
				}
		}catch(FileNotFoundException e){
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("read line");
		System.out.println("set kakeibo");
		System.out.println("end");
		return kakeibo;
		
	}

}
