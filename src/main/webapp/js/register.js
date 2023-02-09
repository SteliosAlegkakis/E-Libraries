var password = document.querySelector('#password');
var confirm_password = document.querySelector('#password-confirm');
var confirmed = document.querySelector('#confirm');
var show_password = document.querySelector('#showPassword');
var show_confirm_password = document.querySelector('#showConfirmPassword');
var password_strength;
var password_match;


//password confirmation

function compare_passwords(){
    if(password.value == confirm_password.value){//password are the same
        //display the green confirmed message
        confirmed.style.display='block';
        confirmed.innerHTML = 'confirmed';
        confirmed.style.color = 'green';
        password_match = true;
    }
    else{//passwords are different
        //display the red mismatch message
        confirmed.style.display='block';
        confirmed.innerHTML = 'mismatch';
        confirmed.style.color = 'red';
        password_match = false;
    }

    if(confirm_password.value.length === 0 || password.value.length === 0) confirmed.style.display = 'none';
}


password.addEventListener('keyup',()=>{
    if(confirm_password.value.length > 0) compare_passwords();
});

confirm_password.addEventListener('keyup',compare_passwords);

//password reveal

show_password.addEventListener("click",function (e) {
    // toggle the type attribute
    const type = password.getAttribute('type') === 'password' ? 'text' : 'password';
    password.setAttribute('type', type);
    // toggle the eye slash icon
    this.classList.toggle('fa-eye-slash');
});

show_confirm_password.addEventListener("click",function (e) {
    // toggle the type attribute
    const type = confirm_password.getAttribute('type') === 'password' ? 'text' : 'password';
    confirm_password.setAttribute('type', type);
    // toggle the eye slash icon
    this.classList.toggle('fa-eye-slash');
});

//password safety

function checkPasswordSafety(){
    var i,j;
    var num_count = 0;
    var cap_check = 0;
    var small_check =0;
    var symbols_check = 0;
    var symbol = 0;
    var symbol_found = 0;
    var symbols = ['!','@','#','$','%','^','&',"*",'(',')','_','+','=','-','.',','];
    password_strength='';
    for(i=0;i<password.value.length;i++){
        for(j=0; j<10; j++){
            if(password.value.charAt(i) == j) num_count++;//count how many numbers in the password
        }
        for(j=0;j<symbols.length;j++){
            //checks if a symbol is found
            if(password.value.charAt(i) == symbols[j]){
                //checks if it is the first symbol
                if(!symbol_found){
                    symbol = password.value.charAt(i);
                    symbol_found = 1;
                }//if it is the second symbol compares it with the first
                else{
                    if(symbol != password.value.charAt(i)) symbols_check = 1;
                }
            }
        } 
    }
    if(password.value.match(/[a-z]+/)) small_check = 1;//checks if password contains a small letter
    if(password.value.match(/[A-Z]+/)) cap_check = 1;//checks if password contains a capital letter
    if((small_check || cap_check) && symbol_found){
        if(num_count>=(password.value.length/2)) password_strength = 'weak';
        else if((num_count<(password.value.length/2)) && symbols_check && cap_check && small_check) password_strength = 'strong';
        else if(!(num_count>(password.value.length/2))) password_strength = 'medium';
        if(password.value.includes('helmepa') || password.value.includes('uoc') || password.value.includes('tuc')) password_strength = 'weak';
    }
    
    var show_strength = document.querySelector('#show_strength');
    show_strength.style.display = 'block';
    if(password_strength === 'weak'){
        show_strength.style.color = 'red';
        show_strength.innerHTML = 'weak';
    }
    else if(password_strength === 'strong'){
        show_strength.style.color = 'green';
        show_strength.innerHTML = 'strong';
    }
    else if(password_strength === 'medium'){
        show_strength.style.color = 'orange';
        show_strength.innerHTML = 'medium';
    }

    if(password.value.length<8 || password.value.length>12) show_strength.style.display = 'none';
}

password.addEventListener('keyup',checkPasswordSafety);


