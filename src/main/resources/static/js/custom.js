

jQuery(function ($) {
	"use strict";

	/* ----------------------------------------------------------- */
	/*  Fixed header
	/* ----------------------------------------------------------- */

	$(window).on('scroll', function () {
		if ($(window).scrollTop() > 70) {
			$('.navdown, .header-two').addClass('navbar-fixed');
		} else {
			$('.navdown, .header-two').removeClass('navbar-fixed');
		}
	});

	/* ----------------------------------------------------------- */
	/*  Mobile Menu
	/* ----------------------------------------------------------- */

	jQuery(".nav.navbar-nav li a").on("click", function () {
		jQuery(this).parent("li").find(".dropdown-menu").slideToggle();
		jQuery(this).find("i").toggleClass("fa-angle-down fa-angle-up");
	});



	/* ----------------------------------------------------------- */
	/*  Back to top
	/* ----------------------------------------------------------- */

	$(window).scroll(function () {
		if ($(this).scrollTop() > 50) {
			$('#back-to-top').fadeIn();
		} else {
			$('#back-to-top').fadeOut();
		}
	});

	// scroll body to 0px on click
	$('#back-to-top').on('click', function () {
		$('#back-to-top').tooltip('hide');
		$('body,html').animate({
			scrollTop: 0
		}, 800);
		return false;
	});

	$('#back-to-top').tooltip('hide');

});

	/* ----------------------------------------------------------- */
	/*  Site search
	/* ----------------------------------------------------------- */

	$('.nav-search').on('click', function () {
		$('.search-block').fadeIn(350);
	});

	$('.search-close').on('click', function () {
		$('.search-block').fadeOut(350);
	});



	/*Admin- Sidebar Collapse*/
	
      (function($) {

				"use strict";

				var fullHeight = function() {

					$('.js-fullheight').css('height', $(window).height());
					$(window).resize(function(){
						$('.js-fullheight').css('height', $(window).height());
					});

				};
				fullHeight();

				$('#sidebarCollapse').on('click', function () {
			      $('#sidebar').toggleClass('active');
			  });

			})(jQuery);

			
			
			
