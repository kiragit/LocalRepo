ROOT_DIR="/Users/kentarokira/Documents/accounting/Kyuuyo"
OUT_DIR="/txt"
LIST=`ls -1 ${ROOT_DIR}${OUT_DIR}`

for LINE in ${LIST}
do
  python text.py ${OUT_DIR}"/"${LINE} $1
done
