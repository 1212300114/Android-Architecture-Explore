window.onerror = function(){return true;}
function fontSize(n){
	switch(n){
		case 28:document.getElementById("body_section").className = "font_small";break;
		case 33:document.getElementById("body_section").className = "font_large";break;
		case 38:document.getElementById("body_section").className = "font_largex";break;
	}
}
$(function () {
	var tags = document.getElementById("content").getElementsByTagName("img");
    for (i = 0; i < tags.length; i++) {
    	if(tags[i].getAttribute("type") != "adv")
        	tags[i].setAttribute("onclick", "location.href='" + tags[i].getAttribute("src") + "'");
    }
    $("#videoImg img").bind("error",function(){
		this.src='file:///android_asset/lazyload/grey.gif';
	}); 
    $(".thumbImg img, #content img").bind("error",function(){
		$(this).remove()
	}); 
	$("#body_section img").lazyload({
		placeholder : "file:///android_asset/lazyload/grey.gif",    
		effect : "fadeIn"   
	});
});