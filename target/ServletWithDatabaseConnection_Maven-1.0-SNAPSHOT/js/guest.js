function showGuestBooks(books){
    if(books[0]===null) return "";
    let html = "";
    html+="<div class='column' style='align-items: center;justify-content: center;width: 100%'>"
    for(let i = 0;i < books.length;i++){
        html+="<div class='column book'>" +
            "<h4 class='book_title'>"+books[i].title+"</h4>"+
            "<div class='row' style='align-items: center;padding-bottom: 2rem'>" +
            "<img src='"+books[i].photo+"'>" +
            "<p class='book_p'>ISBN: "+books[i].isbn+"<br>Authors: "+books[i].authors+"<br>Genre: "+books[i].genre+
            "<br>URL: <a href='"+books[i].url+"'>"+books[i].url+"</a><br>Pages: "+books[i].pages+"<br>Publication year: "+books[i].publicationyear+
            "</p>"+
            "</div>" +
            "</div>";
    }
    html+="</div>"
    return html;
}
function getGuestBooks(genre){
    console.log("getGuestBooks");
    let xhr = new XMLHttpRequest();
    xhr.onload = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let books = readBooks(xhr);
            $("#ajaxContent").html(showGuestBooks(books));
            document.getElementById("ajaxContent").style.display = 'flex';
        }
    }
    let data = "request=getBooks&genre="+genre;
    xhr.open('GET', 'StudentServlet?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}

function getGuestBook(title){
    console.log("getGuestBook");
    let xhr = new XMLHttpRequest();
    xhr.onload = function() {
        if (xhr.readyState === 4 && xhr.status === 200) {
            let book = readBooks(xhr);
            $("#ajaxContent").html(showGuestBooks(book));
            document.getElementById("ajaxContent").style.display = 'flex';
        }
    }
    let data = "request=getBooks&book_title="+title;
    xhr.open('GET', 'StudentServlet?'+data);
    xhr.setRequestHeader('Content-type','application/x-www-form-urlencoded');
    xhr.send();
}