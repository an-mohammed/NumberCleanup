
  $.fn.responsiveTable = function () {
      $(this).find('table').each(function () {
          var trAcc = $(this).find('tr').not('tr:first-child'),
              thHead = [];

          $(this).find('tr:first-child td, tr:first-child th').each(function () {
              headLines = $(this).text();
              thHead.push(headLines);
          });

          trAcc.each(function () {
              for (i = 0, l = thHead.length; i < l; i++) {
                  $(this).find('td').eq(i + 1).prepend('<h3>' + thHead[i + 1] + '</h3>');
              }
          });

          trAcc.append('<i class="icon-accordion">+</i>');
          trAcc.click(function () {
              if ($(this).hasClass('accordion-opened')) {
                  $(this).animate({
                      maxHeight: '60px'
                  }, 200).removeClass('accordion-opened').find('.icon-accordion').text('+');

              } else {
                  $(this).animate({
                      maxHeight: '1000px'
                  }, 400).addClass('accordion-opened').find('.icon-accordion').text('-');
              }
          });
      });
  };

// Init
  $(function () {
      $('.table-responsive').responsiveTable();
  });




$('.btn-tabed').click(function(){
    $('.btn-tabed').removeClass('active');
    $(this).addClass('active');
})

$(function(){
    jQuery('img.svg').each(function(){
        var $img = jQuery(this);
        var imgID = $img.attr('id');
        var imgClass = $img.attr('class');
        var imgURL = $img.attr('src');
    
        jQuery.get(imgURL, function(data) {
            // Get the SVG tag, ignore the rest
            var $svg = jQuery(data).find('svg');
    
            // Add replaced image's ID to the new SVG
            if(typeof imgID !== 'undefined') {
                $svg = $svg.attr('id', imgID);
            }
            // Add replaced image's classes to the new SVG
            if(typeof imgClass !== 'undefined') {
                $svg = $svg.attr('class', imgClass+' replaced-svg');
            }
    
            // Remove any invalid XML tags as per http://validator.w3.org
            $svg = $svg.removeAttr('xmlns:a');
            
            // Check if the viewport is set, else we gonna set it if we can.
            if(!$svg.attr('viewBox') && $svg.attr('height') && $svg.attr('width')) {
                $svg.attr('viewBox', '0 0 ' + $svg.attr('height') + ' ' + $svg.attr('width'))
            }
    
            // Replace image with new SVG
            $img.replaceWith($svg);
    
        }, 'xml');
    
    });
});


