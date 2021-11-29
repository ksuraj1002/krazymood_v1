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

});