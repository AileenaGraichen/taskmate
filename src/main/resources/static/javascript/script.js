function showLoginForm(){
    document.getElementById("login-form").style.display = "block";
    document.getElementById("register-form").style.display = "none";
}

function showRegisterForm(){
    document.getElementById("login-form").style.display = "none";
    document.getElementById("register-form").style.display = "block";
}

function openCreateProjectForm() {
    document.getElementById("create-project-form").style.display = "block";
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