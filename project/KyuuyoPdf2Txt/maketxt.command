#!/bin/sh

cd `dirname $0`

ROOT_DIR="/Users/kentarokira/Documents/accounting/Kyuuyo"
IN_DIR="/pdf"
OUT_DIR="/txt"
LIST=`ls -1 ${ROOT_DIR}${IN_DIR}`

for LINE in ${LIST}
do
  if [ ! -e ${ROOT_DIR}${OUT_DIR}/${LINE}.txt ]; then
    echo ${ROOT_DIR}${OUT_DIR}/${LINE} " pdf -> txt"
    xpdf-pdftotext ${ROOT_DIR}${IN_DIR}/${LINE} ${ROOT_DIR}${OUT_DIR}/${LINE}.txt -opw '19851028' -upw '19851028' -layout 
  else
    echo ${ROOT_DIR}${OUT_DIR}/${LINE} " is exist"
  fi
done
