$(function () {
    let csrfToken = $("meta[name='_csrf']").attr("content");
    let csrfHeader = $("meta[name='_csrf_header']").attr("content");
    let coach = $('.pic');
    let coachNotHover = $(coach.children()[0]);
    let coachHover = $(coach.children()[1]);
    coach.on('mouseover', function (event) {
        coachNotHover.removeClass('active');
        coachHover.addClass('active');
    });
    coach.on('mouseout', function (event) {
        coachHover.removeClass('active');
        coachNotHover.addClass('active');
    });
    let seats = $('.seat');
    let awaiting = false;
    $('.free').click(function (event) {
        if (!awaiting) {
            seats.removeClass('chosen');
            $(this).addClass('chosen');
        }
    });
    $('#buyTicketButton').click(function (event) {
        let chosen = $('.chosen');
        if(chosen.length === 0){
            $('.card-footer .alert').remove();
            $('.card-footer').append($('<div/>', {
                class: 'alert alert-info'
            }).html('Choose seat at first!'));
            return;
        }
        let seatInfo = {
            departureId: $('.pic').parent().data('iddeparture'),
            seatNum: chosen.data('seatnum'),
            coachNumber: chosen.data('coachnum')
        };
        let th = this;
        $.ajax('/client/tickets/buy', {
            method: "POST",
            contentType: 'application/json',
            data: JSON.stringify(seatInfo),
            beforeSend: function (jqXHR) {
                awaiting = true;
                jqXHR.setRequestHeader(csrfHeader, csrfToken);
                $(this).prop('disabled', true)
            },
            success: function (resp) {
                let mainBlock = $('#main-block');
                mainBlock.children().remove();
                $('<div/>', {
                    class: 'alert alert-success'
                }).html('You successfully bought a ticket!').appendTo(mainBlock);
            },
            error: function (jqXHR, status, error) {
                awaiting = false;
                console.log(jqXHR);
                $('.card-footer .alert').remove();
                $('.card-footer').append($('<div/>', {
                    class: 'alert alert-danger'
                }).html(jqXHR.responseJSON));
                $(this).prop('disabled', false);
            }
        });
    })
});