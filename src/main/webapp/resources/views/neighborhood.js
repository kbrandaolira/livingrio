$(document).ready(function(){
	$("#btn_evaluate").click(function() {
		url = $("#BASE_URL").val() + "neighborhood/" + $("#identifier").val() + "/evaluate";
		window.location.href = url;
	});
	
	$('#modal_pictures').on('show.bs.modal',function(e) {
		var id = $(e.relatedTarget).data('book-id');
		
		myHtml = '<img src="' + $("#img_"+id).attr("src") + '"/><p>';
		
		$("#body_picture").html(myHtml);
		
		
	});
	
	$("#link_editNeighborhood").click(function(){
		url = $("#BASE_URL").val() + "manageNeighborhood/init/" + $("#identifier").val();
		window.location.href = url;
		
	});
	
	
});