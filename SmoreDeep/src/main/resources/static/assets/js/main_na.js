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

    // 비밀번호 유효성 검사
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
