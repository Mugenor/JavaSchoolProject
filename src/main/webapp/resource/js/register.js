$(function(){
    $("#datetimepicker").datepicker({
        format: "dd.mm.yyyy",
        startDate: "01.01.1950",
        endDate: "01.01.2018",
        immediateUpdates: true
    });
});

function validateForm() {
    console.log("in form validate");
    let nameRegexp = /^[A-ZА-Я][a-zа-я]+$/;
    let usernameRegexp = /^[a-zа-я0-9]{2,}$/i;
    let form = document.forms.newUser;
    let name = form.name.value;
    form.name.value = name = name.trim();

    let surname = form.surname.value;
    form.surname.value = surname = surname.trim();

    let username = form.username.value;
    form.username.value = username = username.trim();

    let birthday = form.birthday.value;
    let password = form.password.value;

    if (!nameRegexp.test(name) || !nameRegexp.test(surname) || !usernameRegexp.test(username)
        || birthday === "" || password.length < 5) {
        console.log("form invalid");
        return false;
    }
    console.log("form valid");
    return true;
}