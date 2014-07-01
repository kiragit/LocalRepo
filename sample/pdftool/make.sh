#! /bin/sh
#start 2014/05/18 exports対策
mv ./js/app.js ./js/appbk.js
sed "s/exports.*//g" ./js/appbk.js > ./js/app.js
#end 2014/05/18 exports対策
zip -r app.nw index.html package.json ./js/app.js ./pdf.js/
cp app.nw node-webkit.app/Contents/Resources/

#start 2014/05/18 exports対策
mv ./js/appbk.js ./js/app.js
#end 2014/05/18 exports対策
