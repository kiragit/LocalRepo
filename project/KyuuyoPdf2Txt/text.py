# coding:UTF-8 
import sys # モジュール属性 argv を取得するため
import re #正規表現
import string #一括置換
  
argvs = sys.argv  # コマンドライン引数を格納したリストの取得
argc = len(argvs) # 引数の個数
# デバッグプリント
# print argvs
# print argc
if (argc != 3):   # 引数が足りない場合は、その旨を表示
  print '使い方 python text.py [ファイル名] [検索条件]'
  quit()         # プログラムの終了
    
#print 'The content of %s ...n' % argvs[1]

r = re.compile(argvs[2]+'\d+')

f = open(argvs[1])
line = f.readline() # 1行読み込む(改行文字も含まれる)
while line:
  newline=line.replace('￥','').replace(',','').replace(' ','').replace('　','').replace(' ','').replace('：','')
  #newline=line.replace(',','').replace(' ','').replace('　','').replace(' ','')
  #print newline
  m=r.search(newline)
  if(m!=None):
    print argvs[1]+","+ argvs[2]+","+m.group(0).replace(argvs[2],'')
    quit()
  line = f.readline()
f.close
