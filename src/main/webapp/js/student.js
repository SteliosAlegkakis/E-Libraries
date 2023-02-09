function showBooks(books){
    let html = "";
    html+="<div class='column' style='align-items: center;justify-content: center;width: 100%'>"
    for(let i = 0;i < books.length;i++){
        html+="<div class='column book'>" +
            "<h4 class='book_title'>"+books[i].title+"</h4>"+
            "<div class='row' style='align-items: center'>" +
            "<img src='"+books[i].photo+"'>" +
            "<p class='book_p'>ISBN: "+books[i].isbn+"<br>Authors: "+books[i].authors+"<br>Genre: "+books[i].genre+
            "<br>URL: <a href='"+books[i].url+"'>"+books[i].url+"</a><br>Pages: "+books[i].pages+"<br>Publication year: "+books[i].publicationyear+
            "</p>"+
            "</div>"
            +"<button class='button'>Borrow</button>"+
            "</div>";
    }
    html+="</div>"
    return html;
}

function readBooks(xhr){
    let i = 0,j = 0,jsonBook="",books = [];
    while(i<xhr.responseText.length-1){
        while (xhr.responseText.at(i)!='|'){
            jsonBook+=xhr.responseText.at(i);
            i++;
            if(i>=xhr.responseText.length) break;
        }
        if(i>=xhr.responseText.length) break;
        books[j] = JSON.parse(jsonBook);
        jsonBook="";
        j++;
        i++;
    }
    return books;
}

function getBooks(genre){
    console.log("getBooks");
    let xhr = new XMLHttpRequest();
    xhr.onload = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let books = readBooks(xhr);
            $("#ajaxContent").html(showBooks(books));
            document.getElementById("ajaxContent").style.display = 'flex';
        }
    }
    let data = "request=getBooks&genre="+genre;
    xhr.open('GET', 'StudentServlet?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}


function logout(){
    let xhr = new XMLHttpRequest();
    xhr.onload = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            window.location.replace("http://localhost:8080/ServletWithDatabaseConnection_Maven_war_exploded/index.html");
            console.log("logout");
        } else if (xhr.status !== 200) {
            console.log("Error at logout");
        }
    }
    let data = "request=logout";
    xhr.open('GET', 'UserServlet?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}