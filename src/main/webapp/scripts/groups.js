function getAllGroups() {
    var request = new XMLHttpRequest();
    request.open("GET", "http://localhost:8080/groups/getall", false);
    request.send();

    for (i = 0; request.size; i++) {
        var ul = document.getElementById("groupMainContent").innerText =
            request[i]
    }
}

function loadDoc() {
    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function () {
        if (this.readyState == 4 && this.status == 200) {
            document.getElementById("groupMainContent").innerHTML = this.responseText;

        }
        ;
        xhttp.open("GET", "http://localhost:8080/groups/getall", true);
        xhttp.send();
    }
}
