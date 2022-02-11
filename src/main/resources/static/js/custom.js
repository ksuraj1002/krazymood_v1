$("button.search-close").hide();
$("div.search-form").hide();
$(document).ready(function() {

	//copying text
	$('.copyTxt').on("click", function() {
		id=$(this).data("my-func-attr");
		copyText =$('#copyContent'+id).text();
		var $temp = $("<textarea>");
		$("body").append($temp);
		$temp.val(copyText).select();
		document.execCommand("copy");
		$temp.remove();

		$(this).attr('title', 'Copied')
			.tooltip('fixTitle')
			.tooltip('show');
	});

	/*search item here*/
	function addSplittedWord(txt){
		splittedWord = txt.split(" ");
		returnValue="";
		for(i=0; i<=9; i++){
			returnValue+=splittedWord[i]+" ";
		}
		return returnValue;
	}
	$("#searchItm").keyup(function (){
		var rawData =$("#searchItm").val();
		//alert(rawData);
		if(rawData.length>5){
			$.ajax({
				type: "GET",
				enctype: 'multipart/form-data',
				url: "/search/content?con="+rawData,
				processData: false,
				contentType: false,
				cache: false,
				success: function (data) {
					if(data.length>0){

						var res = "<table style='overflow-y:scroll;height:180px;display:block;'>";
						for(var i=0; i<data.length; i++){
							truncateText = addSplittedWord(($(data[i].text).text()).trim());
							/*+data[i].language+'/'+data[i].category.title+'/'+data[i].subCategory.subCatName+'/'+data[i].id+'/'*/
							res+="<tr><td><a href='/"+data[i].engHeader +"'> <img src='/storage/public_images/"+data[i].imgname+"' style='width:100px;padding:2px 5px;' /> </a></td><td><a href='/"+data[i].engHeader +"'>"+ truncateText + "</a></td></tr>"
						}
						res+="</table>";
						$("#searchCntnt").html(res);
					}else{
						$("#searchCntnt").html("<table><tr><td>No contents are available</td></tr></table>");
					}

				},
				error: function (e) {
					$("#searchCntnt").html("<table><tr><td>No contents are available</td></tr></table>");
					console.log("ERROR : "+e);
				}
			});

		}
	});

	$("button.search-btn").click(function(){
		$("button.search-close").show();
		$("div.search-form").show();
		$("button.search-btn").hide();
	});

	$("button.search-close").click(function(){
		$("button.search-btn").show();
		$("button.search-close").hide();
		$("div.search-form").hide();
	});

});