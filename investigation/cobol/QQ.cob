      ****************************************** 
000100* Sample COBOL program
      * かけ算の表（九九）を作成する２次元配列
      * の学習用プログラム
      ****************************************** 
      * 見出し部
      * プログラム名は必須
      ****************************************** 
000200 IDENTIFICATION DIVISION.
000300 PROGRAM-ID. QQ.
      ****************************************** 
      * 環境部
      * ファイルを使用する場合などに使用
      ****************************************** 
       ENVIRONMENT DIVISION.
      ****************************************** 
      * データ部
      * プログラムで使用するデータを定義
      ****************************************** 
000310 DATA DIVISION.
       WORKING-STORAGE SECTION.
       01 I   PIC 9(02).
       01 J   PIC 9(02).
       01 WCNT PIC 9(05) VALUE 0.
      * 2次元テーブルの領域定義はOCCURES OCCURES
       01 CNT-AREAS.
          03 CNTA OCCURS 20.
             05 CNTB OCCURS 20.
                07 CNT PIC 9(03)B(02).  
      ****************************************** 
      * 実行部
      * プログラムの処理を記述
      ****************************************** 
000400 PROCEDURE DIVISION.  
       
       PERFORM VARYING J FROM 1 BY 1 UNTIL J > 20
         PERFORM VARYING I FROM 1 BY 1 UNTIL I > 20
           COMPUTE WCNT = J * I 
           MOVE WCNT TO CNT (J I)
          END-PERFORM
       END-PERFORM
      * 結果をテーブル形式で表示させる
       DISPLAY "******* START *******"
       PERFORM VARYING J FROM 1 BY 1 UNTIL J > 20
             DISPLAY CNTA (J)
       END-PERFORM
       DISPLAY "******** END ********"

000600 STOP RUN.
