/**
* Styleswitch stylesheet switcher built on jQuery
* Under an Attribution, Share Alike License
* By Kelvin Luck ( http://www.kelvinluck.com/ )
**/

function chgStyle(v){
	document.getElementsByTagName("link")["pagestyle"].href = "../css/mainState/" + v + ".css";
}

