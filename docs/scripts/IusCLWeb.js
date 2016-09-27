/* JavaScript File */

/*
 * **************************************************************************************************** 
 */
window.onload = function() {

/*
	mainbox = document.getElementById("mainbox");
	mainboxheight = mainbox.offsetHeight;
	
	maincontent = document.getElementById("maincontent");
	maincontentheight = mainboxheight - 32;
	maincontent.style.height = maincontentheight + "px";
*/
	if (window.location.hash.length > 1) {
		subItem = window.location.hash.split("#")[1];
		if (document.getElementById(subItem + "_div") != null) {
			ftoggleitemsubitem(subItem, subItem + "_div");
		}
		else {
			var anc = document.getElementById(subItem);
			if (anc != null) {
				window.location = anc.getAttribute("href")
			}
		}
	}
};

/*
 * **************************************************************************************************** 
 */
function ftoggleitemsubitem(item_name, subitem_name) { 

	var item = document.getElementById(item_name);

	if ((item.style.fontWeight.length == 0) || (item.style.fontWeight != "bold")) {
		item.style.fontWeight = "bold";
	}
	else {
		item.style.fontWeight = "normal";
	};
	
	ftogglesubitem(subitem_name);
}

/*
 * **************************************************************************************************** 
 */
function ftogglesubitem(subitem_name) { 
	
	var subitem = document.getElementById(subitem_name);
	
	if ((subitem.style.display == "none") || (subitem.style.display != "block")) {
		subitem.style.display = "block";
	}
	else {
		subitem.style.display = "none";
	};
}

/*
 * **************************************************************************************************** 
 */
function ftogglerelative(item_name, subitem_name) { 

	var item = document.getElementById(item_name);
	var subitem = document.getElementById(subitem_name);
	
	subitem.style.left = getLeft(item) - 2 + 12 + "px";
	subitem.style.top = getTop(item) + 14 + 12 + "px";

	if ((subitem.style.display == "none") || (subitem.style.display != "block")) {
		subitem.style.display = "block";
	}
	else {
		subitem.style.display = "none";
	};
}

/*
 * **************************************************************************************************** 
 */
function getLeft(obj){
    var leftValue= 0;
    while(obj){
	leftValue+= obj.offsetLeft;
	obj= obj.offsetParent;
    }
    return leftValue;
}

/*
 * **************************************************************************************************** 
 */
function getTop(obj){
    var topValue= 0;
    while(obj){
	topValue+= obj.offsetTop;
	obj= obj.offsetParent;
    }
    return topValue;
}
