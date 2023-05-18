function showLoginForm(){
    document.getElementById("login-form").style.display = "block";
    document.getElementById("register-form").style.display = "none";
}

function showRegisterForm(){
    document.getElementById("login-form").style.display = "none";
    document.getElementById("register-form").style.display = "block";
}

function openCreateProjectForm() {
    document.getElementById("create-project").style.display = "block";
}

function openCreateSectionForm() {
    document.getElementById("create-section-form").style.display = "block";
}

function openEditProjectForm() {
    document.getElementById("edit-project-form").style.display = "block";
}

function openDeleteProjectPopUp() {
    document.getElementById("delete-project-id").style.display = "block";
}

function openCreateActivityForm() {
    document.getElementById("create-activity-form").style.display = "block";
}

function openEditActivityForm() {
    document.getElementById("edit-activity-form").style.display = "block";
}

function openDeleteActivityPopUp() {
    document.getElementById("delete-activity-id").style.display = "block";
}

function openEditSectionForm() {
    document.getElementById("edit-section-form").style.display = "block";
}

function openDeleteSectionPopUp() {
    document.getElementById("delete-section-id").style.display = "block";
}

function openCreateTaskForm() {
    document.getElementById("create-task-form").style.display = "block";
}

function openEditTaskForm() {
    document.getElementById("edit-task-form").style.display = "block";
}

function openDeleteTaskPopUp() {
    document.getElementById("delete-task-id").style.display = "block";
}


function toggleNavbar() {
    let x = document.getElementById("navbar-side");
    let y = document.getElementById("dashboard");
    if(x.style.left === "-200px"){
        x.style.left = "0";
        y.style.marginLeft = "300px";
    } else {
        x.style.left = "-200px";
        y.style.marginLeft = "100px";
    }
}