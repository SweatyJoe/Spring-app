function onload() {
    let button = document.getElementById("restore-button")
    if (localStorage.getItem("cpu") != null) {
        button.style.visibility = "visible";
    } else button.style.visibility = "hidden";
}

function store(id) {
    if (localStorage.getItem("cpu") == null) {
        localStorage.setItem("cpu", id);
        alert("item set" + localStorage.getItem("cpu"))
    } else alert("ЦПУ уже выбран, удалите уже выбранный и попробуйте заново.")
}

function reStore() {
    if (localStorage.getItem("cpu") != null) {
        let button = document.getElementById("restore-button")
        button.style.visibility = "hidden";
        localStorage.removeItem("cpu");
    } else alert("Процессор уже сброшен.")
}

function comeBack() {
    location.href = "/list?"
    if (localStorage.getItem("cpu") != null) {
        location.href = "/list?cpu=" + localStorage.getItem("cpu");
        if (localStorage.getItem("gpu") != null) {
            location.href = "/list?cpu=" + localStorage.getItem("cpu") +
                "&gpu=" + localStorage.getItem("gpu");
            if(localStorage.getItem("Dram") != null){

            }
        }
    } else {
        location.href = "/list";
    }

    return location.href;
}

//searcher
function myFunction() {
    // Declare variables
    var input, filter, table, tr, td, i, txtValue;
    input = document.getElementById("Input");
    filter = input.value.toUpperCase();
    table = document.getElementById("table");
    tr = table.getElementsByTagName("tr");

    for (i = 0; i < tr.length; i++) {
        td = tr[i].getElementsByTagName("td")[0];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(filter) > -1) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
}

//filter
function sortTable(n) {
    var table, rows, switching, i, x, y, shouldSwitch, dir, switchcount = 0;
    table = document.getElementById("table");
    switching = true;
    dir = "asc";
    while (switching) {
        switching = false;
        rows = table.getElementsByTagName("TR");
        for (i = 1; i < (rows.length - 1); i++) {
            shouldSwitch = false;
            x = rows[i].getElementsByTagName("TD")[n];
            y = rows[i + 1].getElementsByTagName("TD")[n];
            if (dir == "asc") {
                if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
                    shouldSwitch = true;
                    break;
                }
            } else if (dir == "desc") {
                if (x.innerHTML.toLowerCase() < y.innerHTML.toLowerCase()) {
                    shouldSwitch = true;
                    break;
                }
            }
        }
        if (shouldSwitch) {
            rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
            switching = true;
            switchcount++;
        } else {
            if (switchcount == 0 && dir == "asc") {
                dir = "desc";
                switching = true;
            }
        }
    }
}