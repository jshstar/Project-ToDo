let host = 'http://' + window.location.host;

console.log('이런 젠장');
$(document).ready(function () {
    const auth = getToken();
    if(auth === '') {
        window.location.href = host + "/api/user/login-page";
    } else {
        $('#login-true').show();
        $('#login-false').hide();
    }
})

function logout() {
    // 토큰 삭제
    // Cookies.remove('Authorization', { path: '/' });
    window.localStorage.removeItem('user-info')
    window.location.href = host + "/api/user/login-page";
}

function getToken() {
    // let auth = Cookies.get('Authorization');
    let auth = window.localStorage.getItem('user-info')
    if(auth === undefined) {
        return '';
    }

    return auth;
}