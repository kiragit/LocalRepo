/* -*- Mode: Java; tab-width: 2; indent-tabs-mode: nil; c-basic-offset: 2 -*- */
/* vim: set shiftwidth=2 tabstop=2 autoindent cindent expandtab: */

//
// See README for overview
//

'use strict';

//
// Fetch the PDF document from the URL using promises
//
var fs = require('fs');

//global.window = global;
//global.navigator = { userAgent: "node" };
//global.PDFJS = {};
//global.DOMParser = require('./lib/domparsermock.js').DOMParserMock;
//require('./lib/pdf.combined.js');


var pdfPath = '/Users/kentarokira/Documents/開発/node-webkit/pdftool/sample.pdf';
var data = new Uint8Array(fs.readFileSync(pdfPath));
PDFJS.workerSrc = './pdf.js/build/generic/build/pdf.worker.js';


PDFJS.getDocument(data).then(function(pdf) {
  // Using promise to fetch the page
  pdf.getPage(1).then(function(page) {
    var scale = 1.5;
    var viewport = page.getViewport(scale);

    //
    // Prepare canvas using PDF page dimensions
    //
    var canvas = document.getElementById('c');
    var context = canvas.getContext('2d');
    canvas.height = viewport.height;
    canvas.width = viewport.width;

    //
    // Render PDF page into canvas context
    //
    var renderContext = {
      canvasContext: context,
      viewport: viewport
    };
    page.render(renderContext);
  });
});
