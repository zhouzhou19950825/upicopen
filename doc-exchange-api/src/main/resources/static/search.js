	$.ajax({
				url : '/exchange',
				type : "POST",
				data : {
					url:""
				},
//				dataType : "json",
				success : function(data) {
					alert(data);
				}
	});