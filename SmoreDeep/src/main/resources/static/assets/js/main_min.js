/* lecture-list 카테고리 슬라이드 */
var on = $('.slides').find('.activetop').index();
var options = {
  horizontal: 1,
  mouseDragging: 1,
  touchDragging: 1,
};
var frame = new Sly('.category', options).init();

$('.category .slides li').on("click", function(){
  $('.category .slides li').removeClass("focus");
  $(this).addClass("focus");
})

/* lecture-one 탭 */
$(".intro").click(function(){
  $(".intro").removeClass("pick");
  $(this).addClass("pick");
})