//librarian-student checking

document.querySelector('#student').addEventListener('click',displayUser);//when student radio button is clicked
document.querySelector('#librarian').addEventListener('click',displayUser);//when librarian radio button is clicked 

//changes the form depending on the user type
function displayUser(){

    //get the user selection
    var userType = document.querySelector('input[name="user-type"]:checked').value;

    if(userType === 'student'){
        //show fields for student
        document.querySelector('#student-type').style.display = 'block';
        document.querySelector('#university').style.display = 'block';
        document.querySelector('#pass-info').style.display = 'block';
        document.querySelector('#address-type').innerHTML = 'Home Address';
        
        //hide fields for librarian 
        document.querySelector('#library').style.display = 'none';

        //change required fields
        document.querySelector('#student-type').setAttribute("required","");
        document.querySelector('[name="university"]').setAttribute("required","");
        document.querySelector('[name="student_type"]').setAttribute("required","");
        document.querySelector('#student_id').setAttribute("required","");
        document.querySelector('#student_id_from_date').setAttribute("required","");
        document.querySelector('#student_id_to_date').setAttribute("required","");
        document.querySelector('#libraryname').removeAttribute("required");
        document.querySelector('#libraryinfo').removeAttribute("required");

    }

    if(userType === 'librarian'){
        //show fields for librarian
        document.querySelector('#library').style.display = 'block';
        document.querySelector('#address-type').innerHTML = 'Library Address';

        //hide fields for student
        document.querySelector('#student-type').style.display = 'none'
        document.querySelector('#university').style.display = 'none';
        document.querySelector('#pass-info').style.display = 'none';

        //change required fields 
        document.querySelector('#libraryname').setAttribute("required","");
        document.querySelector('#libraryinfo').setAttribute("required","");
        document.querySelector('#student-type').removeAttribute("required");
        document.querySelector('[name="university"]').removeAttribute("required");
        document.querySelector('[name="student_type"]').removeAttribute("required");
        document.querySelector('#student_id').removeAttribute("required");
        document.querySelector("#student_id_from_date").removeAttribute("required");
        document.querySelector("#student_id_to_date").removeAttribute("required");
    }
}

//extra checkings when the user is student and/or password is weak

function checkStudentEmail(){
    let university = document.querySelector('input[name="university"]:checked').value;
    let email = document.querySelector("#email").value;
    let email_end = university+".gr";

    if(email.endsWith(email_end)) return true;
    else return false;
}

function checkPassDate(){
    let start = new Date(document.getElementById("student_id_from_date").value);
    let expiration = new Date(document.getElementById("student_id_to_date").value);
    if(start>expiration || start === expiration) return false;
    else return true;
}

function checkPassDuration(){
    let start = new Date(document.querySelector('#student_id_from_date').value);
    let expiration = new Date(document.querySelector('#student_id_to_date').value);
    let years = Math.abs(expiration.getFullYear() - start.getFullYear());
    let student_type = document.querySelector('input[name="student_type"]:checked').value;

    if(student_type === "undergraduate" && years === 6) return true;
    if(student_type === "postgraduate" && years === 2) return true;
    if(student_type === "PhD" && years === 5) return true;

    return false;
}

function validateForm(){

    let user_type = document.querySelector('input[name="user-type"]:checked').value;

    if(password_strength == 'weak') {
        document.querySelector('#weak-password').style.display = 'block';
        return false;
    }
    else document.querySelector('#weak-password').style.display = 'none';

    if(!password_match){
        document.querySelector('#password-mismatch').style.display = 'block';
        return false;
    }
    else document.querySelector('#password-mismatch').style.display = 'none';

    //we dont have anything else to check if user is librarian
    if(user_type === 'librarian') return true;

    //chekings for students
    if(!checkStudentEmail()){
        document.querySelector('#wrong-email').style.display = 'block';
        return false;
    }
    else  document.querySelector('#wrong-email').style.display = 'none';

    if(!checkPassDate()){
        document.querySelector('#wrong-dates').style.display = 'block';
        return false;
    }
    else document.querySelector('#wrong-dates').style.display = 'none';

    if(!checkPassDuration()){
        document.querySelector('#wrong-duration').style.display = 'block';
        return false;
    }
    else document.querySelector('#wrong-duration').style.display = 'none';
}

