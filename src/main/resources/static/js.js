/*
*  Функция скрытия кнопки удаления цпу
 */
function onloadbodyCpu() {
    let button = document.getElementById("restore-button")
    if (localStorage.getItem("cpu") != null) {
        button.style.visibility = "visible";
    } else button.style.visibility = "hidden";
    return document.getElementById('container');
}

function store(id, key, cost) {
    let keyCost = key + "cost";
    if (localStorage.getItem(key) == null) {
        localStorage.setItem(key, id);
        localStorage.setItem(keyCost, cost);
        alert("Текущий компонент " + key + " id: " + localStorage.getItem(key))
    } else if (confirm("Компонент уже выбран, удалите уже выбранный и попробуйте заново.")) {
        localStorage.setItem(key, id);
        localStorage.setItem(keyCost, cost);
    }
}

/*function reStoreCpu() {
    if (localStorage.getItem("cpu") != null) {
        let button = document.getElementById("restore-button")
        button.style.visibility = "hidden";
        localStorage.removeItem("cpu");
    } else alert("Процессор пустует.")
}

function reStoreGpu() {
    if (localStorage.getItem("gpu") != null) {
        let button = document.getElementById("restore-button")
        button.style.visibility = "hidden";
        localStorage.removeItem("gpu");
    } else alert("Видеокарта пустует.")
}*/

/*function comeBack() {
    location.href = "/list?"
    if (localStorage.getItem("cpu") != null) {
        location.href = "/list?cpu=" + localStorage.getItem("cpu");
        if (localStorage.getItem("gpu") != null) {
            location.href = "/list?cpu=" + localStorage.getItem("cpu") +
                "&gpu=" + localStorage.getItem("gpu");
            if (localStorage.getItem("Dram") != null) {

            }
        }
    } else {
        location.href = "/list";
    }
    return location.href;
}*/

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

function sortingByName() {
    var items = document.querySelectorAll('.block');

    // get all items as an array and call the sort method
    Array.from(items).sort(function(a, b) {
        // get the text content
        a = a.querySelector('.item-name').innerHTML.toLowerCase()
        b = b.querySelector('.item-name').innerHTML.toLowerCase()
        return (a > b) - (a < b)
    }).forEach(function(n, i) {
        n.style.order = i
    })

}

function sortingByPrice(){
    var items = document.querySelectorAll('.block')

    Array.from(items).sort(function(a, b) {
        // using ~~ to cast the value to a number instead of a string
        a = ~~a.querySelector('.product-price').innerText
        b = ~~b.querySelector('.product-price').innerText
        return a - b
    }).forEach(function(n, i) {
        n.style.order = i
    })
}

