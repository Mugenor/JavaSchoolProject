$(function () {
    let coach = $('.pic');
    let coachNotHover = $(coach.children()[0]);
    let coachHover = $(coach.children()[1]);
    coach.on('mouseover', function (event) {
        console.log(event);
        coachNotHover.removeClass('active');
        coachHover.addClass('active');
    });
    coach.on('mouseout', function (event) {
        console.log(event);
        coachHover.removeClass('active');
        coachNotHover.addClass('active');
    })
});