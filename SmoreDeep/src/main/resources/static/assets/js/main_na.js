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

document.addEventListener("DOMContentLoaded", function () {
    const password = document.getElementById("password");
    const confirmPassword = document.getElementById("confirm_password");
    const form = document.querySelector("form");
    const inputs = document.querySelectorAll("input[required], select[required]");

    form.addEventListener("submit", function (e) {
        // 필수 입력값 검증
        const emptyInput = Array.from(inputs).some(input => !input.value.trim());
        if (emptyInput) {
            alert("모든 필수 항목을 입력해주세요.");
            e.preventDefault();
            return;
        }

        // 비밀번호 형식 검증 (영문+숫자 포함, 8자 이상)
        const passwordValue = password.value.trim();
        if (!/^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}$/.test(passwordValue)) {
            alert("비밀번호는 8자리 이상이며, 영문과 숫자를 포함해야 합니다.");
            e.preventDefault();
            return;
        }

        // 비밀번호 일치 검증
        if (passwordValue !== confirmPassword.value.trim()) {
            alert("비밀번호가 일치하지 않습니다.");
            e.preventDefault();
        }
    });
});