//Map section

//initialise the map
map = new OpenLayers.Map("Map");
var mapnik = new OpenLayers.Layer.OSM();
map.addLayer(mapnik);
var center_position = setPosition(35.3053121,25.0722869);
map.setCenter(center_position,8);
document.querySelector('#Map').style.display = 'none';

//read the address from html
function readAddress(){
    var country = document.querySelector('#country').value;
    var city = document.querySelector('#city').value;
    var address = document.querySelector('#address').value;
    var complete = address+" "+city+" "+country;
    return complete;
}

//event listener for the map button to show or hide the map 
document.querySelector('#map-button').addEventListener('click',function(e){
    if(document.querySelector('#Map').style.display === 'none'){
        document.querySelector('#Map').style.display = 'block';
        document.querySelector('#map-button').innerHTML = 'Hide Map';
        loadDoc();
    }    
    else{
        document.querySelector('#Map').style.display = 'none';
        document.querySelector('#map-button').innerHTML = 'Show Map';
    }    
})

function hideShowMap(){
    document.querySelector('#Map').style.display = 'none';
    document.querySelector('#map-button').innerHTML = 'Show Map';
    document.querySelector('#map-fail').innerHTML = '';
}

document.querySelector('#country').addEventListener('change',hideShowMap);
document.querySelector('#city').addEventListener('change',hideShowMap);
document.querySelector('#address').addEventListener('change',hideShowMap);


function setPosition(lat, lon){
    var fromProjection = new OpenLayers.Projection("EPSG:4326");
    var toProjection = new OpenLayers.Projection("EPSG:900913");
    var position = new OpenLayers.LonLat(lon, lat).transform( fromProjection,
    toProjection);
    return position;
}

function handler(position, message){
    var popup = new OpenLayers.Popup.FramedCloud("Popup",position, null,message, null,true);
    map.addPopup(popup);
}

function popupClear() {
    while( map.popups.length ) {
         map.removePopup(map.popups[0]);
    }
}

function loadDoc(){
    const data = null;

    const xhr = new XMLHttpRequest();
    xhr.withCredentials = true;

    // var markers = new OpenLayers.Layer.Markers( "Markers" );
    // map.addLayer(markers);
    popupClear();
    xhr.addEventListener("readystatechange", function () {
        if (this.readyState === this.DONE) {
            const obj = JSON.parse(xhr.responseText);
            console.log(address);
            if(Object.keys(obj).length === 0){
                document.querySelector('#Map').style.display = 'none';
                document.querySelector('#map-fail').style.display = 'block';
                document.querySelector('#map-fail').innerHTML = 'Could Not Find The Location';
            }
            else if(!obj[0].display_name.includes("Crete")){
                document.querySelector('#Map').style.display = 'none';
                document.querySelector('#map-fail').style.display = 'block';
                document.querySelector('#map-fail').innerHTML = 'Service is available only for Crete';
            }
            else{
                let lon = obj[0].lon;
                let lat = obj[0].lat;
                handler(setPosition(lat,lon),address);
                map.setCenter(setPosition(lat,lon),16);
                document.querySelector('#map-fail').style.display = 'none';
            }
        }
    });

    var address = readAddress();
    xhr.open("GET", "https://forward-reverse-geocoding.p.rapidapi.com/v1/search?q="+address+"&polygon_threshold=0.0");
    xhr.setRequestHeader("X-RapidAPI-Key", "f2f9ed6942msh1bf966fdf276b80p15644bjsn7ea754a51041");
    xhr.setRequestHeader("X-RapidAPI-Host", "forward-reverse-geocoding.p.rapidapi.com");
    xhr.send(data);
}