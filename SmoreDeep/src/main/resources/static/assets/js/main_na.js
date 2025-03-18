document.addEventListener("DOMContentLoaded", function () {
    const allAgreeCheckbox = document.getElementById("join");
    const checkboxes = document.querySelectorAll(".agree .checkbox");
    const nextButton = document.querySelector("button[type='submit']");
    /* 회원가입 체크박스 전체동의 */
    function updateButtonState() {
        const allChecked = Array.from(checkboxes).every(checkbox => checkbox.checked);
        nextButton.disabled = !allChecked;
    }
    // 전체 동의 체크박스 클릭 시 개별 항목들 자동 선택/해제
    allAgreeCheckbox.addEventListener("change", function () {
        checkboxes.forEach(checkbox => checkbox.checked = allAgreeCheckbox.checked);
        updateButtonState();
    });

    checkboxes.forEach(checkbox => {
        checkbox.addEventListener("change", function () {
            allAgreeCheckbox.checked = Array.from(checkboxes).every(checkbox => checkbox.checked);
            updateButtonState();
        });
    });

    updateButtonState();
});

// 회원가입 다음 페이지 넘기기
$(".next").click(function(){
    $(".wrap-join2").show();
    $(".wrap-join").hide();
})

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

  password.onkeyup = function () {
    if (password.value.length !== 0) {
      if(strongPassword(password.value)) {
        passCheck.classList.add('hidden');
      }
      else {
        passCheck.classList.remove('hidden');
      }
    }
  };

  passwordRetype.onkeyup = function () {
    if (password.value.length !== 0) {
      if(isMatch(password.value, passwordRetype.value)) {
        passSame.classList.add('hidden');
      }
      else {
        passSame.classList.remove('hidden');
      }
    }
  }

/* 로그인 페이지 탭 키 */
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