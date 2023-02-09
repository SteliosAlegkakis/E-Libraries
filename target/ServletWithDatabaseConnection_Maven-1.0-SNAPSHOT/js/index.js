function login(){
    console.log("login");
    let xhr = new XMLHttpRequest();
    xhr.onload = function() {
        if (xhr.readyState === 4 && xhr.status === 201) {
            window.location.replace("http://localhost:8080/ServletWithDatabaseConnection_Maven_war_exploded/student.html");
        } else if (xhr.status !== 201) {
            document.getElementById("wrong_login").style.display = 'block';
            console.log("wrong login");
        }
    }
    let data = $('#loginForm').serialize();
    data+="&request=login";
    xhr.open('GET', 'UserServlet?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}
function isLoggedIn(){
    var xhr = new XMLHttpRequest();
    xhr.onload = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            window.location.replace("http://localhost:8080/ServletWithDatabaseConnection_Maven_war_exploded/student.html");
        } else if (xhr.status !== 200) {
            document.getElementById("loginForm").style.display = 'flex';
        }
    };
    let data = "request=isLoggedIn";
    xhr.open('GET', 'UserServlet?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}