
function move(elem) {
    let progress = parseInt(elem.getAttribute("data-progress"));
    let width = 0;
    let id = setInterval(frame, 10);
    function frame() {
        if (width >= progress) {
            clearInterval(id);
        } else {
            width++;
            elem.style.width = width + "%";
        }
    }
}

function progressBar(){
    document.querySelectorAll('.myBar').forEach(elem => move(elem));
}

