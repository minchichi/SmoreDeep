/* lecture-one 탭 */
$(".intro").click(function(){
  $(".intro").removeClass("pick");
  $(this).addClass("pick");
});

window.onload = function() { 
	if(page!=0) {
		$("#intro-review").trigger("click");
		if(window.location.href.includes("lecture_one")) {
			window.location.href=window.location.href+"#lecture-review";
		}
	}
};

/* lecture-list 카테고리 슬라이드 */
/*var on = $('.slides').find('.activetop').index();
var options = {
  horizontal: 1,
  mouseDragging: 1,
  touchDragging: 1
};
var frame = new Sly('.category', options).init();*/

$('.category .slides li').on("click", function(){
  $('.category .slides li').removeClass("focus");
  $('.category .slides li').removeClass("activetop");
  $(this).addClass("focus");
  $(this).addClass("activetop");
  
  document.getElementById('category').value = numToText($(this).val());
  document.getElementById('page').value = 0;
  document.getElementById('searchForm').submit();
});

function numToText(num) {
switch (num) {
	case 0: num="전체"
		break;
	case 1: num="프론트엔드"
		break;
	case 2: num="백엔드"
		break;
	case 3: num="프로그래밍 언어"
		break;
	case 4: num="알고리즘 · 자료구조"
		break;
	case 5: num="데브옵스 · 인프라"
		break;
	case 6: num="데이터베이스"
		break;
	case 7: num="웹 개발"
		break;
	case 8: num="모바일 앱 개발"
		break;
	case 9: num="개발 도구"
		break;
	case 10: num="개발 · 프로그래밍 기타"
		break;
	case 11: num="풀스택"
		break;
	case 12: num="데스크톱 앱 개발"
		break;
	default:
	};
	return num;
};

function textToNum(text) {
switch (text) {
	case "전체": text=0
		break;
	case "프론트엔드": text=1
		break;
	case "백엔드": text=2
		break;
	case "프로그래밍 언어": text=3
		break;
	case "알고리즘 · 자료구조": text=4
		break;
	case "데브옵스 · 인프라": text=5
		break;
	case "데이터베이스": text=6
		break;
	case "웹 개발": text=7
		break;
	case "모바일 앱 개발": text=8
		break;
	case "개발 도구": text=9
		break;
	case "개발 · 프로그래밍 기타": text=10
		break;
	case "풀스택": text=11
		break;
	case "데스크톱 앱 개발": text=12
		break;
	default:
	};
	return text;
};

window.addEventListener('load', function() {
	var liNum = textToNum(categoryText);
	const liElement = document.querySelector('li[value="'+liNum+'"]');
	if (liElement) {
		liElement.classList.add("focus", "activetop");
	}
	/* lecture-list 카테고리 슬라이드 */
	var on = $('.slides').find('.activetop').index();
	var options = {
		horizontal: 1,
		mouseDragging: 1,
		touchDragging: 1,
		itemNav: 'centered',
		smart: 1,
		startAt: on
	};
	var frame = new Sly('.category', options).init();
		
});


const page_elements = document.getElementsByClassName("page-link");
Array.from(page_elements).forEach(function(element) {
    element.addEventListener('click', function() {
        document.getElementById('page').value = this.dataset.page;
        document.getElementById('searchForm').submit();
    });
	
});


const search_input = document.getElementById("search-input");
search_input.addEventListener('keydown', function(event) {
	if (event.key === 'Enter') {
	event.preventDefault();
	document.getElementById('search').value = document.getElementById('search-input').value;
    document.getElementById('page').value = 0;
    document.getElementById('searchForm').submit();
	}
});

const search_button = document.getElementById("search-button");
search_button.addEventListener('click', function() {
    document.getElementById('search').value = document.getElementById('search-input').value;
    document.getElementById('page').value = 0;
    document.getElementById('searchForm').submit();
});

const search_level = document.getElementById("search-level");
search_level.addEventListener('change', function() {
    document.getElementById('level').value = document.getElementById('search-level').value;
    document.getElementById('page').value = 0;
    document.getElementById('searchForm').submit();
});

const search_schedule = document.getElementById("search-schedule");
search_schedule.addEventListener('change', function() {
    document.getElementById('schedule').value = document.getElementById('search-schedule').value;
    document.getElementById('page').value = 0;
    document.getElementById('searchForm').submit();
});