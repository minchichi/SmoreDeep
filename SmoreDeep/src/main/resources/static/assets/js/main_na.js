document.addEventListener("DOMContentLoaded", function () {
    const allAgreeCheckbox = document.getElementById("join"); // 전체 동의 체크박스
    const checkboxes = document.querySelectorAll(".agree input[type='checkbox']"); // 개별 체크박스
    const nextButton = document.querySelector(".button.next");

    // 전체 동의 체크박스 클릭 시 개별 체크박스 상태 변경
    allAgreeCheckbox.addEventListener("change", function () {
        const isChecked = allAgreeCheckbox.checked;
        checkboxes.forEach(checkbox => {
            checkbox.checked = isChecked;
        });
        updateNextButtonState();
    });

    // 개별 체크박스 상태 변경 시 전체 동의 체크박스 상태 업데이트
    checkboxes.forEach(checkbox => {
        checkbox.addEventListener("change", function () {
            allAgreeCheckbox.checked = [...checkboxes].every(checkbox => checkbox.checked);
            updateNextButtonState();
        });
    });

    // "다음" 버튼 활성화 여부 업데이트 함수
    function updateNextButtonState() {
        const allChecked = [...checkboxes].every(checkbox => checkbox.checked);
        nextButton.disabled = !allChecked;
        nextButton.style.opacity = allChecked ? "1" : "0.5";
        nextButton.style.cursor = allChecked ? "pointer" : "not-allowed";
    }

    // "다음" 버튼 클릭 시 다음 단계로 이동
    function goToNextPage() {
        if (!nextButton.disabled) {
            document.querySelector(".wrap-join").classList.add("hidden");
            document.querySelector(".wrap-join2").classList.remove("hidden");
        }
    }

    // "다음" 버튼 클릭 시 다음 페이지로 이동
    nextButton.addEventListener("click", goToNextPage);

    updateNextButtonState(); // 초기 상태 설정

});

let isUserIdTaken = false;

// 아이디 중복확인
$('#user_id').on('blur', function() {  // input 필드를 떠날 때
            var userId = $('#user_id').val();  // 사용자가 입력한 아이디 값
            if (userId) {
                $.ajax({
                    url: '/check-userId',
                    type: 'POST',
                    contentType: 'application/json',
                    data: JSON.stringify({ userId: userId }),
                    success: function(response) {
                        if (response.isTaken) {
                            $('#user_id_message').text('이미 사용 중인 아이디입니다').css('color', 'red');
							UserIdCheck = false;
                        } else {
                            $('#user_id_message').text('사용 가능한 아이디입니다').css('color', 'green');
							UserIdCheck = true;
                        }
                    },
                    error: function() {
                        $('#user_id_message').text('아이디 중복 확인 실패').css('color', 'red');
						UserIdCheck = false;
                    }
                });
            }
        });


// 비밀번호 유효성 검사
let password = document.querySelector("#password");
let passwordRetype = document.querySelector("#confirm_password")
let passCheck = document.querySelector('.pass_check');
let passSame = document.querySelector(".pass_same");

function strongPassword(str) {
  return /^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(str);
}

function isMatch (password1, password2) {
  return password1 === password2;
}


let strongPw = false;
let matchPw = false;

function onPasswordChange() {
  if (password.value.length !== 0) {
    if(strongPassword(password.value)) {
      passCheck.classList.add('hidden');
	  strongPw = true;
    }
    else {
      passCheck.classList.remove('hidden');
	  strongPw = false;
    }
  }
  if (password.value.length !== 0) {
    if(isMatch(password.value, passwordRetype.value)) {
      passSame.classList.add('hidden');
      matchPw = true;
    }
    else {
      passSame.classList.remove('hidden');
      matchPw = false;
    }
  }
};

password.addEventListener('input', onPasswordChange);  // 비밀번호가 변경될 때
passwordRetype.addEventListener('input', onPasswordChange);  // 비밀번호 확인이 변경될 때

$('#signup-form').on('submit', function(event) {
    if (!UserIdCheck||!strongPw||!matchPw) {
        event.preventDefault();
    } else {
		document.getElementById('signup-form').submit();
	}
});


// 로그인 페이지 탭 키 기능
function switchTab(role) {
    document.querySelectorAll('.tab-button').forEach(btn => btn.classList.remove('active'));
    document.querySelectorAll('form').forEach(form => form.classList.add('hidden'));
    
    if (role === 'user') {
        document.querySelectorAll('.tab-button')[0].classList.add('active');
        document.getElementById('user').classList.remove('hidden');
    } else {
        document.querySelectorAll('.tab-button')[1].classList.add('active');
        document.getElementById('admin').classList.remove('hidden');
    }
}