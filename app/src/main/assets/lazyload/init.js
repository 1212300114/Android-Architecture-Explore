window.onerror = function(){return true;}

function reloadPhoto(oldpath, newpath){
	$("img").each(function(i){
		var _src = $(this).attr("data-src");
		if(_src == oldpath){
			$(this).attr("src", newpath);
		}
	})
}
var imgArr=new Array();
var imgIndex=0;
$(function () {

	$("#content img").each(function(){
		var o=this;
		if("video"!=$(o).attr("class")&&"false"!=$(o).attr("atlas"))
		{
		        var url=$(o).attr("data-src");
				imgArr[imgIndex]=url;
				$(o).bind("click",function(){
					var tmpIndex=parseInt($(o).attr("imgIndex"));
					imgBrows(imgArr,tmpIndex);
				});
				$(o).attr("imgIndex",imgIndex);
				imgIndex++;
		}
	});
	$(".phonenum").each(function(){
	  $(this).click(function(){
			var phoneNum=$(this).text();
			callPhone(phoneNum);
	  });
});
});
//������Ƶ
function playVideo(url,title)
{
	jsInterface.startVideo(url,title);
}
//ͼ�����
function imgBrows(imgArr,imgIndex)
{
  jsInterface.imgBrows(imgArr,imgIndex);
}
//����绰
function callPhone(phoneNum)
{
	jsInterface.callPhone(phoneNum);
}