$(function () {
    $('#main-block').children().remove();
    let table = $('<table/>', {
        id: 'departures',
        class: 'table table-striped table-bordered table-light'
    });
    let thead = $('<thead/>')
        .append($('<tr/>')
            .append($('<th/>').html('Column1'))
            .append($('<th/>').html('Column2'))).appendTo(table);
    // let tbody = $('<tbody/>')
    //     .append($('<tr/>')
    //         .append($('<th/>').html('Bla'))
    //         .append($('<th/>').html('Bla'))).appendTo(table);
    table.appendTo($('#main-block'));
    $('#departures').DataTable({
        ajax: '/departure'
    });
});