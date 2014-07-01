package readText;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Meisai {
	
	//マップ
	public Map<String,String> meisai;
	//リスト
	public static List<String> meisaiList;
	//年月
	private String YYYYMM="賃金明細書";
	 //合計
	private String TOTAL_SIKYUKIN="支給金合計";
	private String TOTAL_KOUJYOKIN="控除金合計";
	private String TOTAL_SASIHIKI="差引支給金";
	//受取手段
	private String UKETORI_GINKOU_DEF="銀行振込(一般)";
	private String UKETORI_GINKOU1="銀行振込(定額1)";
	private String UKETORI_GINKOU2="銀行振込(定額2)";
	//基本情報
	private String BASE_TOKYU="職群等級";
	private String BASE_SYOKUSYU="職種";
	private String BASE_HONKYU="本給";
	private String BASE_JIKANWARITINGIN="時間割賃金";
	private String BASE_KOUSEINENKIN="標報月額(厚生年金)";
	private String BASE_KENKOUHOKEN="標報月額(健康保険)";
	private String BASE_FUYOUTAISYOU="税扶養対象人員";
	private String BASE_KAZOKUTEATE="家族手当対象人員";
	//勤務休暇
	private String KINKYU_SYUKKIN="日数出勤";
	private String KINKYU_JIKANGAI=""; 
	private String KINKYU_SINYA="";
	//支給金内訳
	private String SIKYU_GOUKEI="支給金合計";
	private String SIKYU_KAZEITAISYOU="内課税対象支給金";
	private String SIKYU_HONKYU="本給";
	private String SIKYU_JIKANGAI="時間外勤務手当";
	private String SIKYU_SHINYA="深夜割増手当";
	private String SIKYU_MOTIKABUSYOUREI="持株会奨励金";
	private String SIKYU_CAFECASHBACK="カフェキャッシュバック";
	private String SIKYU_SHIKAKUHYOUSYOUKIN="資格取得表彰金";
	private String SIKYU_JYOUTYUTEATE="常駐手当";
	private String SIKYU_RINJI4="臨時給与(4月分)";
	private String SIKYU_RINJI5="臨時給与(5月分)";
	private String SIKYU_TYUSYOKUHOJYO="昼食補助";
	private String SIKYU_JYUTAKUTEATE="住宅手当";
	private String SIKYU_TUUKINTEATE="通勤手当";
	private String SIKYU_DAITETUKYUBUBIKI="代徹休歩引";
	private String SIKYU_NEWMARUKIHAITOUKIN="NEW(希)配当金";
	//控除金内訳
	private String KOUJYO_TOTAL="控除金合計"; 
	private String KOUJYO_KENKOUHOKEN="健康保険料";
	private String KOUJYO_KOUSEINENKIN="厚生年金保険料";
	private String KOUJYO_KOYOUHOKEN="雇用保険料";
	private String KOUJYO_SYOTOKUZEI="所得税";
	private String KOUJYO_JYUMINZEI="住民税";
	private String KOUJYO_MOTIKABUTUMITATE="持株会積立";
	private String KOUJYO_MOTIKABUSYOUREI="持株会奨励金";
	private String KOUJYO_IPPANZAIKEI="一般財形貯蓄";
	private String KOUJYO_DANTAIIRYOUHOKEN="団体医療保険料";
	private String KOUJYO_GANHOKEN=" (ガン)保険料";
	private String KOUJYO_IRYOUHOEKN="(医)保険料";
	private String KOUJYO_RYOUSYATAKUHI="寮・社宅費";
	private String KOUJYO_KUMIAIHI="組合費";
	private String KOUJYO_KUMIAIKANKEIHI1="組合関係費1";
	private String KOUJYO_KUMIAIKANKEIHI2="組合関係費2";
	//カフェテリアP
	private String TOKKI_CAFETERIAZAN="当月末カフェテリア残P";
	private String TOKKI_CAFETERIAFUYO="当年付与P";
	private String TOKKI_CAFETERIAKURIKOSI="前年繰越P";
	
	Meisai(){
		
		//明細
		meisai = new HashMap<String, String>();
		//年月
		meisai.put(YYYYMM, "");
		//合計
		meisai.put(TOTAL_SIKYUKIN, "");
		meisai.put(TOTAL_KOUJYOKIN, "");
		meisai.put(TOTAL_SASIHIKI, "");
		//受取
		meisai.put(UKETORI_GINKOU_DEF, "");
		meisai.put(UKETORI_GINKOU1, "");
		meisai.put(UKETORI_GINKOU2, "");
		//基本
		meisai.put(BASE_TOKYU, "");
		meisai.put(BASE_SYOKUSYU, "");
		meisai.put(BASE_HONKYU, "");
		meisai.put(BASE_JIKANWARITINGIN, "");
		meisai.put(BASE_KOUSEINENKIN, "");
		meisai.put(BASE_KENKOUHOKEN, "");
		meisai.put(BASE_FUYOUTAISYOU, "");
		meisai.put(BASE_KAZOKUTEATE, "");
		//勤休
		meisai.put(KINKYU_SYUKKIN, "");
		meisai.put(KINKYU_JIKANGAI, "");
		meisai.put(KINKYU_SINYA, "");
		//支給
		meisai.put(SIKYU_GOUKEI, "");
		meisai.put(SIKYU_KAZEITAISYOU, "");
		meisai.put(SIKYU_HONKYU, "");
		meisai.put(SIKYU_JIKANGAI, "");
		meisai.put(SIKYU_SHINYA, "");
		meisai.put(SIKYU_MOTIKABUSYOUREI, "");
		meisai.put(SIKYU_JYOUTYUTEATE, "");
		meisai.put(SIKYU_RINJI4, "");
		meisai.put(SIKYU_RINJI5, "");
		meisai.put(SIKYU_TYUSYOKUHOJYO, "");
		meisai.put(SIKYU_JYUTAKUTEATE, "");
		meisai.put(SIKYU_TUUKINTEATE, "");
		meisai.put(SIKYU_DAITETUKYUBUBIKI, "");
		meisai.put(SIKYU_CAFECASHBACK, "");
		meisai.put(SIKYU_SHIKAKUHYOUSYOUKIN, "");
		meisai.put(SIKYU_NEWMARUKIHAITOUKIN, "");
		//控除
		meisai.put(KOUJYO_TOTAL, "");
		meisai.put(KOUJYO_KENKOUHOKEN, "");
		meisai.put(KOUJYO_KOUSEINENKIN, "");
		meisai.put(KOUJYO_KOYOUHOKEN, "");
		meisai.put(KOUJYO_SYOTOKUZEI, "");
		meisai.put(KOUJYO_JYUMINZEI, "");
		meisai.put(KOUJYO_MOTIKABUTUMITATE, "");
		meisai.put(KOUJYO_MOTIKABUSYOUREI, "");
		meisai.put(KOUJYO_IPPANZAIKEI, "");
		meisai.put(KOUJYO_DANTAIIRYOUHOKEN, "");
		meisai.put(KOUJYO_GANHOKEN, "");
		meisai.put(KOUJYO_IRYOUHOEKN, "");
		meisai.put(KOUJYO_RYOUSYATAKUHI, "");
		meisai.put(KOUJYO_KUMIAIHI, "");
		meisai.put(KOUJYO_KUMIAIKANKEIHI1, "");
		meisai.put(KOUJYO_KUMIAIKANKEIHI2, "");
		//特記
		meisai.put(TOKKI_CAFETERIAZAN, "");
		meisai.put(TOKKI_CAFETERIAFUYO, "");
		meisai.put(TOKKI_CAFETERIAKURIKOSI, "");
		
		//リスト
		meisaiList = new ArrayList<String>();
		meisaiList.add(YYYYMM);
		meisaiList.add("【支払内訳】");
			meisaiList.add(TOTAL_SIKYUKIN);
			meisaiList.add(SIKYU_KAZEITAISYOU);
			meisaiList.add(SIKYU_HONKYU);
			meisaiList.add(SIKYU_JIKANGAI);
			meisaiList.add(SIKYU_SHINYA);
			meisaiList.add(SIKYU_MOTIKABUSYOUREI);
			meisaiList.add(SIKYU_JYOUTYUTEATE);
			meisaiList.add(SIKYU_RINJI4);
			meisaiList.add(SIKYU_RINJI5);
			meisaiList.add(SIKYU_TYUSYOKUHOJYO);
			meisaiList.add(SIKYU_JYUTAKUTEATE);
			meisaiList.add(SIKYU_TUUKINTEATE);
			meisaiList.add(SIKYU_DAITETUKYUBUBIKI);
			meisaiList.add(SIKYU_CAFECASHBACK);
			meisaiList.add(SIKYU_SHIKAKUHYOUSYOUKIN);
			meisaiList.add(SIKYU_NEWMARUKIHAITOUKIN);
		meisaiList.add("【控除内訳】");
			meisaiList.add(KOUJYO_TOTAL);
		//meisaiList.add("<<税金>>");
			meisaiList.add(KOUJYO_SYOTOKUZEI);
			meisaiList.add(KOUJYO_JYUMINZEI);
		//meisaiList.add("<<社会保険>>");
			meisaiList.add(KOUJYO_KOYOUHOKEN);
			meisaiList.add(KOUJYO_KENKOUHOKEN);
			meisaiList.add(KOUJYO_KOUSEINENKIN);
		//meisaiList.add("<<任意保険>>");
			meisaiList.add(KOUJYO_DANTAIIRYOUHOKEN);
			meisaiList.add(KOUJYO_GANHOKEN);
			meisaiList.add(KOUJYO_IRYOUHOEKN);
		//meisaiList.add("<<社内>>");
			meisaiList.add(KOUJYO_IPPANZAIKEI);
			meisaiList.add(KOUJYO_MOTIKABUTUMITATE);
			meisaiList.add(KOUJYO_MOTIKABUSYOUREI);
		//meisaiList.add("<<組合>>");
			meisaiList.add(KOUJYO_KUMIAIHI);
			meisaiList.add(KOUJYO_KUMIAIKANKEIHI1);
			meisaiList.add(KOUJYO_KUMIAIKANKEIHI2);
		meisaiList.add("【差引内訳】");
			meisaiList.add(TOTAL_SASIHIKI);
		//meisaiList.add("<<新生銀行>>");
			meisaiList.add(UKETORI_GINKOU1);
		//meisaiList.add("<<三井住友（共用）>>");
			meisaiList.add(UKETORI_GINKOU2);
		//meisaiList.add("<<三井住友（個人）>>");
			meisaiList.add(UKETORI_GINKOU_DEF);
		
		
	}
	
	public void print(){
    	for(String key : meisai.keySet()){
    			System.out.println(meisai.get(YYYYMM) + "," + key +","+ meisai.get(key));
    	}
	}
}