/* add category and subcategory */

  $(document).ready(function(){
        	
          var maxField = 10; //Input fields increment limitation
          var addButton = $('.add_button'); //Add button selector
          var wrapper = $('.field_wrapper'); //Input field wrapper
          var fieldHTML = '<div class="form-group" > <input type="text" class="form-control" id="subcategory" name="subcategory[]" placeholder="Enter sub-category name (like-love shayri)"> <a href="javascript:void(0);" class="remove_button"> <img src="../images/cross.jpg" style="width:25px; "/></a> </div>';
								
        /*  var fieldHTML = '<div><input type="text" id="subcategory" name="subcategory[]" value=""/><a href="javascript:void(0);" class="remove_button"> <img src="../images/cross.jpg" style="width:25px; "/></a></div>'; */ 
          var x = 1; //Initial field counter is 1

          //Once add button is clicked
          $(addButton).click(function(){
            //Check maximum number of input fields
            if(x < maxField){
              x++; //Increment field counter
              $(wrapper).append(fieldHTML); //Add field html
            }
          });

          //Once remove button is clicked
          $(wrapper).on('click', '.remove_button', function(e){
            e.preventDefault();
            $(this).parent('div').remove(); //Remove field html
            x--; //Decrement field counter
          });


      /*   start  for legends and category*/
	  var addlgndButton = $('.add_lgnd_button'); //Add button selector
	  var addlgndwrapper = $('.lgnd_field_wrapper');
	  var fieldlegend = '<div class="form-group" > <input type="text" class="form-control" id="lgndName" name="legendName[]" placeholder="Enter legends name"> <a href="javascript:void(0);" class="remove_button"> <img src="../images/cross.jpg" style="width:25px; "/></a> </div>';

	  /*  var fieldHTML = '<div><input type="text" id="subcategory" name="subcategory[]" value=""/><a href="javascript:void(0);" class="remove_button"> <img src="../images/cross.jpg" style="width:25px; "/></a></div>'; */
	  var x = 1; //Initial field counter is 1

	  //Once add button is clicked
	  $(addlgndButton).click(function(){
		  //Check maximum number of input fields
		  if(x < maxField){
			  x++; //Increment field counter
			  $(addlgndwrapper).append(fieldlegend); //Add field html
		  }
	  });

	  //Once remove button is clicked
	  $(addlgndwrapper).on('click', '.remove_button', function(e){
		  e.preventDefault();
		  $(this).parent('div').remove(); //Remove field html
		  x--; //Decrement field counter
	  });

	  $("#formLgnd").submit(function(event){
		  event.preventDefault();
		  var values = $('input[name^="legendName"]');
		  var myObj = [];

		  $.each(values, function (i, v) {
			  myObj.push(v.value);
		  });

		  console.log(myObj);

		  var json={
			  "categoryId":$("#categoryId").val(),
			  "language" : $("#lang").val(),
			  "tranlegendName":myObj
		  };

		  console.log(JSON.stringify(json));

		  $.ajax({
			  type: "POST",
			  enctype: 'multipart/form-data',
			  url: "/admin/addlegends",
			  data: JSON.stringify(json),
			  processData: false,
			  contentType: "application/json",
			  cache: false,
			  success: function (data) {
				  console.log("SUCCESS : ", data);
			  },
			  error: function (e) {
				  console.log("ERROR : "+e);
			  }
		  });
	  });

          
          $("#formcategory").submit(function(event){
        	  event.preventDefault();
        	  var values = $('input[name^="subcategory"]');
                	var myObj = [];
                	   
                    $.each(values, function (i, v) {
                        myObj.push({subCatName: v.value});
                    });  
                 
                    console.log(myObj);
        		
        	  var json={
        			  "title":$("#title").val(),
        			  "visibility":$("#visibility").val(),
        			  "subCategory":myObj
        	  };
        	  
        	  console.log(JSON.stringify(json));
        	  
              $.ajax({
                  type: "POST",
                  enctype: 'multipart/form-data',
                  url: "/admin/addcategory",
                  data: JSON.stringify(json),
                  processData: false,
                  contentType: "application/json",
                  cache: false,
                  success: function (data) {
                      console.log("SUCCESS : ", data);
                  },
                  error: function (e) {
                      console.log("ERROR : "+e);
                  }
              });
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

	  /*eod search item here*/
	  
	 /* change the value of placeholder */

		     $("#searchItm").attr("placeholder", "for better search type here ");
		     
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

	  $("#engheader").keyup(function (){
		  var rawData =$("#engheader").val();
		  //alert(rawData);
		  if(rawData.length>15){
			  $.ajax({
				  type: "GET",
				  enctype: 'multipart/form-data',
				  url: "/admin/keywords?key="+rawData,
				  processData: false,
				  contentType: false,
				  cache: false,
				  success: function (data) {
					  if(data != ""){
					  	alert("Need to modify keywords...Already exists!!!");
					  	$(':input[type="button"]').prop('disabled', true);
					  }else{
						  $(':input[type="button"]').prop('disabled', false);
						  console.log("No issues");
					  }
				  },
				  error: function (e) {
					  $(':input[type="button"]').prop('disabled', false);
					  console.log("No issues");
				  }
			  });

		  }
	  });

  });


$(document).ready(function() {
	$("#sendPost #hiddenValue").val(window.location.pathname); 
});

   /* $(document).ready(function() {
		if (localStorage.getItem("my_app_name_here-quote-scroll") != null) {
			$(window).scrollTop(localStorage.getItem("my_app_name_here-quote-scroll"));
		}

		$(window).on("scroll", function() {
			localStorage.setItem("my_app_name_here-quote-scroll", $(window).scrollTop());
		});
    });*/