$(function(){
    $("#datetimepicker").datepicker({
        format: "dd.mm.yyyy",
        startDate: "01.01.1950",
        endDate: "01.01.2018",
        immediateUpdates: true
    });
    let errorDiv = $('<div/>', {
        class: 'alert alert-danger w-full text-center'
    })

    function validateForm() {
        console.log("in form validate");
        $('.alert').remove();
        let nameRegexp = /^[A-ZА-Я][a-zа-я]+$/;
        let usernameRegexp = /^[a-zа-я0-9]{2,}$/i;
        let name = this.name.value;
        this.name.value = name = name.trim();

        let surname = this.surname.value;
        this.surname.value = surname = surname.trim();

        let username = this.username.value;
        this.username.value = username = username.trim();

        let birthday = this.birthday.value;
        let password = this.password.value;
        let isValid = true;
        if (password.length < 5) {
            errorDiv.html('<p>Password must not be less then 5 characters!</p>');
            isValid = false;
        }
        if (birthday === "") {
            errorDiv.html('<p>Enter your birthday!</p>');
            isValid = false;
        }
        if (!usernameRegexp.test(username)) {
            errorDiv.html('<p>Enter correct username!');
            isValid = false;
        }
        if (!nameRegexp.test(surname)) {
            errorDiv.html('<p>Enter correct surname!</p>');
            isValid = false;
        }
        if (surname.length < 2) {
            errorDiv.html('<p>Surname must not be less then 2 characters!');
            isValid = false;
        }
        if (!nameRegexp.test(name)) {
            errorDiv.html('<p>Enter correct name!</p>');
            isValid = false;
        }
        if (name.length < 2) {
            errorDiv.html('<p>Name must not be less then 2 characters!</p>');
            isValid = false;
        }
        if (!isValid) {
            errorDiv.prependTo(this);
        }
        return isValid;
    }

    $('#newUser').on('submit', validateForm);
});

