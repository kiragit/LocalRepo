var window  = Titanium.UI.createWindow();
var webView = Titanium.UI.createWebView( { url: "index.html" });
 
window.add(webView);
window.open();